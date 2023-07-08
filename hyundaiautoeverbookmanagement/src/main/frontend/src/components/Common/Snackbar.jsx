import { useContext, forwardRef } from 'react'
import { SnackbarContext } from '../../context/SnackbarContext'
import MuiAlert from '@mui/material/Alert'
import Snackbar from '@mui/material/Snackbar'

const Alert = forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />
})

const SnackbarComponent = () => {
  const { snackbar, setSnackbar } = useContext(SnackbarContext)

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return
    }

    setSnackbar({
      ...snackbar,
      open: false,
    })
  }

  return (
    <Snackbar
      open={snackbar.open}
      autoHideDuration={3000}
      onClose={handleClose}
    >
      <Alert
        onClose={handleClose}
        severity={snackbar.severity}
        sx={{ width: '100%' }}
      >
        {snackbar.message}
      </Alert>
    </Snackbar>
  )
}

export default SnackbarComponent
