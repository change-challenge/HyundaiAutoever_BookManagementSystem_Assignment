import * as S from '../style'
import { Text } from '../../../components/index'
import { default as Table } from './table'
import { useEffect, useState } from 'react'
import Pagination from '@mui/material/Pagination'
import axios from 'axios'

const AdminUser = ({ user }) => {
  console.log('AdminUser user : ', user)
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [page, setPage] = useState(1)

  const fetchUsers = async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await axios.get(`/api/admin/member`)
      setUsers(response.data)
      setLoading(false)
    } catch (error) {
      setError(error)
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchUsers()
  }, [])

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const usersToShow = users.slice((page - 1) * 20, page * 20)

  if (loading) return <div>Loading...</div>
  if (error) return <div>Error occurred: {error.message}</div>

  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`회원 관리`}
          fontSize={({ theme }) => theme.fontSize.sz40}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
      </S.AdminTitle>
      <S.TableContainer>
        <Table users={usersToShow} myInfo={user} />
      </S.TableContainer>
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(users.length / 20)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </S.AdminUserContainer>
  )
}

export default AdminUser
