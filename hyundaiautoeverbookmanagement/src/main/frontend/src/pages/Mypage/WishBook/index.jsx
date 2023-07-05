import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'

const MypageWishBook = () => {
  return (
    <>
      <S.RentCountWrapper>
        <Text
          text="신청현황건수 : 5건"
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
              text={`신청일 : 2023.06.22
			상태 : 처리중`}
              fontSize={({ theme }) => theme.fontSize.sz16}
            />
          </S.RentDetailWrapper>
        </S.RentInfoWrapper>
      </S.RentDetailContainer>
    </>
  )
}

export default MypageWishBook
