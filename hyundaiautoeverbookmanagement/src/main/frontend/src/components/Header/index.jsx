import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Logo from '../../assets/logo.svg'
import * as S from './style'
import { Text } from '../index'
import {
  useIsLoginState,
  useIsLoginDispatch,
} from '../../context/IsLoginContext'
import axios from 'axios'

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
  const navigate = useNavigate()
  const isLogin = useIsLoginState()
  const setIsLogin = useIsLoginDispatch()

  const handleLogout = async () => {
    try {
      await axios.post('/auth/logout')
      // 로그아웃 성공 후 필요한 동작 수행
      setIsLogin(false) // 클라이언트 측에서 인증 상태 해제
      navigate('/') // 로그아웃 후 리다이렉트 등의 작업 수행
    } catch (error) {
      // 로그아웃 요청 실패 처리
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
                    onClick={handleLogout} // 로그아웃 버튼 클릭 시 로그아웃 처리 함수 호출
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
