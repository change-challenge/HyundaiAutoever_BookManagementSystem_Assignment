import { useState } from 'react'
import { Text, LabelInput, Title } from '../../components/index'
import * as S from './style'
import Button from '@mui/material/Button'
import Stack from '@mui/material/Stack'
import { LinkButtonWrapper } from '../../components/Header/style'

export default function WishBook() {
  const [wishBookName, setWishBookName] = useState('')
  const [wishBookArthor, setWishBookArthor] = useState('')
  const [wishBookPublisher, setWishBookPublisher] = useState('')
  const [wishBookCreateDate, setWishBookCreateDate] = useState('')
  const [wishBookISBN, setWishBookISBN] = useState('')

  const onClickWishBook = () => {
    alert('등록하겠습니까?')
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
              width="300"
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
              value={wishBookArthor}
              onChange={e => setWishBookArthor(e.target.value)}
              marginTop="0"
              width="200"
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
              width="200"
            />
          </S.LabelWrapper>
          <S.LabelWrapper>
            <S.LabelTitleWrapper>
              <Text
                text="발행연도"
                color={({ theme }) => theme.colors.grey5}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.LabelTitleWrapper>

            <LabelInput
              type="text"
              placeholder=""
              value={wishBookCreateDate}
              onChange={e => setWishBookCreateDate(e.target.value)}
              marginTop="0"
              width="200"
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
              width="200"
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
