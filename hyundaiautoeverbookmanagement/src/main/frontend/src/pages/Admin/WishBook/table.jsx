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

export default function CustomizedTables({ wishBooks }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>신청자</StyledTableCell>
            <StyledTableCell>책제목</StyledTableCell>
            <StyledTableCell>저자</StyledTableCell>
            <StyledTableCell>발행일자</StyledTableCell>
            <StyledTableCell>출핀사</StyledTableCell>
            <StyledTableCell>ISBN</StyledTableCell>
            <StyledTableCell>버튼</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {wishBooks.map(wishbook => (
            <StyledTableRow key={wishbook.name}>
              <StyledTableCell component="th" scope="wishbook">
                {wishbook.wishBookId}
              </StyledTableCell>
              <StyledTableCell>{wishbook.userEmail}</StyledTableCell>
              <StyledTableCell>{wishbook.bookName}</StyledTableCell>
              <StyledTableCell>{wishbook.bookAuthor}</StyledTableCell>
              <StyledTableCell>{wishbook.bookcreateDate}</StyledTableCell>
              <StyledTableCell>{wishbook.bookPublisher}</StyledTableCell>
              <StyledTableCell>{wishbook.ISBN}</StyledTableCell>
              <StyledTableCell>버튼</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
