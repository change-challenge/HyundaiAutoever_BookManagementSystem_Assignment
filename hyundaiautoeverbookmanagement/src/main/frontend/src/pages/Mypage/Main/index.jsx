import BasicTabs from './Tabs'
import * as S from './style'
import CircularProgress from '@mui/material/CircularProgress'
import Box from '@mui/material/Box'
import { Text } from '../../../components/index'
import { fetchUserInfo } from '../../../context/UserContext'
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'

const Mypage = () => {
  const navigate = useNavigate()
  const [user, setUser] = useState(null)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
      if (!userInfo) {
        alert('로그인이 필요한 기능입니다.')
        navigate('/')
      }
    }
    getUserInfo()
  }, [])

  console.log('user : ', user)

  return (
    user && (
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
  )
}

export default Mypage
