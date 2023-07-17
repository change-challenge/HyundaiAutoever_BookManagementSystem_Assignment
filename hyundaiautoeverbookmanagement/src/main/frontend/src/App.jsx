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
} from './pages/index'

const App = () => {
  const [user, setUser] = useState(null)
  const location = useLocation()

  useEffect(() => {
    console.log('user : ', user)
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
    }
    getUserInfo()
    console.log('APP의 USER다 ', user)
  }, [location])

  let HeaderComponent

  if (location.pathname === '/') {
    HeaderComponent = Header
  } else {
    HeaderComponent = user
      ? HeaderWithSearchBarLogIn
      : HeaderWithSearchBarLogOut
  }
  console.log('밖이다 APP의 USER다 ', user)

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
      </Routes>
    </>
  )
}

export default App
