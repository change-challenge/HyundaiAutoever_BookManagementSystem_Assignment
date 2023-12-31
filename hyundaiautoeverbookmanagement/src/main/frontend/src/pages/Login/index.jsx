import { useNavigate } from 'react-router-dom'
import { LabelInput, Title } from '../../components/index'
import * as S from './style'
import React, { useState, useContext, useEffect } from 'react'
import { useTokenDispatch, IsLoginContext } from '../../context/IsLoginContext'
import { fetchUserInfo } from '../../context/UserContext'
import { SnackbarContext } from '../../context/SnackbarContext'
import apiClient from '../../axios'
import { useAlert } from '../../context/AlertContext'

function Login() {
  const showAlert = useAlert()
  const setToken = useTokenDispatch()
  const { setSnackbar } = useContext(SnackbarContext)
  const { setIsLogin } = useContext(IsLoginContext)
  const [email, setEmail] = useState('')
  const [pw, setPw] = useState('')
  const [emailValid, setEmailValid] = useState(false)
  const [pwValid, setPwValid] = useState(false)
  const [notAllow, setNotAllow] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      if (userInfo) {
        showAlert('이미 로그인을 하셨습니다.')
        navigate('/')
      }
    }
    getUserInfo()
  }, [])

  const handleEmail = e => {
    setEmail(e.target.value)
    const regex =
      /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i
    if (regex.test(email)) {
      setEmailValid(true)
    } else {
      setEmailValid(false)
    }
  }

  const handlePassword = e => {
    const value = e.target.value
    setPw(value)
    const regex =
      /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/
    const isValid = value.length >= 8 && regex.test(value)
    setPwValid(isValid)
  }

  const onClickConfirmButton = async () => {
    try {
      const response = await apiClient.post('/api/auth/login', {
        email,
        password: pw,
      })
      console.log('onClickConfirmButton : ', response.data)
      const accessToken = response.data.accessToken
      localStorage.setItem('token', accessToken) // 추가: localStorage에 토큰 저장
      apiClient.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${accessToken}` // 추가: 헤더에 Access Token 설정
      setToken(accessToken)
      setIsLogin(true)
      setSnackbar({
        open: true,
        severity: 'success',
        message: '로그인 성공!',
      })

      fetchUserInfo()
      navigate('/')
    } catch (error) {
      setPw('')
      setPwValid(false)
      showAlert('로그인 정보가 올바르지 않거나 가입된 회원이 아닙니다.')
    }
  }

  const onClickSignUpButton = () => {
    navigate('/signUp')
  }

  useEffect(() => {
    if (emailValid && pwValid) {
      setNotAllow(false)
      return
    }
    setNotAllow(true)
  }, [emailValid, pwValid])

  return (
    <>
      <S.Container>
        <S.InnerContainer>
          <Title text="로그인" />
          <S.ContentWrap>
            <LabelInput
              type="text"
              placeholder="아이디"
              value={email}
              onChange={handleEmail}
              width="100%"
            />
            {!emailValid && email.length > 0 && (
              <S.ErrorMessageWrap>
                <div>올바른 이메일을 입력해주세요.</div>
              </S.ErrorMessageWrap>
            )}
            <LabelInput
              type="password"
              placeholder="비밀번호"
              value={pw}
              onChange={handlePassword}
              width="100%"
            />
            {!pwValid && pw.length > 0 && (
              <S.ErrorMessageWrap>
                <div>영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.</div>
              </S.ErrorMessageWrap>
            )}
          </S.ContentWrap>
          <div style={{ marginTop: '50px' }}>
            <S.LoginButton onClick={onClickConfirmButton} disabled={notAllow}>
              로그인
            </S.LoginButton>
            <S.SignUpButton onClick={onClickSignUpButton}>
              회원가입
            </S.SignUpButton>
          </div>
        </S.InnerContainer>
      </S.Container>
    </>
  )
}

export default Login
