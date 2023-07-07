import React, { createContext, useContext, useState, useMemo } from 'react'

const userId = sessionStorage.getItem('id')
const token = sessionStorage.getItem('token')
//const initialToken = localStorage.getItem('token') || ''
const initialToken = sessionStorage.getItem('token') || ''

export const TokenContext = createContext({
  token: initialToken,
  setToken: () => {},
})

export function TokenProvider({ children }) {
  const [token, setToken] = useState(initialToken)

  //  const updateToken = newToken => {
  //    setToken(newToken)
  //    localStorage.setItem('token', newToken)
  //  }
  const updateToken = newToken => {
    setToken(newToken)
    sessionStorage.setItem('token', newToken)
  }

  const value = useMemo(() => ({ token, setToken: updateToken }), [token])

  return <TokenContext.Provider value={value}>{children}</TokenContext.Provider>
}

export function useTokenState() {
  const context = useContext(TokenContext)
  if (!context) {
    throw new Error('Cannot find TokenProvider')
  }
  return context.token
}

export function useTokenDispatch() {
  const context = useContext(TokenContext)
  if (!context) {
    throw new Error('Cannot find TokenProvider')
  }
  return context.setToken
}

export const IsLoginContext = createContext({
  isLogin: userId !== null && token !== null ? true : false,
})

export function IsLoginProvider({ children }) {
  const [isLogin, setIsLogin] = useState(
    userId !== null && token !== null ? true : false
  )
  // useMemo로 캐싱하지 않으면 value가 바뀔 때마다 state를 사용하는 모든 컴포넌트가 매번 리렌더링됨
  const value = useMemo(() => ({ isLogin, setIsLogin }), [isLogin, setIsLogin])
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
