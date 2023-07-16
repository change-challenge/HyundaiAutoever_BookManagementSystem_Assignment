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

export async function fetchUserInfo() {
  try {
    const token = localStorage.getItem('token')
    if (token) {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.get('/api/member/me', config)
      const userInfo = response.data
      console.log('Token! GET!')
      return userInfo
    }
    return null
  } catch (error) {
    console.error('Failed to fetch member info', error)
    localStorage.clear()
    return null
  }
}

export function UserProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUserInfo(userInfo)
    }
    getUserInfo()
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
