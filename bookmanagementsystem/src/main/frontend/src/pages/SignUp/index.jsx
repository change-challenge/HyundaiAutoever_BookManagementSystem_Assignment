import { useNavigate } from 'react-router-dom'
import { Title, LabelInput } from '../../components/index'
import * as S from './style'
import React, { useState } from 'react'
import { useEffect } from 'react'

function SignUp() {
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
    const isValid = value.length > 2
    setNameValid(isValid)
  }

  const onClickConfirmButton = () => {
    alert('로그인에 성공했습니다.')
  }

  const onClickSignUpButton = () => {
    navigate('/login')
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
            <S.ToLoginButton onClick={onClickSignUpButton}>
              로그인 페이지로
            </S.ToLoginButton>
          </div>
        </S.InnerContainer>
      </S.Container>
    </>
  )
}

export default SignUp
