import * as S from '../style'
import { Text } from '../../../components/index'
import { default as Table } from './table'
import { useEffect, useState } from 'react'
import Pagination from '@mui/material/Pagination'
import apiClient from '../../../axios'

const AdminRent = () => {
  const [rents, setRents] = useState([])
  const [page, setPage] = useState(1)

  const fetchRents = async () => {
    const response = await apiClient.get(`/api/admin/rent`)
    setRents(response.data)
    console.log(response.data)
  }

  useEffect(() => {
    fetchRents()
  }, [])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const sortRents = (a, b) => {
    // 먼저 returnedDate의 유무에 따라 정렬
    if (a.returnedDate && b.returnedDate) {
      return new Date(b.startDate) - new Date(a.startDate)
    }
    if (!a.returnedDate && b.returnedDate) {
      return -1
    }
    if (a.returnedDate && !b.returnedDate) {
      return 1
    }
    // 둘 다 returnedDate가 없는 경우 startDate를 기준으로 정렬
    return new Date(b.startDate) - new Date(a.startDate)
  }
  const rentsToShow = rents.sort(sortRents).slice((page - 1) * 20, page * 20)

  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`대출 관리`}
          fontSize={({ theme }) => theme.fontSize.sz40}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
      </S.AdminTitle>
      <S.TableContainer>
        <Table rents={rentsToShow} />
      </S.TableContainer>
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(rents.length / 20)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </S.AdminUserContainer>
  )
}

export default AdminRent
