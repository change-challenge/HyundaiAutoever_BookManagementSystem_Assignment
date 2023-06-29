import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ThemeProvider } from 'styled-components'
import GlobalStyle from './styles/GlobalStyle'
import theme from './styles/theme'
import { Header, Footer } from './components/index'
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
} from './pages/index'

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <>
      <GlobalStyle />
      <ThemeProvider theme={theme}>
        <Router>
          <Header />
          <Routes>
            <Route path="/" element={<Main />}></Route>
            <Route path="/login" element={<Login />}></Route>
            <Route path="/signup" element={<SignUp />}></Route>
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
    </>
  </React.StrictMode>
)
