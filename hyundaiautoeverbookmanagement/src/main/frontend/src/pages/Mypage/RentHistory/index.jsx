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

    let sortedRents = response.data.sort((a, b) => {
      // 먼저, rentReturnedDate가 null인 요소를 뒤로 보냄
      if (a.rentReturnedDate === null && b.rentReturnedDate !== null) {
        return -1
      }
      if (a.rentReturnedDate !== null && b.rentReturnedDate === null) {
        return 1
      }

      // 그 후, rentStartDate를 기준으로 최신 순서대로 정렬
      return new Date(b.rentStartDate) - new Date(a.rentStartDate)
    })

    setRents(sortedRents)
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
                  text={`대출일 : ${rent.rentStartDate} | 반납일 : -
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
