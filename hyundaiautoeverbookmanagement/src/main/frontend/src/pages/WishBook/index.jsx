import { useState, useEffect } from 'react'
import { Text, LabelInput, Title } from '../../components/index'
import * as S from './style'
import Button from '@mui/material/Button'
import Stack from '@mui/material/Stack'
import axios from 'axios'
import { fetchUserInfo } from '../../context/UserContext'
import { useNavigate } from 'react-router-dom' // useNavigate로 변경

export default function WishBook() {
  const [wishBookName, setWishBookName] = useState('')
  const [wishBookAuthor, setWishBookAuthor] = useState('')
  const [wishBookPublisher, setWishBookPublisher] = useState('')
  const [wishBookISBN, setWishBookISBN] = useState('')
  const navigate = useNavigate() // useNavigate로 변경
  const [user, setUser] = useState(null)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
      console.log('WishBook userInfo ', userInfo)
    }
    getUserInfo()
  }, [])

  const onClickWishBook = () => {
    if (!wishBookName || !wishBookAuthor || !wishBookPublisher) {
      alert('희망도서명, 저자, 발행자는 필수 항목입니다.')
      return
    }
    const confirm = window.confirm('희망도서를 신청하시겠습니까?')
    if (confirm) {
      const currentDate = new Date()
      const isoDate = currentDate.toISOString()

      const requestData = {
        title: wishBookName,
        author: wishBookAuthor,
        publisher: wishBookPublisher,
        ISBN: wishBookISBN,
        userEmail: user.email,
        wishDate: isoDate,
      }

      console.log('requestData : ', requestData)
      axios
        .post('/api/wishbook/create', requestData)
        .then(response => {
          console.log(response.data) // 성공적인 응답 처리
          alert('성공적으로 희망도서신청을 하였습니다.')
          navigate('/')
        })
        .catch(error => {
          alert('잠시 뒤에 다시 신청해주세요.')
          console.error(error) // 오류 처리
        })
    }
  }

  return (
    <S.InnerContainer>
      <Title text="희망도서 정보" />
      <S.ContentWrap>
        <Stack spacing={3}>
          <S.LabelWrapper>
            <S.LabelTitleWrapper>
              <S.EssentialMark>*</S.EssentialMark>
              <Text
                text="희망도서명"
                color={({ theme }) => theme.colors.grey5}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.LabelTitleWrapper>
            <LabelInput
              type="text"
              placeholder=""
              value={wishBookName}
              onChange={e => setWishBookName(e.target.value)}
              marginTop="0"
              marginRight="50"
              width="300px"
            />
            <Button size="large" variant="contained" onClick={onClickWishBook}>
              검색
            </Button>
          </S.LabelWrapper>
          <S.LabelWrapper>
            <S.LabelTitleWrapper>
              <S.EssentialMark>*</S.EssentialMark>
              <Text
                text="저자"
                color={({ theme }) => theme.colors.grey5}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.LabelTitleWrapper>
            <LabelInput
              type="text"
              placeholder=""
              value={wishBookAuthor}
              onChange={e => setWishBookAuthor(e.target.value)}
              marginTop="0"
              width="200px"
            />
          </S.LabelWrapper>
          <S.LabelWrapper>
            <S.LabelTitleWrapper>
              <S.EssentialMark>*</S.EssentialMark>
              <Text
                text="발행자"
                color={({ theme }) => theme.colors.grey5}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.LabelTitleWrapper>
            <LabelInput
              type="text"
              placeholder=""
              value={wishBookPublisher}
              onChange={e => setWishBookPublisher(e.target.value)}
              marginTop="0"
              width="200px"
            />
          </S.LabelWrapper>
          <S.LabelWrapper>
            <S.LabelTitleWrapper>
              <Text
                text="ISBN"
                color={({ theme }) => theme.colors.grey5}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.LabelTitleWrapper>
            <LabelInput
              type="text"
              placeholder=""
              value={wishBookISBN}
              onChange={e => setWishBookISBN(e.target.value)}
              marginTop="0"
              width="200px"
            />
          </S.LabelWrapper>
        </Stack>
      </S.ContentWrap>
      <S.ButtonWrapper>
        <Button size="large" variant="contained" onClick={onClickWishBook}>
          확인
        </Button>
      </S.ButtonWrapper>
      <div style={{ marginTop: '50px', marginBottom: '50px' }}></div>
    </S.InnerContainer>
  )
}
