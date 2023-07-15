import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import axios from 'axios'
import Pagination from '@mui/material/Pagination'

const MypageWishBook = ({ user }) => {
  const [wishBooks, setWishBooks] = useState([])
  const [page, setPage] = useState(1)

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const fetchwishBook = async () => {
    const response = await axios.get(`/api/wishbook/read`, {
      params: {
        email: user.email,
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

  const wishsToShow = wishBooks.slice((page - 1) * 5, page * 5)

  return (
    <>
      <S.RentCountWrapper>
        <Text
          text={`신청현황건수 : ${wishBooks.length}건`}
          textColor={({ theme }) => theme.colors.black}
        />
      </S.RentCountWrapper>
      {wishsToShow.map(wishBook => (
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
      ))}
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(wishBooks.length / 5)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </>
  )
}

export default MypageWishBook
