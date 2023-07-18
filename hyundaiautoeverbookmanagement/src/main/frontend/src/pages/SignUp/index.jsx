import { useNavigate } from 'react-router-dom'
import { Title, LabelInput } from '../../components/index'
import * as S from './style'
import React, { useState, useContext } from 'react'
import { useEffect } from 'react'
import { fetchUserInfo } from '../../context/UserContext'
import { SnackbarContext } from '../../context/SnackbarContext'

function SignUp() {
  const { setSnackbar } = useContext(SnackbarContext)
  const [email, setEmail] = useState('')
  const [pw, setPw] = useState('')
  const [pwSame, setPwSame] = useState('')
  const [name, setName] = useState('')

  const [emailValid, setEmailValid] = useState(false)
  const [nameValid, setNameValid] = useState(false)
  const [pwValid, setPwValid] = useState(false)
  const [pwSameValid, setPwSameValid] = useState(false)

  const [notAllow, setNotAllow] = useState(true)

  const navigate = useNavigate()

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      if (userInfo) {
        alert('이미 로그인을 하셨습니다.')
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

  const handlePasswordSame = e => {
    const value = e.target.value
    setPwSame(value)
    const isValid = value === pw
    setPwSameValid(isValid)
  }
  const handleName = e => {
    const value = e.target.value
    setName(value)
    const isValid = value.length >= 2
    setNameValid(isValid)
  }

  const onClickConfirmButton = () => {
    // 회원가입 버튼 클릭 시 호출되는 함수
    if (emailValid && pwValid && pwSameValid && nameValid) {
      // 필수 입력 사항이 모두 입력되었을 때 API 호출
      // fetch 또는 axios를 사용하여 백엔드 API로 데이터 전송
      const currentDate = new Date()
      const isoDate = currentDate.toISOString()
      const data = {
        email: email,
        name: name,
        password: pw,
        registDate: isoDate,
      }

      const confirm = window.confirm('회원가입을 하시겠습니까?')
      if (confirm) {
        fetch('/api/auth/signup', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        })
          .then(response => {
            if (response.ok) {
              // 회원가입 성공 처리
              navigate('/login')
              setSnackbar({
                open: true,
                severity: 'success',
                message: '회원가입 성공!',
              })
            } else {
              alert('가입불가')
            }
          })
          .catch(error => {
            // 네트워크 에러 처리
            console.error('Error:', error)
          })
      }
    }
  }

  const onClickToLoginButton = () => {
    navigate('/api/login')
  }

  useEffect(() => {
    if (emailValid && pwValid && pwSameValid && nameValid) {
      setNotAllow(false)
      return
    }
    setNotAllow(true)
  }, [emailValid, pwValid, pwSameValid, nameValid])

  return (
    <>
      <S.Container>
        <S.InnerContainer>
          <Title text="회원가입" />
          <S.ContentWrap>
            <LabelInput
              type="text"
              placeholder="아이디"
              value={email}
              onChange={handleEmail}
              width="100%"
            />
            {emailValid
              ? email.length > 0 && (
                  <S.OkMessageWrap>
                    <div>사용 가능한 이메일입니다.</div>
                  </S.OkMessageWrap>
                )
              : email.length > 0 && (
                  <S.ErrorMessageWrap>
                    <div>올바른 이메일을 입력해주세요.</div>
                  </S.ErrorMessageWrap>
                )}
            <LabelInput
              type="text"
              placeholder="이름"
              value={name}
              onChange={handleName}
              width="100%"
            />
            {name.length > 0 && name.length < 2 && (
              <S.ErrorMessageWrap>
                <div>이름은 2자 이상만 가능합니다.</div>
              </S.ErrorMessageWrap>
            )}
            <LabelInput
              type="password"
              placeholder="비밀번호"
              value={pw}
              onChange={handlePassword}
              width="100%"
            />
            {pwValid
              ? pw.length > 0 && (
                  <S.OkMessageWrap>
                    <div>사용 가능한 비밀번호입니다.</div>
                  </S.OkMessageWrap>
                )
              : pw.length > 0 && (
                  <S.ErrorMessageWrap>
                    <div>영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.</div>
                  </S.ErrorMessageWrap>
                )}
            <LabelInput
              type="password"
              placeholder="비밀번호 재확인"
              value={pwSame}
              onChange={handlePasswordSame}
              width="100%"
            />
            {pwSameValid
              ? pwSame.length > 0 && (
                  <S.OkMessageWrap>
                    <div>비밀번호가 일치합니다.</div>
                  </S.OkMessageWrap>
                )
              : pwSame.length > 0 && (
                  <S.ErrorMessageWrap>
                    <div>비밀번호가 일치하지 않습니다.</div>
                  </S.ErrorMessageWrap>
                )}
          </S.ContentWrap>
          <div style={{ marginTop: '50px' }}>
            <S.SignUpButton onClick={onClickConfirmButton} disabled={notAllow}>
              회원가입
            </S.SignUpButton>
            <S.ToLoginButton onClick={onClickToLoginButton}>
              로그인 페이지로
            </S.ToLoginButton>
          </div>
        </S.InnerContainer>
      </S.Container>
    </>
  )
}

export default SignUp
