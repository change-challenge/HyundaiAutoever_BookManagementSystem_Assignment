import BasicTabs from './Tabs'
import * as S from './style'
import CircularProgress from '@mui/material/CircularProgress'
import Box from '@mui/material/Box'
import { Text } from '../../../components/index'
import { useUserState } from '../../../context/UserContext'

const Mypage = () => {
  const userInfo = useUserState()

  if (!userInfo) {
    return (
      <Box sx={{ display: 'flex', width: 300, height: 300 }}>
        <CircularProgress />
      </Box>
    )
  }

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
            text={`${userInfo.name}님, 반갑습니다!`}
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
