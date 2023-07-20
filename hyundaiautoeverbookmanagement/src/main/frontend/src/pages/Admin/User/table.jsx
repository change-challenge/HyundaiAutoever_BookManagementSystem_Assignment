import * as React from 'react'
import { styled } from '@mui/material/styles'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell, { tableCellClasses } from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import Button from '@mui/material/Button'
import apiClient from '../../../axios'
import { SnackbarContext } from '../../../context/SnackbarContext'
import { useEffect, useState, useContext } from 'react'
import { fetchUserInfo } from '../../../context/UserContext'
import { useAlert } from '../../../context/AlertContext'
import { useConfirm } from '../../../context/ConfirmContext'

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}))

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}))

export default function CustomizedTables({ users }) {
  const showAlert = useAlert()
  const showConfirm = useConfirm()
  const { setSnackbar } = useContext(SnackbarContext)
  const [myInfo, setMyInfo] = useState(null)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setMyInfo(userInfo)
    }
    getUserInfo()
    console.log(myInfo)
  }, [])

  console.log('myInfo : ', myInfo)
  const ChangeMemberType = async email => {
    console.log('email : ', email)
    const response = await apiClient
      .patch(`/api/admin/member/type`, {
        email: email,
        myEmail: myInfo.email,
      })
      .then(response => {
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'success',
          message: '유저 권한 변경!',
        })
      })
      .catch(error => {
        showAlert('당신은 Admin이 아닙니다.')
        console.log(error)
      })
  }

  const HandleClick = email => {
    showConfirm('유저 권한을 바꾸시겠습니까?', () => ChangeMemberType(email))
  }
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>이메일</StyledTableCell>
            <StyledTableCell>이름</StyledTableCell>
            <StyledTableCell>가입일자</StyledTableCell>
            <StyledTableCell>대출 여부</StyledTableCell>
            <StyledTableCell>상태</StyledTableCell>
            <StyledTableCell></StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users &&
            users.length > 0 &&
            users.map(user => (
              <StyledTableRow key={user.name}>
                <StyledTableCell component="th" scope="row">
                  {user.id}
                </StyledTableCell>
                <StyledTableCell>{user.email}</StyledTableCell>
                <StyledTableCell>{user.name}</StyledTableCell>
                <StyledTableCell>
                  {new Date(user.registDate).toLocaleDateString()}
                </StyledTableCell>

                <StyledTableCell>
                  {user.rentCount === 0 ? '-' : `대출중(${user.rentCount}권)`}
                </StyledTableCell>
                <StyledTableCell>
                  {user.lateDay === 0 ? '-' : `연채중(${user.lateDay}일)`}
                </StyledTableCell>
                <StyledTableCell>
                  {user.memberType === 'ADMIN' ? (
                    <Button
                      variant="contained"
                      onClick={() => HandleClick(user.email)}
                    >
                      TO USER
                    </Button>
                  ) : (
                    <Button
                      variant="contained"
                      color="error"
                      onClick={() => HandleClick(user.email)}
                    >
                      TO ADMIN
                    </Button>
                  )}
                </StyledTableCell>
              </StyledTableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
