import React from 'react'
import styled from 'styled-components'
import { useEffect, useState, useContext } from 'react'
import { fetchUserInfo } from '../../context/UserContext'
import axios from 'axios'
import Button from '@mui/material/Button'
import { SnackbarContext } from '../../context/SnackbarContext'

const TableWrapper = styled.div`
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
`

const Table = styled.table`
  width: 100%;
`

const TableHeader = styled.th`
  background-color: ${({ theme }) => theme.colors.grey1};
  padding: 10px;
  text-align: center;
  font-weight: bold;
  font-family: 'Apple SD Gothic Neo';
  border: 1px solid #ccc;
`

const TableBody = styled.tbody`
  color: ${({ theme }) => theme.colors.grey9};
`

const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #f9f9f9;
  }
`

const TableCell = styled.td`
  padding: 10px;
  border: 1px solid #ccc;
  text-align: center;
  font-family: 'Apple SD Gothic Neo';
  vertical-align: middle;
`

const BookCountTable = ({ bookId }) => {
  const [copyDetail, setCopyDetail] = useState([])
  const [user, setUser] = useState(null)
  const { setSnackbar } = useContext(SnackbarContext)

  const handleRentClick = copyId => {
    if (!user) {
      alert('로그인이 필요한 기능입니다!')
      return
    }
    console.log('copyId : ', copyId)

    const confirm = window.confirm('도서를 대여하시겠습니까?')
    if (confirm) {
      makeRent(copyId)
    }
  }

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
    }
    getUserInfo()
    console.log(user)
  }, [])

  const makeRent = async copyId => {
    const response = await axios
      .post(`/api/rent/${copyId}`, {
        copyId: copyId,
        email: user.email,
      })
      .then(response => {
        console.log('makeRent: ', response.data)
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'success',
          message: '도서 대여 성공!',
        })
      })
      .catch(error => {
        if (error.response) {
          if (error.response.status === 400) {
            if (error.response.data == '세권초과') {
              setSnackbar({
                open: true,
                severity: 'error',
                message: '도서는 3권까지 빌릴 수 있습니다.',
              })
            }
            if (error.response.data == '빌린도서') {
              setSnackbar({
                open: true,
                severity: 'error',
                message: '이미 대여한 도서입니다.',
              })
            }
          } else {
            console.error('Server error: ', error.response.data)
          }
        } else if (error.request) {
          console.error('No response: ', error.request)
        } else {
          console.error('Error: ', error.message)
        }
      })
  }

  const fetchCopys = async () => {
    const response = await axios.get(`/api/bookdetail/${bookId}`)
    setCopyDetail(response.data)
    console.log(response.data)
  }

  useEffect(() => {
    fetchCopys()
  }, [])

  return (
    <TableWrapper>
      <Table>
        <thead>
          <tr>
            <TableHeader>번호</TableHeader>
            <TableHeader>대출상태</TableHeader>
            <TableHeader>반납 예정일</TableHeader>
            <TableHeader>도서 대여</TableHeader>
          </tr>
        </thead>
        <TableBody>
          {copyDetail.map((item, index) => (
            <TableRow key={index}>
              <TableCell>{index + 1}</TableCell>
              <TableCell style={{ fontWeight: 'bold' }}>
                {item.endDate === null ? (
                  '대출가능'
                ) : (
                  <div>
                    대출불가
                    <br />
                    [대출중]
                    <br />
                  </div>
                )}
              </TableCell>
              <TableCell>
                {item.endDate === null ? '-' : `${item.endDate}`}
              </TableCell>
              <TableCell>
                {item.endDate === null ? (
                  <Button
                    variant="contained"
                    onClick={() => handleRentClick(item.copyId)}
                  >
                    대여하기
                  </Button>
                ) : (
                  <Button variant="contained" disabled>
                    대여하기
                  </Button>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableWrapper>
  )
}

export default BookCountTable
