import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'

const MypageRentHistory = () => {
  return (
    <>
      <S.RentCountWrapper>
        <Text
          text="대출이력건수 : 5건"
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
      </S.RentDetailContainer>
    </>
  )
}

export default MypageRentHistory
