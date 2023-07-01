import * as S from './style'
import SideBar from './SideBar'
import { Text } from '../../components/index'

const BookSearch = () => {
  return (
    <>
      <S.Container>
        <S.BookSearchContainer>
          <S.InnerWrapper>
            <S.BookSearchTitleWrapper>
              <SideBar />
              <Text
                text="‘00’에 대한 120개의 검색 결과"
                color={({ theme }) => theme.colors.black}
                fontWeight={'bold'}
                fontSize={({ theme }) => theme.fontSize.sz32}
              />
            </S.BookSearchTitleWrapper>
          </S.InnerWrapper>
        </S.BookSearchContainer>
      </S.Container>
    </>
  )
}

export default BookSearch
