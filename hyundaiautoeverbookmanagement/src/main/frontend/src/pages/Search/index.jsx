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

  const [selectedFilter, setSelectedFilter] = useState('정확도순')
  const [books, setBooks] = useState([])
  const [filteredBooks, setFilteredBooks] = useState([])
  const [selectedCategory, setSelectedCategory] = useState('전체')
  const [categoryCounts, setCategoryCounts] = useState({})
  const [page, setPage] = useState(1)

  useEffect(() => {
    const fetchBooks = async () => {
      const response = await axios.get(`/api/book/search?title=${query}`)
      setBooks(response.data)
      setFilteredBooks(response.data)
      setFilteredBooks(sortBooks(response.data))
      console.log(books)

      const counts = response.data.reduce((acc, book) => {
        acc[book.category] = (acc[book.category] || 0) + 1
        return acc
      }, {})
      counts['전체'] = response.data.length
      setCategoryCounts(counts)
    }
    fetchBooks()
  }, [])

  useEffect(() => {
    setFilteredBooks(sortBooks(books))
  }, [selectedCategory, selectedFilter])

  useEffect(() => {
    if (selectedCategory === '전체') {
      setFilteredBooks(books)
    } else {
      setFilteredBooks(books.filter(book => book.category === selectedCategory))
    }
  }, [selectedCategory])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const sortBooks = books => {
    let sortedBooks = books.slice()
    if (selectedFilter === '발행일순') {
      sortedBooks.sort((a, b) => new Date(b.pubDate) - new Date(a.pubDate))
    } else if (selectedFilter === '인기순') {
      sortedBooks.sort((a, b) => b.rentCount - a.rentCount)
    }
    if (selectedCategory !== '전체') {
      sortedBooks = sortedBooks.filter(
        book => book.category === selectedCategory
      )
    }
    return sortedBooks
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
            text={`에 대한 ${filteredBooks.length}개의 검색 결과`}
            color={({ theme }) => theme.colors.black}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz28}
          />
        </S.BookSearchTitleWrapper>
        <S.BookSearchContainer>
          <SideBar
            setSelectedCategory={setSelectedCategory}
            categoryCounts={categoryCounts}
          />
          <S.BookSearchContentWrapper>
            <S.BookSearchFilterWrapper>
              <ul
                style={{
                  display: 'flex',
                  flexWrap: 'wrap',
                }}
              >
                {filters.map(filter => (
                  <li key={filter.id} style={{ margin: '0 10px 0 10px' }}>
                    <button
                      style={{
                        textDecoration: 'none',
                        color: 'gray',
                        fontWeight:
                          selectedFilter === filter.name ? 'bolder' : 'normal',
                        border: 'none',
                        backgroundColor: 'transparent',
                        cursor: 'pointer',
                      }}
                      onClick={() => setSelectedFilter(filter.name)}
                    >
                      {filter.name}
                    </button>
                  </li>
                ))}
              </ul>
            </S.BookSearchFilterWrapper>
            <S.BookListWrapper>
              {filteredBooks.slice((page - 1) * 20, page * 20).map(book => (
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
