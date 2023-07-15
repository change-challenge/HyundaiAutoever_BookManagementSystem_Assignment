import React from 'react'
import Stack from '@mui/material/Stack'
import Button from '@mui/material/Button'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import axios from 'axios'

const MypageRent = ({ user }) => {
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
        <S.RentDetailContainer>
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
              <Button variant="contained" size="large">
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
