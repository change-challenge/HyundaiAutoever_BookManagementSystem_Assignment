import React, { createContext, useState, useContext } from 'react'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogTitle from '@mui/material/DialogTitle'
import Button from '@mui/material/Button'

const AlertContext = createContext()

const AlertDialog = ({ message, onClose }) => {
  return (
    <Dialog open={Boolean(message)} onClose={onClose} maxWidth="xs" fullWidth>
      <DialogTitle>{message}</DialogTitle>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          확인
        </Button>
      </DialogActions>
    </Dialog>
  )
}

export const useAlert = () => {
  return useContext(AlertContext)
}

export const AlertProvider = ({ children }) => {
  const [alertMessage, setAlertMessage] = useState(null)

  const showAlert = message => {
    setAlertMessage(message)
  }

  const closeAlert = () => {
    setAlertMessage(null)
  }

  return (
    <AlertContext.Provider value={showAlert}>
      {children}
      {alertMessage && (
        <AlertDialog message={alertMessage} onClose={closeAlert} />
      )}
    </AlertContext.Provider>
  )
}
