import React, { useContext } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Logo from '../../assets/logo.svg'
import * as S from './style'
import { Text } from '../index'
import {
  useIsLoginState,
  useIsLoginDispatch,
  useTokenDispatch,
} from '../../context/IsLoginContext'
import { useUserDispatch } from '../../context/UserContext'
import { SnackbarContext } from '../../context/SnackbarContext'
import apiClient from '../../axios'

const basicNavMenu = [
  {
    linkTo: '/signup',
    text: '회원가입',
  },
  {
    linkTo: '/login',
    text: '로그인',
  },
]

function Header() {
  const { setSnackbar } = useContext(SnackbarContext)

  const navigate = useNavigate()
  const isLogin = useIsLoginState()
  const setUserInfo = useUserDispatch()
  const setIsLogin = useIsLoginDispatch()
  const setToken = useTokenDispatch() // 추가: 토큰 디스패치 함수

  const handleLogout = async () => {
    try {
      await apiClient.post('/api/auth/logout')
      setIsLogin(false)
      setUserInfo(null)
      setToken(null)
      localStorage.removeItem('token')
      setSnackbar({
        open: true,
        severity: 'error',
        message: '로그아웃 성공!',
      })
      navigate('/')
      window.location.reload()
    } catch (error) {
      console.error('로그아웃 실패:', error)
    }
  }

  const logoClick = () => {
    navigate('/')
  }

  return (
    <S.HeaderContainer>
      <S.Layout>
        <S.LogoWrapper onClick={logoClick}>
          <img
            src={Logo}
            alt="logo"
            width="120px"
            height="36px"
            draggable={false}
          />
          <Text
            text="현대오토에버 도서관리시스템"
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz10}
            text-align={'center'}
          />
        </S.LogoWrapper>
        <S.NavWrapper>
          <S.LinkButtonWrapper>
            {isLogin ? (
              <>
                <li>
                  <Link
                    style={{
                      marginRight: '2rem',
                      padding: '0 10px',
                      lineHeight: '1em',
                      height: '1em',
                    }}
                    to="/mypage"
                  >
                    <span
                      style={{
                        display: 'inline-block',
                      }}
                    >
                      <Text
                        text="마이페이지"
                        color={({ theme }) => theme.colors.main}
                        fontSize={({ theme }) => theme.fontSize.sz16}
                        fontFamily={'Roboto'}
                        cursor={'pointer'}
                      />
                    </span>
                  </Link>
                </li>
                <li>
                  <span
                    style={{
                      padding: '0 10px',
                      lineHeight: '1em',
                      height: '1em',
                      display: 'inline-block',
                    }}
                    onClick={handleLogout}
                    role="button"
                  >
                    <Text
                      text="로그아웃"
                      color={({ theme }) => theme.colors.main}
                      fontSize={({ theme }) => theme.fontSize.sz16}
                      fontFamily={'Roboto'}
                      cursor={'pointer'}
                    />
                  </span>
                </li>
              </>
            ) : (
              basicNavMenu.map(menu => (
                <li key={menu.linkTo}>
                  <Link
                    style={{
                      marginRight: '2rem',
                      padding: '0 10px',
                      lineHeight: '1em',
                      height: '1em',
                    }}
                    to={menu.linkTo}
                  >
                    <span
                      style={{
                        display: 'inline-block',
                      }}
                    >
                      <Text
                        text={menu.text}
                        color={({ theme }) => theme.colors.main}
                        fontSize={({ theme }) => theme.fontSize.sz16}
                        fontFamily={'Roboto'}
                        cursor={'pointer'}
                      />
                    </span>
                  </Link>
                </li>
              ))
            )}
          </S.LinkButtonWrapper>
        </S.NavWrapper>
      </S.Layout>
    </S.HeaderContainer>
  )
}

export default Header
