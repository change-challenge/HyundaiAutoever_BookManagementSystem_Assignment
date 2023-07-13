import React from 'react'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Modal from '@mui/material/Modal'

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

function SearchResultModal({ open, books, onSelectBook, handleClose }) {
  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            희망도서 검색 결과
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            {books.map(book => (
              <div key={book.id}>
                <h3>{book.title}</h3>
                <button onClick={() => onSelectBook(book)}>Select</button>
              </div>
            ))}
          </Typography>
        </Box>
      </Modal>
    </div>
  )
}

export default SearchResultModal
