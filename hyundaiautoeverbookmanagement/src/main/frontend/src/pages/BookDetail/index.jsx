import { useParams } from 'react-router-dom'
import * as S from './style'
import { Text } from '../../components/index'
import CustomSeparator from './Breadcrumbs'
import BookCountTable from './BookCountTable'
import { useEffect, useState } from 'react'
import axios from 'axios'

const BookDetail = () => {
  let { bookId } = useParams()

  const [book, setBook] = useState([])

  useEffect(() => {
    const fetchBooks = async () => {
      const response = await axios.get(`/api/book/search/${bookId}`)
      setBook(response.data)
      console.log(book)
    }
    fetchBooks()
  }, [])

  return (
    <S.BookDetailContainer>
      <S.InnerWrapper>
        <S.BookDetailTitleWrapper>
          <Text
            text="도서 상세페이지"
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz32}
          />
        </S.BookDetailTitleWrapper>
        <S.BreadcrumbWrapper>
          <CustomSeparator />
        </S.BreadcrumbWrapper>
        <S.BookDetailContentWrapper>
          <S.BookDetailImageWrapper>
            <img alt="" src={book.cover} width="100%" height="100%" />
          </S.BookDetailImageWrapper>
          <S.BookDetailContent>
            <S.BookDetailContentTitleWrapper>
              <Text
                text={book.title}
                color={({ theme }) => theme.colors.main}
                fontWeight={'bold'}
                fontSize={({ theme }) => theme.fontSize.sz28}
              />
            </S.BookDetailContentTitleWrapper>
            {/*도서리스트 컨텐츠 시작*/}
            <S.BookDetailContentInfoContainer>
              <S.BookDetailContentInfoWrapper>
                <S.BookDetailContentQuestionWrapper>
                  <Text
                    text="저자"
                    color={({ theme }) => theme.colors.main}
                    fontWeight={'bold'}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentQuestionWrapper>
                <S.BookDetailContentAnswerWrapper>
                  <Text
                    text={book.author}
                    color={({ theme }) => theme.colors.grey9}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentAnswerWrapper>
              </S.BookDetailContentInfoWrapper>

              <S.BookDetailContentInfoWrapper>
                <S.BookDetailContentQuestionWrapper>
                  <Text
                    text="출판사"
                    color={({ theme }) => theme.colors.main}
                    fontWeight={'bold'}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentQuestionWrapper>
                <S.BookDetailContentAnswerWrapper>
                  <Text
                    text={book.publisher}
                    color={({ theme }) => theme.colors.grey9}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentAnswerWrapper>
              </S.BookDetailContentInfoWrapper>

              <S.BookDetailContentInfoWrapper>
                <S.BookDetailContentQuestionWrapper>
                  <Text
                    text="출간월일"
                    color={({ theme }) => theme.colors.main}
                    fontWeight={'bold'}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentQuestionWrapper>
                <S.BookDetailContentAnswerWrapper>
                  <Text
                    text={book.pubDate}
                    color={({ theme }) => theme.colors.grey9}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentAnswerWrapper>
              </S.BookDetailContentInfoWrapper>

              <S.BookDetailContentInfoWrapper>
                <S.BookDetailContentQuestionWrapper>
                  <Text
                    text="카테고리"
                    color={({ theme }) => theme.colors.main}
                    fontWeight={'bold'}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentQuestionWrapper>
                <S.BookDetailContentAnswerWrapper>
                  <Text
                    text={book.category}
                    color={({ theme }) => theme.colors.grey9}
                    fontSize={({ theme }) => theme.fontSize.sz18}
                  />
                </S.BookDetailContentAnswerWrapper>
              </S.BookDetailContentInfoWrapper>
            </S.BookDetailContentInfoContainer>
            {/*도서리스트 컨텐츠 끝*/}
          </S.BookDetailContent>
        </S.BookDetailContentWrapper>

        <S.BookDetailInfoContainer>
          <S.BookDetailInfoTitleWrapper>
            <Text
              text="상세정보"
              color={({ theme }) => theme.colors.main}
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz18}
            />
          </S.BookDetailInfoTitleWrapper>
          <S.BookDetailInfoContentWrapper>
            {book.info == '' ? (
              <Text
                text="없음"
                lineHeight="1.2"
                color={({ theme }) => theme.colors.grey7}
                fontSize={({ theme }) => theme.fontSize.sz18}
              />
            ) : (
              <Text
                text={book.info}
                lineHeight="1.2"
                color={({ theme }) => theme.colors.grey7}
                fontSize={({ theme }) => theme.fontSize.sz18}
              />
            )}
          </S.BookDetailInfoContentWrapper>
        </S.BookDetailInfoContainer>

        <S.BookCountInfoContainer>
          <S.BookDetailInfoTitleWrapper>
            <Text
              text="소장정보"
              color={({ theme }) => theme.colors.main}
              fontWeight={'bold'}
              fontSize={({ theme }) => theme.fontSize.sz18}
            />
          </S.BookDetailInfoTitleWrapper>
          <S.BookDetailCountTableWrapper>
            <BookCountTable bookId={bookId} />
          </S.BookDetailCountTableWrapper>
        </S.BookCountInfoContainer>
      </S.InnerWrapper>
    </S.BookDetailContainer>
  )
}

export default BookDetail
