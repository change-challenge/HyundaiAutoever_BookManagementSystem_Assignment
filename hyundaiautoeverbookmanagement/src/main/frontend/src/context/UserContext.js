import React, { createContext, useContext, useState, useEffect } from 'react'
import axios from 'axios'

const UserContext = createContext(null)

export function UserProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null)

  useEffect(() => {
    // 로그인 후 사용자 정보를 가져오는 요청을 보냅니다.
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get('/api/user/me')
        const userInfo = response.data
        setUserInfo(userInfo)
      } catch (error) {
        console.error('Failed to fetch user info', error)
      }
    }

    fetchUserInfo()
  }, [])

  return (
    <UserContext.Provider value={userInfo}>{children}</UserContext.Provider>
  )
}

export function useUser() {
  const userInfo = useContext(UserContext)
  if (userInfo === null) {
    throw new Error('useUser must be used within a UserProvider')
  }
  return userInfo
}
