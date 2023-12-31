import React from 'react'
import Stack from '@mui/material/Stack'
import Button from '@mui/material/Button'
import { Text } from '../../../components/index'
import * as S from './style'
import { useEffect, useState, useContext } from 'react'
import apiClient from '../../../axios'
import { SnackbarContext } from '../../../context/SnackbarContext'
import Pagination from '@mui/material/Pagination'
import { useAlert } from '../../../context/AlertContext'
import { useConfirm } from '../../../context/ConfirmContext'

const MypageRent = ({ user }) => {
  const showAlert = useAlert()
  const showConfirm = useConfirm()
  const { setSnackbar } = useContext(SnackbarContext)
  const [rents, setRents] = useState([])
  const [page, setPage] = useState(1)

  const handlePageChange = (event, value) => {
    setPage(value)
  }

  const fetchRents = async () => {
    const response = await apiClient.get(`/api/rent/current`, {
      params: {
        email: user.email,
      },
    })
    setRents(response.data)
  }

  const handleReturnClick = copyId => {
    if (!user) {
      showAlert('로그인이 필요한 기능입니다!')
      return
    }
    console.log('copyId : ', copyId)

    showConfirm('도서를 반납하시겠습니까?', () => makeReturn(copyId))
  }

  const makeReturn = async copyId => {
    const response = await apiClient
      .post(`/api/return/${copyId}`, {
        copyId: copyId,
        email: user.email,
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

  const handleExtendClick = copyId => {
    if (!user) {
      showAlert('로그인이 필요한 기능입니다!')
      return
    }

    showConfirm('도서를 연장하시겠습니까?', () => makeExtend(copyId))
  }

  const makeExtend = async copyId => {
    const response = await apiClient
      .post(`/api/extend/${copyId}`, {
        copyId: copyId,
        email: user.email,
      })
      .then(response => {
        console.log('makeExtend: ', response.data)
        window.location.reload()
        setSnackbar({
          open: true,
          severity: 'success',
          message: '도서 연장 성공!',
        })
      })
      .catch(error => {
        if (error.response) {
          setSnackbar({
            open: true,
            severity: 'error',
            message: '도서를 연장할 수 없습니다.',
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

  const rentsToShow = rents.slice((page - 1) * 5, page * 5)

  return (
    <>
      <S.RentCountWrapper>
        <Text
          text={`대출현황건수 : ${rents.length}건`}
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
              <Text
                text={`대출일 : ${rent.startDate} | 반납예정일 : ${rent.endDate}
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
              <Button
                variant="contained"
                size="large"
                onClick={() => handleExtendClick(rent.copyId)}
                disabled={!rent.extendable}
              >
                연장하기
              </Button>
            </Stack>
          </S.ButtonWrapper>
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

export default MypageRent
