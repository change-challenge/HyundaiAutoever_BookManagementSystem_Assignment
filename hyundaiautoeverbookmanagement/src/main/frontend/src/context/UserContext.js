import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  useMemo,
} from 'react'
import axios from 'axios'

const UserContext = createContext({
  userInfo: null,
  setUserInfo: () => {},
})

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
          console.log('context 안에 ', userInfo)
          setUserInfo(userInfo)
        }
      } catch (error) {
        console.error('Failed to fetch user info', error)
      }
    }

    fetchUserInfo()
  }, [])

  const value = useMemo(
    () => ({ userInfo, setUserInfo }),
    [userInfo, setUserInfo]
  )

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>
}

export function useUserState() {
  const context = useContext(UserContext)
  if (context === null) {
    throw new Error('useUserState must be used within a UserProvider')
  }
  return context.userInfo
}

export function useUserDispatch() {
  const context = useContext(UserContext)
  if (context === null) {
    throw new Error('useUserDispatch must be used within a UserProvider')
  }
  return context.setUserInfo
}
