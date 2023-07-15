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
  width: 90%;
  margin-top: 10px;
`

const BookListAuthorPublisher = styled.div`
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

const BookList = ({ book }) => {
  console.log('BookList : ', book)
  return (
    <BookListContentContainer>
      <BookListImageWrapper>
        <Link to={process.env.PUBLIC_URL + `/search/detail/${book.id}`}>
          <img alt="" src={book.cover} width="120px" height="160px" />
        </Link>
      </BookListImageWrapper>
      <BookListInfoWrapper>
        <BookListTitle>
          <Link
            to={process.env.PUBLIC_URL + `/search/detail/${book.id}`}
            style={{
              textDecoration: 'none',
            }}
          >
            <Text
              text={book.title}
              color={({ theme }) => theme.colors.grey9}
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz22}
              cursor={'pointer'}
            />
          </Link>
        </BookListTitle>
        <BookListAuthorPublisher>
          <Text
            text={book.author}
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
            text={book.publisher}
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListAuthorPublisher>
        <BookListCreateDate>
          <Text
            text={`발행월일 ${book.pubDate}`}
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListCreateDate>
        <BookListISBN>
          <Text
            text={`ISBN ${book.isbn}`}
            color={({ theme }) => theme.colors.grey9}
            fontSize={({ theme }) => theme.fontSize.sz14}
          />
        </BookListISBN>
        <BookListStatus>
          <div style={{ marginLeft: '10px' }}>
            {book.bookStatus ? (
              <Text
                text="대출 가능"
                color={({ theme }) => theme.colors.green1}
                fontSize={({ theme }) => theme.fontSize.sz14}
                fontWeight={'bold'}
              />
            ) : (
              <Text
                text="대출 중"
                color={({ theme }) => theme.colors.red1}
                fontSize={({ theme }) => theme.fontSize.sz14}
                fontWeight={'bold'}
              />
            )}
          </div>
        </BookListStatus>
      </BookListInfoWrapper>
    </BookListContentContainer>
  )
}

export default BookList
