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
  rentId,
  bookName,
  userEmail,
  rentDate,
  returnDate,
  bookState
) {
  return { rentId, bookName, userEmail, rentDate, returnDate, bookState }
}

const rows = [
  createData(
    1,
    '인간본성의 법칙',
    'dog@gmail.com',
    '2023.06.17',
    '-',
    '연체중(2일)'
  ),
  createData(
    2,
    '인간이해',
    'dogsdfs@gmail.com',
    '2023.06.12',
    '2023.06.28',
    '반납됨'
  ),
]

export default function CustomizedTables({ rents }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>책제목</StyledTableCell>
            <StyledTableCell>이메일</StyledTableCell>
            <StyledTableCell>대출일자</StyledTableCell>
            <StyledTableCell>반납일자</StyledTableCell>
            <StyledTableCell>상태</StyledTableCell>
            <StyledTableCell>버튼</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rents.map(rent => (
            <StyledTableRow key={rent.name}>
              <StyledTableCell component="th" scope="rent">
                {rent.id}
              </StyledTableCell>
              <StyledTableCell title={rent.title}>
                {rent.title.length > 15
                  ? rent.title.substring(0, 15) + '...'
                  : rent.title}
              </StyledTableCell>

              <StyledTableCell>{rent.userEmail}</StyledTableCell>
              <StyledTableCell>
                {new Date(rent.rentStartDate).toLocaleDateString()}
              </StyledTableCell>
              <StyledTableCell>
                {' '}
                {rent.rentReturnedDate ? rent.rentReturnedDate : '-'}
              </StyledTableCell>
              <StyledTableCell>
                {(() => {
                  if (rent.rentReturnedDate) {
                    return '반납완료'
                  } else {
                    let rentStartDate = new Date(rent.rentStartDate)
                    let today = new Date()

                    let diffInMs = Math.abs(today - rentStartDate)

                    let diffInDays = Math.floor(
                      diffInMs / (1000 * 60 * 60 * 24)
                    )

                    if (diffInDays <= 7) {
                      return '대출중'
                    } else {
                      let overdueDays = diffInDays - 7
                      return `연체중(${overdueDays}일)`
                    }
                  }
                })()}
              </StyledTableCell>

              <StyledTableCell>
                <Button variant="contained" disabled={!!rent.rentReturnedDate}>
                  반납하기
                </Button>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
