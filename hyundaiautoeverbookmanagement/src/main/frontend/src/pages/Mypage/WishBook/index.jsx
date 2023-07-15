import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import axios from 'axios'

const MypageWishBook = ({ user }) => {
  const [wishBooks, setWishBooks] = useState([])

  const fetchwishBook = async () => {
    const response = await axios.get(`/api/wishbook/read`, {
      params: {
        userEmail: user.email,
      },
    })
    setWishBooks(response.data)
    console.log('response.data : ', response.data)
  }

  useEffect(() => {
    fetchwishBook()
  }, [])

  function getStatusText(status) {
    switch (status) {
      case 'PENDING':
        return '처리중'
      case 'DECLINED':
        return '반려'
      case 'APPROVED':
        return '승인'
      default:
        return status
    }
  }

  return (
    <>
      <S.RentCountWrapper>
        <Text
          text={`신청현황건수 : ${wishBooks.length}건`}
          textColor={({ theme }) => theme.colors.black}
        />
      </S.RentCountWrapper>
      {wishBooks.map(wishBook => (
        <S.RentDetailContainer>
          <S.RentInfoWrapper>
            <S.RentTitleWrapper>
              <Text
                text={`${wishBook.book.title}`}
                fontWeight={'bold'}
                fontSize={({ theme }) => theme.fontSize.sz20}
              />
            </S.RentTitleWrapper>
            <S.RentDetailWrapper>
              <Text
                text={`신청일 : ${wishBook.wish_date}`}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
              <Text
                text={getStatusText(wishBook.status)}
                fontSize={({ theme }) => theme.fontSize.sz16}
              />
            </S.RentDetailWrapper>
          </S.RentInfoWrapper>
        </S.RentDetailContainer>
      ))}{' '}
    </>
  )
}

export default MypageWishBook
