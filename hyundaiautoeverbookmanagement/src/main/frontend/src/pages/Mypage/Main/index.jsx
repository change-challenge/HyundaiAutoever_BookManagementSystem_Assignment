import BasicTabs from './Tabs'
import * as S from './style'
import CircularProgress from '@mui/material/CircularProgress'
import Box from '@mui/material/Box'
import { Text } from '../../../components/index'
import { fetchUserInfo } from '../../../context/UserContext'
import { useState, useEffect } from 'react'
import Pagination from '@mui/material/Pagination'

const Mypage = () => {
  const [user, setUser] = useState(null)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
    }
    getUserInfo()
  }, [])

  console.log('user : ', user)
  if (!user) {
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
            text={`${user.name}님, 반갑습니다!`}
            color={({ theme }) => theme.colors.black}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz32}
          />
        </S.MypageGreetingWrapper>
        <S.MypageTabsWrapper>
          <BasicTabs user={user} />
        </S.MypageTabsWrapper>
      </S.InnerWrapper>
    </S.MypageContainer>
  )
}

export default Mypage
