import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import apiClient from '../../../axios'
import Pagination from '@mui/material/Pagination'

const MypageWishBook = ({ user }) => {
  const [wishBooks, setWishBooks] = useState([])
  const [page, setPage] = useState(1)

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const fetchwishBook = async () => {
    const response = await apiClient.get(`/api/wish/read`, {
      params: {
        email: user.email,
      },
    })
    const sortedWishBooks = response.data.sort((a, b) => {
      const priorityA = getStatusPriority(a.status)
      const priorityB = getStatusPriority(b.status)

      if (priorityA !== priorityB) {
        return priorityA - priorityB
      }

      const dateA = new Date(a.wishDate)
      const dateB = new Date(b.wishDate)
      return dateB - dateA
    })

    setWishBooks(sortedWishBooks)
  }

  useEffect(() => {
    fetchwishBook()
  }, [])

  function getStatusPriority(status) {
    switch (status) {
      case 'PENDING':
        return 1
      case 'APPROVED':
        return 2
      case 'REJECTED':
        return 3
      default:
        return 4
    }
  }

  function getStatusText(status) {
    switch (status) {
      case 'PENDING':
        return '처리중'
      case 'REJECTED':
        return '반려됨'
      case 'APPROVED':
        return '승인됨'
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
                text={`신청일 : ${wishBook.wishDate}`}
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
