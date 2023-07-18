import * as S from '../style'
import { Text } from '../../../components/index'
import { default as Table } from './table'
import { useEffect, useState } from 'react'
import Pagination from '@mui/material/Pagination'
import axios from 'axios'

const AdminWishBook = () => {
  const [wishBook, setWishBook] = useState([])
  const [page, setPage] = useState(1)

  const fetchwishBook = async () => {
    const response = await axios.get(`/api/admin/wish`)
    setWishBook(response.data)
  }

  useEffect(() => {
    fetchwishBook()
  }, [])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const wishBookToShow = wishBook.slice((page - 1) * 20, page * 20)

  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`신청 도서 관리`}
          fontSize={({ theme }) => theme.fontSize.sz40}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
      </S.AdminTitle>
      <S.TableContainer>
        <Table wishBooks={wishBookToShow} />
      </S.TableContainer>
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(wishBook.length / 20)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </S.AdminUserContainer>
  )
}

export default AdminWishBook
