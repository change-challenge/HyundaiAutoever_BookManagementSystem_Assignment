import React from 'react'
import Stack from '@mui/material/Stack'
import Button from '@mui/material/Button'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import axios from 'axios'

const MypageRent = ({ user }) => {
  return (
    <>
      <S.RentCountWrapper>
        <Text
          text="대출현황건수 : 5건"
          textColor={({ theme }) => theme.colors.black}
        />
      </S.RentCountWrapper>

      <S.RentDetailContainer>
        <S.RentInfoWrapper>
          <S.RentTitleWrapper>
            <Text
              text="책 제목"
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz20}
            />
          </S.RentTitleWrapper>
          <S.RentDetailWrapper>
            <Text
              text={`대출일 : 2023.06.22 | 반납예정일 : 2023.06.27
			대출상태 : 대출중`}
              fontSize={({ theme }) => theme.fontSize.sz16}
            />
          </S.RentDetailWrapper>
        </S.RentInfoWrapper>

        <S.ButtonWrapper>
          <Stack spacing={2}>
            <Button variant="contained" size="large">
              반납하기
            </Button>
            <Button variant="contained" size="large">
              연장하기
            </Button>
          </Stack>
        </S.ButtonWrapper>
      </S.RentDetailContainer>
      <S.RentDetailContainer>
        <S.RentInfoWrapper>
          <S.RentTitleWrapper>
            <Text
              text="책 제목"
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz20}
            />
          </S.RentTitleWrapper>
          <S.RentDetailWrapper>
            <Text
              text={`대출일 : 2023.06.22 | 반납예정일 : 2023.06.27
			대출상태 : 대출중`}
              fontSize={({ theme }) => theme.fontSize.sz16}
            />
          </S.RentDetailWrapper>
        </S.RentInfoWrapper>

        <S.ButtonWrapper>
          <Stack spacing={2}>
            <Button variant="contained" size="large">
              반납하기
            </Button>
            <Button variant="contained" size="large">
              연장하기
            </Button>
          </Stack>
        </S.ButtonWrapper>
      </S.RentDetailContainer>
    </>
  )
}

export default MypageRent
