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

function createData(
  bookId,
  bookName,
  bookAuthor,
  bookcreateDate,
  bookPublisher,
  ISBN,
  bookCount
) {
  return {
    bookId,
    bookName,
    bookAuthor,
    bookcreateDate,
    bookPublisher,
    ISBN,
    bookCount,
  }
}

const rows = [
  createData(
    1,
    '인간본성의 법칙',
    '로버트 그린',
    '2023.06.17',
    '위즈덤',
    '9791190182560',
    3
  ),
  createData(
    2,
    '인간이해',
    '로버트 그린',
    '2023.06.17',
    '민음사',
    '9791190182560',
    1
  ),
]

export default function CustomizedTables() {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>책제목</StyledTableCell>
            <StyledTableCell>저자</StyledTableCell>
            <StyledTableCell>발행일자</StyledTableCell>
            <StyledTableCell>출핀사</StyledTableCell>
            <StyledTableCell>ISBN</StyledTableCell>
            <StyledTableCell>수량</StyledTableCell>
            <StyledTableCell>버튼</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(row => (
            <StyledTableRow key={row.name}>
              <StyledTableCell component="th" scope="row">
                {row.bookId}
              </StyledTableCell>
              <StyledTableCell>{row.bookName}</StyledTableCell>
              <StyledTableCell>{row.bookAuthor}</StyledTableCell>
              <StyledTableCell>{row.bookcreateDate}</StyledTableCell>
              <StyledTableCell>{row.bookPublisher}</StyledTableCell>
              <StyledTableCell>{row.ISBN}</StyledTableCell>
              <StyledTableCell>{row.bookCount}</StyledTableCell>
              <StyledTableCell>버튼</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
