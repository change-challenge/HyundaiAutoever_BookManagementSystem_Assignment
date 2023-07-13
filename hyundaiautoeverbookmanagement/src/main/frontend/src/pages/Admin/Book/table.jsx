import * as React from 'react'
import { styled } from '@mui/material/styles'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell, { tableCellClasses } from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import Stack from '@mui/material/Stack'
import Button from '@mui/material/Button'

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

export default function CustomizedTables({ books }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>책제목</StyledTableCell>
            <StyledTableCell>저자</StyledTableCell>
            <StyledTableCell>발행일자</StyledTableCell>
            <StyledTableCell>출판사</StyledTableCell>
            <StyledTableCell>ISBN</StyledTableCell>
            <StyledTableCell>수량</StyledTableCell>
            <StyledTableCell></StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {books.map(book => (
            <StyledTableRow key={book.bookId}>
              <StyledTableCell component="th" scope="row">
                {book.id}
              </StyledTableCell>
              <StyledTableCell title={book.title}>
                {book.title.length > 15
                  ? book.title.substring(0, 15) + '...'
                  : book.title}
              </StyledTableCell>

              <StyledTableCell>{book.author}</StyledTableCell>
              <StyledTableCell>
                {new Date(book.pubDate).toLocaleDateString()}
              </StyledTableCell>

              <StyledTableCell>{book.publisher}</StyledTableCell>
              <StyledTableCell>{book.isbn}</StyledTableCell>
              <StyledTableCell>{book.bookCount}</StyledTableCell>
              <StyledTableCell>
                <Stack spacing={2}>
                  <Button variant="contained">수정</Button>
                  <Button variant="contained" color="error">
                    삭제
                  </Button>
                </Stack>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
