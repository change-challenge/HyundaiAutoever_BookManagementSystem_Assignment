import React from 'react'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState } from 'react'
import apiClient from '../../../axios'
import Pagination from '@mui/material/Pagination'

const MypageRentHistory = ({ user }) => {
  const [rents, setRents] = useState([])
  const [page, setPage] = useState(1)

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const fetchRents = async () => {
    const response = await apiClient.get(`/api/rent/history`, {
      params: {
        email: user.email,
      },
    })

    let sortedRents = response.data.sort((a, b) => {
      // 먼저, rentReturnedDate가 null인 요소를 뒤로 보냄
      if (a.returnedDate === null && b.returnedDate !== null) {
        return -1
      }
      if (a.returnedDate !== null && b.returnedDate === null) {
        return 1
      }

      // 그 후, startDate를 기준으로 최신 순서대로 정렬
      return new Date(b.startDate) - new Date(a.startDate)
    })

    setRents(sortedRents)
  }

  useEffect(() => {
    fetchRents()
  }, [])

  const rentsToShow = rents.slice((page - 1) * 5, page * 5)

  return (
    <>
      <S.RentCountWrapper>
        <Text
          text={`대출이력건수 : ${rents.length}건`}
          textColor={({ theme }) => theme.colors.black}
        />
      </S.RentCountWrapper>
      {rentsToShow.map((rent, index) => (
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
              {rent.returnedDate === null ? (
                <Text
                  text={`대출일 : ${rent.startDate} | 반납일 : -
			대출상태 : 대출중`}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              ) : (
                <Text
                  text={`대출일 : ${rent.startDate} | 반납일 : ${rent.returnedDate}
			  대출상태 : 반납됨`}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              )}
            </S.RentDetailWrapper>
          </S.RentInfoWrapper>
        </S.RentDetailContainer>
      ))}
      <S.PaginationWrapper>
        <Pagination
          count={Math.ceil(rents.length / 5)}
          page={page}
          onChange={handlePageChange}
          shape="rounded"
        />
      </S.PaginationWrapper>
    </>
  )
}

export default MypageRentHistory
