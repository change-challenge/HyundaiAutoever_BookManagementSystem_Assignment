import React from 'react'
import BasicTabs from './Tabs'
import * as S from './style'
import { Text } from '../../../components/index'

const Mypage = () => {
  return (
    <S.MypageContainer>
      <S.InnerWrapper>
        <S.MypageTitleWrapper>
          <Text
            text="내 서재"
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz32}
          />
        </S.MypageTitleWrapper>
        <S.MypageGreetingWrapper>
          <Text
            text="000님, 반갑습니다!"
            color={({ theme }) => theme.colors.black}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz32}
          />
        </S.MypageGreetingWrapper>
        <S.MypageTabsWrapper>
          <BasicTabs />
        </S.MypageTabsWrapper>
      </S.InnerWrapper>
    </S.MypageContainer>
  )
}

export default Mypage
