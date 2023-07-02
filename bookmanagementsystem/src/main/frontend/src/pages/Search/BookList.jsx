import bookImg from '../../assets/XL.jpeg'
import styled from 'styled-components'
import { Text } from '../../components/index'
import { Link } from 'react-router-dom'

const BookListContentContainer = styled.div`
  display: flex;
  overflow: hidden;
  position: relative;
  min-height: 135px;
  padding: 15px 0;
`
const BookListImageWrapper = styled.div`
  border: 5px;
  width: 120px;
  height: 160px;
`

const BookListInfoWrapper = styled.div`
  display: block;
  width: 750px;
  margin-left: 20px;
`

const BookListTitle = styled.div`
  display: block;
  margin-top: 10px;
`

const BookListArthorPublisher = styled.div`
  display: flex;
  margin-top: 10px;
`

const BookListCreateDate = styled.div`
  display: block;
  margin-top: 25px;
`

const BookListISBN = styled.div`
  display: block;
  margin-top: 10px;
`

const BookListStatus = styled.div`
  display: flex;
  margin-top: 7px;
  width: 90%;
  height: 30px;
  background-color: ${({ theme }) => theme.colors.grey2};
  border-radius: 5px;
  align-items: center;
`

const BookList = () => {
  return (
    <BookListContentContainer>
      <BookListImageWrapper>
        <Link to={process.env.PUBLIC_URL + `/search/detail`}>
          <img src={bookImg} width="120px" height="160px" />
        </Link>
      </BookListImageWrapper>
      <BookListInfoWrapper>
        <BookListTitle>
          <Link
            to={process.env.PUBLIC_URL + `/search/detail`}
            style={{
              textDecoration: 'none',
            }}
          >
            <Text
              text="인간 본성의 법칙"
              color={({ theme }) => theme.colors.grey9}
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz22}
              cursor={'pointer'}
            />
          </Link>
        </BookListTitle>
        <BookListArthorPublisher>
          <Text
            text="로버트 그린"
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
          <Text
            text=" | "
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
            margin={'0 10px 0 10px'}
          />
          <Text
            text="민음사"
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListArthorPublisher>
        <BookListCreateDate>
          <Text
            text="발행월일  2023년 6월 23일"
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListCreateDate>
        <BookListISBN>
          <Text
            text="ISBN  91239123123"
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListISBN>
        <BookListStatus>
          <div style={{ marginLeft: '10px' }}>
            <Text
              text="대출가능"
              color={({ theme }) => theme.colors.green1}
              fontSize={({ theme }) => theme.fontSize.sz14}
              fontWeight={'bold'}
            />
          </div>
        </BookListStatus>
      </BookListInfoWrapper>
    </BookListContentContainer>
  )
}

export default BookList
