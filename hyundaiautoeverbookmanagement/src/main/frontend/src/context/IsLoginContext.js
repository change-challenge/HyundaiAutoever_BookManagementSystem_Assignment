import React, {
  createContext,
  useEffect,
  useContext,
  useState,
  useMemo,
} from 'react'

const initialToken = localStorage.getItem('token') || ''

export function useTokenState() {
  const context = useContext(IsLoginContext)
  if (!context) {
    throw new Error('Cannot find IsLoginProvider')
  }
  return context.token
}

export function useTokenDispatch() {
  const context = useContext(IsLoginContext)
  if (!context) {
    throw new Error('Cannot find IsLoginProvider')
  }
  return context.setToken
}

export const TokenContext = createContext({
  token: initialToken,
  setIsToken: () => {},
})

export const IsLoginContext = createContext({
  isLogin: false,
  setLogin: () => {},
})

export function IsLoginProvider({ children }) {
  const [isLogin, setIsLogin] = useState(false)
  const [token, setToken] = useState(localStorage.getItem('token') || '')

  useEffect(() => {
    if (token) {
      setIsLogin(true)
    } else {
      setIsLogin(false)
    }
  }, [token])

  const value = useMemo(
    () => ({ isLogin, setIsLogin, token, setToken }),
    [isLogin, setIsLogin, token, setToken]
  )

  return (
    <IsLoginContext.Provider value={value}>{children}</IsLoginContext.Provider>
  )
}

export function useIsLoginState() {
  const context = useContext(IsLoginContext)
  if (!context) {
    throw new Error('Cannot find IsLoginProvider')
  }
  return context.isLogin
}

export function useIsLoginDispatch() {
  const context = useContext(IsLoginContext)
  if (!context) {
    throw new Error('Cannot find IsLoginProvider')
  }
  return context.setIsLogin
}
