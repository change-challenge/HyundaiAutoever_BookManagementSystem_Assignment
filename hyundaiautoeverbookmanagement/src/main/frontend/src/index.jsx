import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ThemeProvider } from 'styled-components'
import GlobalStyle from './styles/GlobalStyle'
import theme from './styles/theme'
import { IsLoginProvider } from './context/IsLoginContext'
import { UserProvider } from './context/UserContext'
import { SnackbarProvider } from './context/SnackbarContext'
import { SnackbarComponent } from './components/index'

import {
  Header,
  HeaderWithSearchBarLogOut,
  HeaderWithSearchBarLogIn,
  Footer,
} from './components/index'
import {
  Main,
  Login,
  SignUp,
  AdminLayout,
  AdminMain,
  AdminBook,
  AdminRent,
  AdminUser,
  AdminWishBook,
  Mypage,
  BookSearch,
  BookDetail,
  WishBook,
} from './pages/index'
import axios from 'axios'

// Axios 인터셉터 설정
// 새로고침 시 localStorage에 저장된 토큰을 사용하여 로그인 상태를 확인
const token = localStorage.getItem('token')
if (token) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <>
      <UserProvider>
        <IsLoginProvider>
          <GlobalStyle />
          <ThemeProvider theme={theme}>
            <SnackbarProvider>
              <Router>
                <Header />
                <Routes>
                  <Route path="/" element={<Main />}></Route>
                  <Route path="/login" element={<Login />}></Route>
                  <Route path="/signup" element={<SignUp />}></Route>
                  <Route path="/mypage" element={<Mypage />} />
                  <Route path="/search" element={<BookSearch />} />
                  <Route path="/search/detail" element={<BookDetail />} />
                  <Route path="/wishbook" element={<WishBook />} />
                  <Route path="/admin" element={<AdminLayout />}>
                    <Route path="" element={<AdminMain />} />
                    <Route path="user" element={<AdminUser />} />
                    <Route path="rent" element={<AdminRent />} />
                    <Route path="book" element={<AdminBook />} />
                    <Route path="wishbook" element={<AdminWishBook />} />
                  </Route>
                </Routes>
                <Footer />
                <SnackbarComponent />
              </Router>
            </SnackbarProvider>
          </ThemeProvider>
        </IsLoginProvider>
      </UserProvider>
    </>
  </React.StrictMode>
)
