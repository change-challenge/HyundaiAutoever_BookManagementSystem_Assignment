import React from 'react'
import Stack from '@mui/material/Stack'
import Button from '@mui/material/Button'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState, useContext } from 'react'
import axios from 'axios'
import { SnackbarContext } from '../../../context/SnackbarContext'

const MypageRent = ({ user }) => {
  const { setSnackbar } = useContext(SnackbarContext)
  const [rents, setRents] = useState([])

  const fetchRents = async () => {
    const response = await axios.get(`/api/rent/current`, {
      params: {
        userEmail: user.email,
      },
    })
    setRents(response.data)
    console.log('response.data : ', response.data)
  }

  const handleReturnClick = copyId => {
    if (!user) {
      alert('로그인이 필요한 기능입니다!')
    }
    console.log('copyId : ', copyId)

    const confirm = window.confirm('도서를 반납하시겠습니까?')
    if (confirm) {
      makeReturn(copyId)
    }
  }

  const makeReturn = async copyId => {
    const response = await axios
      .post(`/api/return/${copyId}`, {
        copyId: copyId,
        userEmail: user.email,
      })
      .then(response => {
        console.log('makeReturn: ', response.data)
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'success',
          message: '도서 반납 성공!',
        })
      })
      .catch(error => {
        if (error.response) {
          setSnackbar({
            open: true,
            severity: 'error',
            message: '도서를 반납할 수 없습니다.',
          })
          if (error.response.status === 400) {
            console.error('Client error: ', error.response.data)
          } else {
            console.error('Server error: ', error.response.data)
          }
        } else if (error.request) {
          console.error('No response: ', error.request)
        } else {
          console.error('Error: ', error.message)
        }
      })
  }

  useEffect(() => {
    fetchRents()
  }, [])

  return (
    <>
      <S.RentCountWrapper>
        <Text
          text={`대출현황건수 : ${rents.length}건`}
          textColor={({ theme }) => theme.colors.black}
        />
      </S.RentCountWrapper>
      {rents.map((rent, index) => (
        <S.RentDetailContainer key={index}>
          <S.RentInfoWrapper>
            <S.RentTitleWrapper>
              <Text
                text={`${rent.title}`}
                fontWeight={'bold'}
                fontSize={({ theme }) => theme.fontSize.sz20}
              />
            </S.RentTitleWrapper>
            <S.RentDetailWrapper>
              <Text
                text={`대출일 : ${rent.rentStartDate} | 반납예정일 : ${rent.rentEndDate}
			  대출상태 : 대출중`}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.RentDetailWrapper>
          </S.RentInfoWrapper>

          <S.ButtonWrapper>
            <Stack spacing={2}>
              <Button
                variant="contained"
                size="large"
                onClick={() => handleReturnClick(rent.copyId)}
              >
                반납하기
              </Button>
              <Button variant="contained" size="large">
                연장하기
              </Button>
            </Stack>
          </S.ButtonWrapper>
        </S.RentDetailContainer>
      ))}
    </>
  )
}

export default MypageRent
