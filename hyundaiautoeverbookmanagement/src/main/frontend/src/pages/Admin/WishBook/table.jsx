import { useContext } from 'react'
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
import { SnackbarContext } from '../../../context/SnackbarContext'
import apiClient from '../../../axios'
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
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}))

export default function CustomizedTables({ wishBooks }) {
  const showConfirm = useConfirm()
  const showAlert = useAlert()
  const { setSnackbar } = useContext(SnackbarContext)

  const sortWishBooks = (a, b) => {
    // 먼저 status에 따라 정렬
    if (a.status === 'PENDING' && b.status !== 'PENDING') {
      return -1
    }
    if (a.status !== 'PENDING' && b.status === 'PENDING') {
      return 1
    }
    return new Date(b.wishDate) - new Date(a.wishDate)
  }

  const submitReject = async wishbookId => {
    try {
      await apiClient.patch('/api/admin/wish/reject', {
        wishId: wishbookId,
      })

      window.location.reload()
      setSnackbar({
        open: true,
        severity: 'error',
        message: '희망도서 반려!',
      })
    } catch (error) {
      if (error.response && error.response.status === 500) {
        showAlert('당신은 Admin이 아닙니다.')
      }
    }
  }

  const handleUpdateButtonClick = async wishbookId => {
    showConfirm('희망 도서를 반려하시겠습니까?', () => submitReject(wishbookId))
  }

  const submitAdd = async wishbook => {
    try {
      await apiClient.post('/api/admin/wish/approve', {
        id: wishbook.id,
        status: wishbook.status,
        email: null,
        book: {
          title: wishbook.book.title,
          author: wishbook.book.author,
          publisher: wishbook.book.publisher,
          category: wishbook.book.category,
          info: wishbook.book.info,
          rent_count: 0,
          isbn: wishbook.book.isbn,
          cover: wishbook.book.cover,
          pubDate: wishbook.book.pubDate,
        },
      })
      window.location.reload()
      setSnackbar({
        open: true,
        severity: 'success',
        message: '희망도서 추기!',
      })
    } catch (error) {
      if (error.response.status === 500) {
        showAlert('해당 도서는 이미 존재합니다.')
      }
    }
  }

  const handleAddButtonClick = async wishbook => {
    showConfirm('희망 도서를 추가하시겠습니까?', () => submitAdd(wishbook))
  }

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>번호</StyledTableCell>
            <StyledTableCell>신청자</StyledTableCell>
            <StyledTableCell>도서명</StyledTableCell>
            <StyledTableCell>저자</StyledTableCell>
            <StyledTableCell>발행일자</StyledTableCell>
            <StyledTableCell>출핀사</StyledTableCell>
            <StyledTableCell>ISBN</StyledTableCell>
            <StyledTableCell>신청일자</StyledTableCell>
            <StyledTableCell>버튼</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {wishBooks.sort(sortWishBooks).map(wishbook => (
            <StyledTableRow key={wishbook.name}>
              <StyledTableCell component="th" scope="wishbook">
                {wishbook.id}
              </StyledTableCell>
              <StyledTableCell>{wishbook.email}</StyledTableCell>
              <StyledTableCell>{wishbook.book.title}</StyledTableCell>
              <StyledTableCell>{wishbook.book.author}</StyledTableCell>
              <StyledTableCell>{wishbook.book.pubDate}</StyledTableCell>
              <StyledTableCell>{wishbook.book.publisher}</StyledTableCell>
              <StyledTableCell>{wishbook.book.isbn}</StyledTableCell>
              <StyledTableCell>{wishbook.wishDate}</StyledTableCell>
              <StyledTableCell>
                <Stack spacing={2}>
                  <Button
                    variant="contained"
                    disabled={wishbook.status !== 'PENDING'}
                    onClick={() => handleAddButtonClick(wishbook)}
                  >
                    추가
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    disabled={wishbook.status !== 'PENDING'}
                    onClick={() => handleUpdateButtonClick(wishbook.id)}
                  >
                    반려
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
