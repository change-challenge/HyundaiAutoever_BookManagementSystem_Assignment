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

function createData(userId, userEmail, userName, isRent, userState) {
  return { userId, userEmail, userName, isRent, userState }
}

const rows = [
  createData(1, 'dog94@gmail.com', '장호잔', '-', '연체중(2일)'),
  createData(2, 'ghwls@gmail.com', '장석진', '대출중(3)', '-'),
]

export default function CustomizedTables() {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>이메일</StyledTableCell>
            <StyledTableCell>이름</StyledTableCell>
            <StyledTableCell>대출 여부</StyledTableCell>
            <StyledTableCell>상태</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(row => (
            <StyledTableRow key={row.name}>
              <StyledTableCell component="th" scope="row">
                {row.userId}
              </StyledTableCell>
              <StyledTableCell>{row.userEmail}</StyledTableCell>
              <StyledTableCell>{row.userName}</StyledTableCell>
              <StyledTableCell>{row.isRent}</StyledTableCell>
              <StyledTableCell>{row.userState}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
