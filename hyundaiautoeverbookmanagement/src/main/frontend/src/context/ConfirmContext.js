import React, { createContext, useState, useContext } from 'react'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogTitle from '@mui/material/DialogTitle'
import Button from '@mui/material/Button'

const ConfirmContext = createContext()

export const useConfirm = () => {
  return useContext(ConfirmContext)
}

export const ConfirmProvider = ({ children }) => {
  const [confirmSettings, setConfirmSettings] = useState({
    open: false,
    message: '',
    onConfirm: null,
  })

  function showConfirm(message, onConfirm, onCancel) {
    setConfirmSettings({
      open: true,
      message,
      onConfirm: () => {
        onConfirm && onConfirm()
        handleCancel()
      },
      onCancel: () => {
        onCancel && onCancel()
        handleCancel()
      },
    })
  }

  const handleConfirm = () => {
    if (confirmSettings.onConfirm) {
      confirmSettings.onConfirm()
    }
    setConfirmSettings({ open: false, message: '', onConfirm: null })
  }

  const handleCancel = () => {
    if (confirmSettings.onCancel) {
      confirmSettings.onCancel()
    }
    setConfirmSettings({
      open: false,
      message: '',
      onConfirm: null,
      onCancel: null,
    })
  }

  return (
    <ConfirmContext.Provider value={showConfirm}>
      {children}
      {confirmSettings.open && (
        <ConfirmDialog
          message={confirmSettings.message}
          onConfirm={handleConfirm}
          onCancel={handleCancel}
        />
      )}
    </ConfirmContext.Provider>
  )
}

const ConfirmDialog = ({ message, onConfirm, onCancel }) => {
  return (
    <Dialog open={Boolean(message)} onClose={onCancel} maxWidth="xs" fullWidth>
      <DialogTitle>{message}</DialogTitle>
      <DialogActions>
        <Button onClick={onCancel} color="primary">
          아니요
        </Button>
        <Button onClick={onConfirm} color="primary">
          예
        </Button>
      </DialogActions>
    </Dialog>
  )
}
