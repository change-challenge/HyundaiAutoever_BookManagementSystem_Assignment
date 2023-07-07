import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ThemeProvider } from 'styled-components'
import GlobalStyle from './styles/GlobalStyle'
import theme from './styles/theme'
import { IsLoginProvider } from './context/IsLoginContext'
import { UserProvider } from './context/UserContext'
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
axios.interceptors.request.use(
  config => {
    const token = sessionStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <>
      <UserProvider>
        <IsLoginProvider>
          <GlobalStyle />
          <ThemeProvider theme={theme}>
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
            </Router>
          </ThemeProvider>
        </IsLoginProvider>
      </UserProvider>
    </>
  </React.StrictMode>
)
