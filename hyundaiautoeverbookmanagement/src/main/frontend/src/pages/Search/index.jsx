import { useState, useEffect } from 'react'
import * as S from './style'
import SideBar from './SideBar'
import { Text } from '../../components/index'
import { Link, useLocation } from 'react-router-dom'
import BookList from './BookList'
import Pagination from '@mui/material/Pagination'
import axios from 'axios'

const filters = [
  { id: 1, name: '정확도순' },
  { id: 2, name: '발행일순' },
  { id: 3, name: '인기순' },
]

const BookSearch = () => {
  const location = useLocation()
  const searchParams = new URLSearchParams(location.search)
  const query = searchParams.get('query') // 검색어 가져오기

  const [books, setBooks] = useState([])
  const [page, setPage] = useState(1)

  useEffect(() => {
    const fetchBooks = async () => {
      const response = await axios.get(`/api/book/search?title=${query}`)
      setBooks(response.data)
      console.log(books)
    }
    fetchBooks()
  }, [query])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  return (
    <>
      <S.Container>
        <S.BookSearchTitleWrapper>
          <Text
            text={`${query}`}
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz28}
          />
          <Text
            text={`에 대한 ${books.length}개의 검색 결과`}
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
              {books.slice((page - 1) * 20, page * 20).map(book => (
                <BookList key={book.id} book={book} query={query} />
              ))}
            </S.BookListWrapper>
            <S.PaginationWrapper>
              <Pagination
                count={Math.ceil(books.length / 20)}
                page={page}
                onChange={handlePageChange}
                shape="rounded"
              />
            </S.PaginationWrapper>
          </S.BookSearchContentWrapper>
        </S.BookSearchContainer>
      </S.Container>
    </>
  )
}

export default BookSearch
