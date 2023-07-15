import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import axios from 'axios'

const MypageRentHistory = ({ user }) => {
  const [rents, setRents] = useState([])

  const fetchRents = async () => {
    const response = await axios.get(`/api/rent/history`, {
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
          text={`대출이력건수 : ${rents.length}건`}
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
              {rent.rentReturnedDate === null ? (
                <Text
                  text={`대출일 : ${rent.rentStartDate} | 반납예정일 : ${rent.rentEndDate}
			대출상태 : 대출중`}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              ) : (
                <Text
                  text={`대출일 : ${rent.rentStartDate} | 반납일 : ${rent.rentReturnedDate}
			  대출상태 : 반납됨`}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              )}
            </S.RentDetailWrapper>
          </S.RentInfoWrapper>
        </S.RentDetailContainer>
      ))}
    </>
  )
}

export default MypageRentHistory
