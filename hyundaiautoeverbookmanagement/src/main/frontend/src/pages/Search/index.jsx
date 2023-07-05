import * as S from './style'
import SideBar from './SideBar'
import { Text } from '../../components/index'
import { Link } from 'react-router-dom'
import BookList from './BookList'
import Pagination from '@mui/material/Pagination'

const filters = [
  { id: 1, name: '정확도순' },
  { id: 2, name: '발행일순' },
  { id: 3, name: '인기순' },
]

const BookSearch = () => {
  return (
    <>
      <S.Container>
        <S.BookSearchTitleWrapper>
          <Text
            text="‘00’"
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz28}
          />
          <Text
            text="에 대한 120개의 검색 결과"
            color={({ theme }) => theme.colors.black}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz28}
          />
        </S.BookSearchTitleWrapper>
        <S.BookSearchContainer>
          <SideBar />
          <S.BookSearchContentWrapper>
            <S.BookSearchFilterWrapper>
              <ul
                style={{
                  display: 'flex',
                  flexWrap: 'wrap',
                }}
              >
                {filters.map(category => (
                  <li key={category.id} style={{ margin: '0 10px 0 10px' }}>
                    <Link
                      to={`/search`}
                      style={{
                        textDecoration: 'none',
                        color: 'gray',
                        fontWeight: 400,
                      }}
                      onMouseOver={e => {
                        e.target.style.fontWeight = 700
                      }}
                      onMouseOut={e => {
                        e.target.style.fontWeight = 400
                      }}
                    >
                      {category.name}
                    </Link>
                  </li>
                ))}
              </ul>
            </S.BookSearchFilterWrapper>
            <S.BookListWrapper>
              <BookList />
              <BookList />
              <BookList />
              <BookList />
              <BookList />
              <BookList />
            </S.BookListWrapper>
            <S.PaginationWrapper>
              <Pagination count={10} shape="rounded" />
            </S.PaginationWrapper>
          </S.BookSearchContentWrapper>
        </S.BookSearchContainer>
      </S.Container>
    </>
  )
}

export default BookSearch
