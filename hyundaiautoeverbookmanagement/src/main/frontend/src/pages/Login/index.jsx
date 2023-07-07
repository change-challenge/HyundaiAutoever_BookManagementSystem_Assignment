import { useNavigate } from 'react-router-dom'
import { LabelInput, Title } from '../../components/index'
import * as S from './style'
import React, { useState, useContext } from 'react'
import { useEffect } from 'react'
import { IsLoginContext } from '../../context/IsLoginContext'
import axios from 'axios' // axios import 추가

function Login() {
  const { setIsLogin } = useContext(IsLoginContext)
  const [email, setEmail] = useState('')
  const [pw, setPw] = useState('')

  const [emailValid, setEmailValid] = useState(false)
  const [pwValid, setPwValid] = useState(false)
  const [notAllow, setNotAllow] = useState(true)
  const navigate = useNavigate()

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

  const onClickConfirmButton = () => {
    // axios를 사용하여 POST 요청 보내기
    axios
      .post('/auth/login', { email, password: pw })
      .then(response => {
        alert('로그인에 성공했습니다.')
        setIsLogin(true)
      })
      .catch(error => {
        // 로그인 실패 시 에러 처리
        console.error('로그인 실패:', error)
      })
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
