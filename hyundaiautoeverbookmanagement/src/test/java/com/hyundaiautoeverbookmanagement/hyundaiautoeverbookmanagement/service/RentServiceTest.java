package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RentService에 대한 테스트")
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
        List<Rent> rents = IntStream.range(0, 3)
                .mapToObj(index -> {
                    Rent rent = new Rent();
                    rent.setId((long) index);
                    rent.setIsExtendable(false);

                    Member member = new Member();
                    rent.setMember(member);

                    Copy copy = new Copy();
                    copy.setId((long) index);

                    Book book = new Book();
                    book.setId((long) index);

                    copy.setBook(book);
                    rent.setCopy(copy);

                    return rent;
                })
                .collect(Collectors.toList());

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
    @DisplayName("Rent했던 경험이 전혀 없는 케이스")
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





}