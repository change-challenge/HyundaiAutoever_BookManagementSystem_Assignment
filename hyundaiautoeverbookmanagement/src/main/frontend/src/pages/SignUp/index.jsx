import { useNavigate } from 'react-router-dom'
import { Title, LabelInput } from '../../components/index'
import * as S from './style'
import React, { useState, useContext } from 'react'
import { useEffect } from 'react'
import { fetchUserInfo } from '../../context/UserContext'
import { SnackbarContext } from '../../context/SnackbarContext'
import apiClient from '../../axios'
import { useAlert } from '../../context/AlertContext'
import { useConfirm } from '../../context/ConfirmContext'

function SignUp() {
  const showConfirm = useConfirm()
  const showAlert = useAlert()
  const { setSnackbar } = useContext(SnackbarContext)
  const [email, setEmail] = useState('')
  const [pw, setPw] = useState('')
  const [pwSame, setPwSame] = useState('')
  const [name, setName] = useState('')

  const [emailValid, setEmailValid] = useState(null)

  const [nameValid, setNameValid] = useState(false)
  const [pwValid, setPwValid] = useState(false)
  const [pwSameValid, setPwSameValid] = useState(false)
  const [notAllow, setNotAllow] = useState(true)
  const [emailExists, setEmailExists] = useState(false)

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
    const currentEmail = e.target.value
    setEmail(currentEmail)
    const regex =
      /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i

    if (!currentEmail) {
      setEmailValid(null)
      setEmailExists(false)
      return
    }

    if (regex.test(currentEmail)) {
      setEmailValid(true)

      apiClient
        .get(`/api/auth/exist?email=${currentEmail}`)
        .then(response => {
          if (response.status === 200) {
            setEmailExists(false)
          }
        })
        .catch(() => {
          setEmailExists(true)
        })
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

  const submitSignup = data => {
    apiClient
      .post('/api/auth/signup', data)
      .then(response => {
        if (response.status === 200) {
          navigate('/login')
          setSnackbar({
            open: true,
            severity: 'success',
            message: '회원가입 성공!',
          })
        } else {
          showAlert('가입불가')
        }
      })
      .catch(error => {
        showAlert('아이디가 중복되었습니다.')
      })
  }
  const onClickConfirmButton = () => {
    if (emailValid && pwValid && pwSameValid && nameValid) {
      const data = {
        email: email,
        name: name,
        password: pw,
      }
      showConfirm('회원가입을 하시겠습니까?', () => submitSignup(data))
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

  const renderEmailValidationMessage = () => {
    // 이메일 중복 체크를 했는데, 중복된 경우
    if (emailExists) {
      return (
        <S.ErrorMessageWrap>
          <div>이메일이 중복되었습니다.</div>
        </S.ErrorMessageWrap>
      )
    }

    if (emailValid === false) {
      return (
        <S.ErrorMessageWrap>
          <div>올바른 이메일을 입력해주세요.</div>
        </S.ErrorMessageWrap>
      )
    }

    if (emailValid === true && !emailExists) {
      return (
        <S.OkMessageWrap>
          <div>사용 가능한 이메일입니다.</div>
        </S.OkMessageWrap>
      )
    }
  }

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
            {renderEmailValidationMessage()}
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
