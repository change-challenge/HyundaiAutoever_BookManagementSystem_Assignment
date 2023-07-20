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
import { useState } from 'react'
import Modal from '@mui/material/Modal'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Select from '@mui/material/Select'
import MenuItem from '@mui/material/MenuItem'
import apiClient from '../../../axios'
import { SnackbarContext } from '../../../context/SnackbarContext'
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

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
}

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}))

export default function CustomizedTables({ books }) {
  const showAlert = useAlert()
  const showConfirm = useConfirm()
  const [open, setOpen] = useState(false)
  const [bookId, setBookId] = useState(0)
  const [bookCount, setBookCount] = useState(0)
  const { setSnackbar } = useContext(SnackbarContext)

  const handleOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }
  const HandleModifyClick = id => {
    handleOpen()
    setBookId(id)
  }

  const sumbitDeleteBook = async bookId => {
    apiClient
      .post('/api/admin/book/delete', { bookId })
      .then(() => {
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'error',
          message: '도서 삭제 성공!',
        })
      })
      .catch(error => {
        if (error.response.status === 500) {
          showAlert('당신은 Admin이 아닙니다.')
        }
      })
  }

  const HandleDeleteClick = bookId => {
    showConfirm('도서를 삭제하시겠습니까? 삭제 후 복구가 불가능합니다.', () =>
      sumbitDeleteBook(bookId)
    )
  }

  return (
    <>
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
                    <Button
                      variant="contained"
                      onClick={() => HandleModifyClick(book.id)}
                    >
                      수정
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      onClick={() => HandleDeleteClick(book.id)}
                    >
                      삭제
                    </Button>
                  </Stack>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            도서 갯수를 수정하시겠습니까?
          </Typography>
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              gap: 2,
              my: 2,
            }}
          >
            <Select
              value={bookCount}
              onChange={event => setBookCount(event.target.value)}
              sx={{
                width: 70,
                height: 40,
              }}
            >
              {[...Array(21).keys()].map(value => (
                <MenuItem key={value} value={value + 1}>
                  {value + 1}
                </MenuItem>
              ))}
            </Select>
            <Button
              variant="contained"
              sx={{
                height: 40,
              }}
              onClick={() => {
                apiClient
                  .patch('/api/admin/book/update', { bookId, bookCount })
                  .then(() => {
                    window.location.reload()
                    setSnackbar({
                      open: true,
                      severity: 'success',
                      message: '도서 수정 성공!',
                    })
                  })
                  .catch(error => {
                    if (error.response.status === 500) {
                      showAlert('당신은 Admin이 아닙니다.')
                    }
                  })
                handleClose()
              }}
            >
              확인
            </Button>
          </Box>
        </Box>
      </Modal>
    </>
  )
}
