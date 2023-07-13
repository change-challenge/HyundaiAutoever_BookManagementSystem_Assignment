import * as S from '../style'
import { Text } from '../../../components/index'
import { default as Table } from './table'
import { useEffect, useState } from 'react'
import Pagination from '@mui/material/Pagination'
import axios from 'axios'

const AdminRent = () => {
  const [rents, setRents] = useState([])
  const [page, setPage] = useState(1)

  const fetchRents = async () => {
    const response = await axios.get(`/api/admin/rent`)
    setRents(response.data)
    console.log(response.data)
  }

  useEffect(() => {
    fetchRents()
  }, [])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const rentsToShow = rents.slice((page - 1) * 20, page * 20)

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
