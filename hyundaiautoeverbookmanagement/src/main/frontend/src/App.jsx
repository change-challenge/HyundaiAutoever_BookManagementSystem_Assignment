import { Routes, Route, useLocation } from 'react-router-dom'
import { fetchUserInfo } from './context/UserContext'
import { useEffect, useState } from 'react'

import {
  Header,
  HeaderWithSearchBarLogOut,
  HeaderWithSearchBarLogIn,
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
  Error,
} from './pages/index'

const App = () => {
  const [user, setUser] = useState(null)
  const location = useLocation()

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
    }
    getUserInfo()
  }, [location])

  let HeaderComponent

  if (location.pathname === '/') {
    HeaderComponent = Header
  } else {
    HeaderComponent = user
      ? HeaderWithSearchBarLogIn
      : HeaderWithSearchBarLogOut
  }

  return (
    <>
      <HeaderComponent />
      <Routes>
        <Route path="/" element={<Main />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/signup" element={<SignUp />}></Route>
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/search" element={<BookSearch />} />
        <Route path="/search/detail/:bookId" element={<BookDetail />} />
        <Route path="/wishbook" element={<WishBook />} />
        <Route path="/admin" element={<AdminLayout />}>
          <Route path="" element={<AdminMain />} />
          <Route path="user" element={<AdminUser />} />
          <Route path="rent" element={<AdminRent />} />
          <Route path="book" element={<AdminBook />} />
          <Route path="wishbook" element={<AdminWishBook />} />
        </Route>
        <Route path="/*" element={<Error />} />
      </Routes>
    </>
  )
}

export default App
