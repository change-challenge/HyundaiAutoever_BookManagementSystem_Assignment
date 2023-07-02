import { useParams } from 'react-router-dom'
import * as S from './style'
import { Text } from '../../components/index'
import CustomSeparator from './Breadcrumbs'
import bookImg from '../../assets/XL.jpeg'
import BookCountTable from './BookCountTable'

const BookDetail = () => {
  const { book_id } = useParams()
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
            <img src={bookImg} width="100%" height="100%" />
          </S.BookDetailImageWrapper>
          <S.BookDetailContent>
            <S.BookDetailContentTitleWrapper>
              <Text
                text="인간 본성의 법칙"
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
                    text="로버트 그린"
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
                    text="민음사"
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
                    text="2018년 9월"
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
                    text="현대소설"
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
            <Text
              text="어디에도 속하지 못했던 자이니치들의 분노와 슬픔에서 탄생한 대작! 한국계 1.5세인 미국 작가 이민진의 장편소설 『파친코』 제1권. 내국인이면서 끝내 이방인일 수밖에 없었던 자이니치(재일동포)들의 처절한 생애를 깊이 있는 필체로 담아낸 작품이다. 저자가 자이니치, 즉 재일동포의 존재를 처음 접한 것은 대학생이었던 1989년, 일본에서 자이니치들을 만났던 개신교 선교사의 강연을 들은 때였다. 상승 욕구가 강한 재미동포들과 달리 많은 자이니치들이 일본의 사회적, 경제적 사다리 아래쪽에서 신음하고 있다는 사실을 알게 된 저자는 그때부터 자이니치에 관해 관심을 가지게 되었고 이번 작품에서 일제강점기부터 1980년대까지를 시대적 배경으로 하여 4대에 걸친 핏줄의 역사를 탄생시켰다. 삶은 모두에게나 고통이지만 일제강점기에 일본으로 건너간 조선인들에게는 더더욱 가혹했다. 그들은 그저 자식만큼은 자신들보다 나은 대우를 받으며 살 수 있기를 바라는 보통 사람들이었지만, 시대는 그들의 평범한 소원을 들어줄 만큼 호락호락한 것이 아니었다. 가난한 집의 막내딸 양진은 돈을 받고 언청이에 절름발이인 훈이와 결혼한다. 양진은 남편 훈이와 함께 하숙집을 운영해나가며 불평 한마디 하지 않는다. ..."
              lineHeight="1.2"
              color={({ theme }) => theme.colors.grey7}
              fontSize={({ theme }) => theme.fontSize.sz18}
            />
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
            <BookCountTable />
          </S.BookDetailCountTableWrapper>
        </S.BookCountInfoContainer>
      </S.InnerWrapper>
    </S.BookDetailContainer>
  )
}

export default BookDetail
