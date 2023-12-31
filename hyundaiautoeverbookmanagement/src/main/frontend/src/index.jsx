import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter as Router } from 'react-router-dom'
import { ThemeProvider } from 'styled-components'
import GlobalStyle from './styles/GlobalStyle'
import theme from './styles/theme'
import { IsLoginProvider } from './context/IsLoginContext'
import { UserProvider } from './context/UserContext'
import { SnackbarProvider } from './context/SnackbarContext'
import { AlertProvider } from './context/AlertContext'
import { ConfirmProvider } from './context/ConfirmContext'
import { SnackbarComponent } from './components/index'
import apiClient from './axios'
import App from './App'
import Footer from './components/Footer'

// 새로고침 시 localStorage에 저장된 토큰을 사용하여 로그인 상태를 확인
const token = localStorage.getItem('token')
if (token) {
  apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <IsLoginProvider>
      <UserProvider>
        <ConfirmProvider>
          <AlertProvider>
            <GlobalStyle />
            <ThemeProvider theme={theme}>
              <SnackbarProvider>
                <Router>
                  <App />
                  <Footer />
                  <SnackbarComponent />
                </Router>
              </SnackbarProvider>
            </ThemeProvider>
          </AlertProvider>
        </ConfirmProvider>
      </UserProvider>
    </IsLoginProvider>
  </React.StrictMode>
)
