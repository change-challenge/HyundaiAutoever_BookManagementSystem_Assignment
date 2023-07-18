import { useContext } from 'react'
import { styled } from '@mui/material/styles'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell, { tableCellClasses } from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import Button from '@mui/material/Button'
import { SnackbarContext } from '../../../context/SnackbarContext'

import axios from 'axios'

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

export default function CustomizedTables({ rents }) {
  const { setSnackbar } = useContext(SnackbarContext)

  const handleReturnClick = (copyId, memberEmail) => {
    const confirm = window.confirm('도서를 반납하시겠습니까?')
    if (confirm) {
      makeReturn(copyId, memberEmail)
    }
  }

  const makeReturn = async (copyId, memberEmail) => {
    const response = await axios
      .patch(`/api/admin/return/${copyId}`, {
        copyId: copyId,
        email: memberEmail,
      })
      .then(response => {
        console.log('makeReturn: ', response.data)
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'success',
          message: '도서 반납 성공!',
        })
      })
      .catch(error => {
        if (error.response) {
          setSnackbar({
            open: true,
            severity: 'error',
            message: '도서를 반납할 수 없습니다.',
          })
          if (error.response.status === 400) {
            console.error('Client error: ', error.response.data)
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

              <StyledTableCell>{rent.memberEmail}</StyledTableCell>
              <StyledTableCell>
                {new Date(rent.startDate).toLocaleDateString()}
              </StyledTableCell>
              <StyledTableCell>
                {' '}
                {rent.returnedDate ? rent.returnedDate : '-'}
              </StyledTableCell>
              <StyledTableCell>
                {(() => {
                  if (rent.returnedDate) {
                    return '반납완료'
                  } else {
                    let startDate = new Date(rent.startDate)
                    let today = new Date()

                    let diffInMs = Math.abs(today - startDate)

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
                <Button
                  variant="contained"
                  disabled={!!rent.returnedDate}
                  onClick={() =>
                    handleReturnClick(rent.copyId, rent.memberEmail)
                  }
                >
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
