import BasicTabs from './Tabs'
import * as S from './style'
import Button from '@mui/material/Button'
import ButtonGroup from '@mui/material/ButtonGroup'
import { Text } from '../../../components/index'
import { fetchUserInfo } from '../../../context/UserContext'
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import apiClient from '../../../axios'

const Mypage = () => {
  const navigate = useNavigate()
  const [user, setUser] = useState(null)
  const [isAdmin, setIsAdmin] = useState(false)

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
      if (!userInfo) {
        alert('로그인이 필요한 기능입니다.')
        navigate('/')
      }
      const result = await checkAdmin()
      console.log('result : ', result)
    }
    getUserInfo()
  }, [])

  const handleWishBook = () => {
    navigate('/wishbook')
  }

  const handleAdmin = () => {
    navigate('/admin')
  }

  const checkAdmin = async () => {
    try {
      const response = await apiClient.get('/api/admin/allow')

      console.log('response : ', response)
      if (response.status !== 200) {
        throw new Error('Not an admin')
      }
      setIsAdmin(true)
    } catch (error) {
      console.error('Error:', error)
      return null
    }
  }
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
            <ButtonGroup variant="outlined" aria-label="outlined button group">
              <Button onClick={handleWishBook}>희망도서 신청하기</Button>
              {isAdmin && (
                <Button onClick={handleAdmin}>관리자 페이지로 가기</Button>
              )}
            </ButtonGroup>
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
