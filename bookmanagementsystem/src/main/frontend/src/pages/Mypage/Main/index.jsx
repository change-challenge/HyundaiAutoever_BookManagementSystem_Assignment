import React from 'react'
import BasicTabs from './Tabs'
import * as S from './style'
import { Text, SearchBar } from '../../../components/index'

const Mypage = () => {
  const handleSubmit = () => {
    console.log('마이 페이지 검색창')
  }
  return (
    <S.MypageContainer>
      <S.InnerWrapper>
        <S.SearchContainer>
          <SearchBar width="500px" height="50px" onSubmit={handleSubmit} />
        </S.SearchContainer>
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
