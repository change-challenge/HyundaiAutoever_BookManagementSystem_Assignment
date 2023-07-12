import * as React from 'react'
import { styled } from '@mui/material/styles'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell, { tableCellClasses } from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'

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
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}))

export default function CustomizedTables({ users }) {
  console.log('table users : ', users)
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
                  {user.rentCount == 0 ? '-' : `대출중(${user.rentCount}권)`}
                </StyledTableCell>
                <StyledTableCell>
                  {user.lateDay == 0 ? '-' : `연채중(${user.lateDay}일)`}
                </StyledTableCell>
              </StyledTableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
