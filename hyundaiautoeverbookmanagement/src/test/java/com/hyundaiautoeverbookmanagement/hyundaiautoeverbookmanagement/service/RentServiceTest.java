package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.CopyException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.RentException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.UserException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("RentService에 대한 테스트")
@Transactional
class RentServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    BookRepository bookRepository;

    @Mock
    RentRepository rentRepository;
    @Mock
    CopyRepository copyRepository;

    @InjectMocks
    RentService rentService;

    @AfterEach
    @DisplayName("SecurityContextHolder 컨텍스트 클리어")
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("모든 Rent를 반환한다.")
    public void ShoudGetAllRents() {
        // Given
        List<Rent> rents = getRents();

        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setId((long) i);
            when(copyRepository.findBookByCopyId((long) i)).thenReturn(book);
        }
        when(rentRepository.findAll()).thenReturn(rents);

        // When
        List<RentResponseDTO> result = rentService.getAllRents();

        // Then
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Rent할 것이 없는 테스트")
    public void ShoudGetNoRent() {
        // Given
        when(rentRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<RentResponseDTO> result = rentService.getAllRents();

        // Then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("현재 대출중인 Rent만 반환되는 케이스")
    public void ShouldGetCurrentRents() {
        // Given
        String email = "test@email.com";
        Member member = new Member();
        member.setId(1L);

        List<Rent> rents = getRents();

        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setId((long) i);
            when(copyRepository.findBookByCopyId((long) i)).thenReturn(book);
        }

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndReturnedDateIsNull(member.getId())).thenReturn(rents);

        // When
        List<RentResponseDTO> result = rentService.getCurrentRents(email);

        // Then
        assertEquals(3, result.size());
        assertNull(result.get(0).getReturnedDate().orElse(null));
    }

    @Test
    @DisplayName("대출중인 Rent가 없는 케이스")
    public void ShouldNotGetCurrentRents() {
        // Given
        String email = "test@email.com";
        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndReturnedDateIsNull(member.getId())).thenReturn(Collections.emptyList());

        // When
        List<RentResponseDTO> result = rentService.getCurrentRents(email);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Rent했던 이력이 전부 반환되는 케이스")
    public void ShouldGetHistoryRents() {
        // Given
        String email = "test@email.com";
        Member member = new Member();
        member.setId(1L);

        List<Rent> rents = getRents();

        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setId((long) i);
            when(copyRepository.findBookByCopyId((long) i)).thenReturn(book);
        }

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberId(member.getId())).thenReturn(rents);

        // When
        List<RentResponseDTO> result = rentService.getHistoryRents(email);

        // Then
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("getHistoryRents 시, Rent했던 경험이 전혀 없는 실패 사례")
    public void ShouldNotGetHistoryRents() {
        // Given
        String email = "test@email.com";
        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberId(member.getId())).thenReturn(Collections.emptyList());

        // When
        List<RentResponseDTO> result = rentService.getHistoryRents(email);

        // Then
        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("rent 시, 유저가 확인 되지 않는 실패 사례")
    public void ShouldNotRentIfMemberIdNotValid() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // when
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // then
        assertThrows(UserException.class, () -> rentService.rent(form));
    }

    @Test
    @DisplayName("rent 시, 세 권 이상 빌리려고 할 때 실패 사례")
    public void ShouldNotRentIfRent3Book() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // when
        Member member = new Member();
        member.setRentCount(3);
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        String result = rentService.rent(form);

        // then
        assertEquals("세권초과", result);
    }

    @Test
    @DisplayName("rent 시, 현재 빌린 도서 빌리려 할 때 실패 사례")
    public void ShouldNotRentIfRentSameBook() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        Member member = new Member();
        member.setRentCount(2);

        Book book = new Book();
        book.setTitle("Book Title");

        // when
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(copyRepository.findBookByCopyId(any())).thenReturn(book);
        when(rentRepository.findRentedBookTitlesByMemberId(any())).thenReturn(Collections.singletonList("Book Title"));

        // then
        String result = rentService.rent(form);
        assertEquals("빌린도서", result);
    }

    @Test
    @DisplayName("rent 시, 성공 사례")
    public void ShouldRent() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        Member member = new Member();
        member.setRentCount(1);

        Book book = new Book();
        book.setTitle("Book Title");

        Copy copy = new Copy();

        // when
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(copyRepository.findBookByCopyId(any())).thenReturn(book);
        when(rentRepository.findRentedBookTitlesByMemberId(any())).thenReturn(Collections.emptyList());
        when(copyRepository.findById(any())).thenReturn(Optional.of(copy));

        // then
        String result = rentService.rent(form);
        assertEquals("Success", result);
    }


    @Test
    @DisplayName("adminReturn 시, Admin 권한이 없어서 실패한 경우")
    public void ShouldNotAdminReturnBookIfNotAdmin() {
        // given
        RentRequestDTO form = new RentRequestDTO();

        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when & then
        assertThrows(RuntimeException.class, () -> rentService.adminReturnBook(form));
    }

    @Test
    @DisplayName("adminReturn 시, 유저가 확인되지 않아 실패한 경우")
    public void ShouldNotAdminReturnBookIfMemberIdNotValid() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> rentService.adminReturnBook(form));
    }

    @Test
    @DisplayName("adminReturn 시, Rent가 확인되지 않아 실패한 경우")
    public void ShouldNotAdminReturnBookIfRentNotFound() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Member member = new Member();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.empty());

        assertThrows(RentException.class, () -> rentService.adminReturnBook(form));
    }

    @Test
    @DisplayName("adminReturn 시, Copy가 확인되지 않아 실패한 경우")
    public void ShouldNotAdminReturnBookIfCopyNotFound() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Member member = new Member();
        Rent rent = new Rent();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.of(rent));
        when(copyRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CopyException.class, () -> rentService.adminReturnBook(form));
    }

    @Test
    @DisplayName("adminReturn 시, 성공한 경우")
    public void ShouldAdminReturnBook() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Member member = new Member();
        Rent rent = new Rent();
        Copy copy = new Copy();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.of(rent));
        when(copyRepository.findById(any())).thenReturn(Optional.of(copy));

        String result = rentService.adminReturnBook(form);
        assertEquals("Success", result);
    }


    @Test
    @DisplayName("returnBook 시, 유저가 확인되지 않아 실패한 경우")
    public void ShouldNotReturnBookIfMemberIdNotValid() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> rentService.returnBook(form));
    }

    @Test
    @DisplayName("returnBook 시, Rent가 확인되지 않아 실패한 경우")
    public void ShouldNotReturnBookIfRentNotFound() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        Member member = new Member();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.empty());

        assertThrows(RentException.class, () -> rentService.returnBook(form));
    }

    @Test
    @DisplayName("returnBook 시, Copy가 확인되지 않아 실패한 경우")
    public void ShouldNotReturnBookIfCopyNotFound() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        Member member = new Member();
        Rent rent = new Rent();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.of(rent));
        when(copyRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CopyException.class, () -> rentService.returnBook(form));
    }

    @Test
    @DisplayName("returnBook 시, 성공한 경우")
    public void ShouldReturnBook() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        Member member = new Member();
        Rent rent = new Rent();
        Copy copy = new Copy();

        // when & then
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(any(), any())).thenReturn(Optional.of(rent));
        when(copyRepository.findById(any())).thenReturn(Optional.of(copy));

        String result = rentService.returnBook(form);
        assertEquals("Success", result);
    }

    @Test
    @DisplayName("extendBook 시, 사용자를 찾을 수 없을 경우 실패 사례")
    public void shouldNotExtendBookIfMemberNotFound() {
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> rentService.extendBook(form));
    }

    @Test
    @DisplayName("extendBook 시, Rent 정보를 찾을 수 없을 경우 실패 사례")
    public void shouldNotExtendBookIfRentNotFound() {
        // given
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");
        form.setCopyId(1L);

        Member member = new Member();
        member.setEmail("test@email.com");
        member.setId(1L);

        // when
        when(memberRepository.findByEmail(form.getEmail())).thenReturn(Optional.of(member));
        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())).thenReturn(Optional.empty());

        // then
        assertThrows(RentException.class, () -> rentService.extendBook(form));
        verify(memberRepository).findByEmail(form.getEmail());
        verify(rentRepository).findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId());
    }

    @Test
    @DisplayName("extendBook 시, 이미 도서 연장이 이루어진 경우 실패 사례")
    public void shouldNotExtendIfAlreadyExtended() {
        RentRequestDTO form = setupRentRequestDTO();
        Rent rent = new Rent();
        rent.setIsExtendable(false);

        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(rent));

        String result = rentService.extendBook(form);
        assertEquals("이미 연장을 하였습니다.", result);
    }

    @Test
    @DisplayName("extendBook 시, 도서 연체 중일 때 실패 사례")
    public void shouldExtendBookIfLate() {
        RentRequestDTO form = setupRentRequestDTO();
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now().minusDays(2));
        rent.setIsExtendable(true);

        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(rent));

        assertThrows(RentException.class, () -> rentService.extendBook(form));
    }

    @Test
    @DisplayName("extendBook 시, 반납 일이 오늘이거나 어제가 아닐 경우 실패 사례")
    public void shouldExtendBookIfNotDueOrOneDayBefore() {
        RentRequestDTO form = setupRentRequestDTO();
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now().plusDays(2));
        rent.setIsExtendable(true);

        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(rent));

        assertThrows(RuntimeException.class, () -> rentService.extendBook(form));
    }

    @Test
    @DisplayName("extendBook 시, 성공 사례 - 어제")
    public void shouldExtendBook_Yesterday() {
        RentRequestDTO form = setupRentRequestDTO();
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now().plusDays(1));
        rent.setIsExtendable(true);

        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(rent));

        String result = rentService.extendBook(form);
        assertEquals("Success", result);
    }

    @Test
    @DisplayName("extendBook 시, 성공 사례 - 오늘")
    public void shouldExtendBook_Today() {
        RentRequestDTO form = setupRentRequestDTO();
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now());
        rent.setIsExtendable(true);

        when(rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(rent));

        String result = rentService.extendBook(form);
        assertEquals("Success", result);
    }

    private RentRequestDTO setupRentRequestDTO() {
        RentRequestDTO form = new RentRequestDTO();
        form.setEmail("test@email.com");
        form.setCopyId(1L);

        Member member = new Member();
        member.setId(1L); // ID 값을 설정
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        return form;
    }

    private static List<Rent> getRents() {
        List<Rent> rents = IntStream.range(0, 3)
                .mapToObj(index -> {
                    Rent rent = new Rent();
                    rent.setId((long) index);
                    rent.setReturnedDate(null);
                    rent.setIsExtendable(false);

                    Member memberInRent = new Member();
                    rent.setMember(memberInRent);

                    Copy copy = new Copy();
                    copy.setId((long) index);

                    Book book = new Book();
                    book.setId((long) index);

                    copy.setBook(book);
                    rent.setCopy(copy);

                    return rent;
                })
                .collect(Collectors.toList());
        return rents;
    }
}