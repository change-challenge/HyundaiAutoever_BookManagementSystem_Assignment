import React, { createContext, useContext, useState, useEffect } from 'react'
import axios from 'axios'

const UserContext = createContext(null)

export function UserProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null)

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem('token') // 토큰 가져오기
        if (token) {
          const config = {
            headers: {
              Authorization: `Bearer ${token}`, // 헤더에 토큰 추가
            },
          }
          const response = await axios.get('/api/user/me', config) // 헤더를 포함하여 요청 보내기

          const userInfo = response.data
          console.log(userInfo)
          setUserInfo(userInfo)
        }
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
