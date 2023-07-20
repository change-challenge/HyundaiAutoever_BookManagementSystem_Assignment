import * as S from '../style'
import { Text } from '../../../components/index'
import { default as Table } from './table'
import { useEffect, useState } from 'react'
import Pagination from '@mui/material/Pagination'
import apiClient from '../../../axios'

const AdminBook = () => {
  const [books, setBooks] = useState([])
  const [page, setPage] = useState(1)

  const fetchBooks = async () => {
    const response = await apiClient.get(`/api/admin/book`)
    setBooks(response.data)
  }

  useEffect(() => {
    fetchBooks()
  }, [])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const booksToShow = books.slice((page - 1) * 20, page * 20)

  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`도서 관리`}
          fontSize={({ theme }) => theme.fontSize.sz40}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
      </S.AdminTitle>
      <S.TableContainer>
        <Table books={booksToShow} />
      </S.TableContainer>
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(books.length / 20)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </S.AdminUserContainer>
  )
}

export default AdminBook
