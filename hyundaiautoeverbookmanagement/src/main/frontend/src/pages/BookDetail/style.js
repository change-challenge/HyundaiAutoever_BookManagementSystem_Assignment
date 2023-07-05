import styled from 'styled-components'

const BookDetailContainer = styled.div`
  box-sizing: content-box;
  background-color: ${({ theme }) => theme.colors.white};
`

const InnerWrapper = styled.div`
  padding: 2rem 0 5rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`

const BookDetailTitleWrapper = styled.div`
  min-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
	justify-content: space-between;*/
`

const BreadcrumbWrapper = styled.div`
  margin-left: auto;
  margin-right: auto;
  display: flex;
  justify-content: flex-end; /* 요소들을 오른쪽으로 정렬 */
  min-width: 1000px;
`

const BookDetailContentWrapper = styled.div`
  min-width: 1000px;
  display: flex;
  margin: 3rem 0;
`

const BookDetailImageWrapper = styled.div`
  width: 280px;
  height: 380px;
  margin: 0 2rem 0 0;
`

const BookDetailContent = styled.div`
  margin-left: 10px;
  height: 380px;
  width: 600px;
`

const BookDetailContentTitleWrapper = styled.div`
  margin: 30px 0 10px 30px;
  padding-bottom: 10px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey6};
`

const BookDetailContentInfoContainer = styled.div`
  margin-top: 20px;
  margin-left: 20px;
`

const BookDetailContentInfoWrapper = styled.div`
  margin-top: 10px;
  display: flex;
`

const BookDetailContentQuestionWrapper = styled.div`
  margin-left: 10px;
  width: 100px;
`

const BookDetailContentAnswerWrapper = styled.div`
  width: 100%;
`

const BookDetailInfoContainer = styled.div`
  min-width: 1000px;
  margin: 1rem 0;
  align-items: center;
`

const BookDetailInfoTitleWrapper = styled.div`
  margin-top: 20px;
  max-width: 1000px;
`

const BookDetailInfoContentWrapper = styled.div`
  margin-top: 30px;
  max-width: 1000px;
`

const BookCountInfoContainer = styled.div`
  min-width: 1000px;
  margin: 1rem 0;
  align-items: center;
`

const BookDetailCountTableWrapper = styled.div`
  align-items: center;
  margin-top: 30px;
  max-width: 1000px;
`

export {
  BookDetailContainer,
  BookDetailTitleWrapper,
  InnerWrapper,
  BreadcrumbWrapper,
  BookDetailContentWrapper,
  BookDetailImageWrapper,
  BookDetailContent,
  BookDetailContentTitleWrapper,
  BookDetailContentInfoContainer,
  BookDetailContentInfoWrapper,
  BookDetailContentQuestionWrapper,
  BookDetailContentAnswerWrapper,
  BookDetailInfoContainer,
  BookDetailInfoTitleWrapper,
  BookDetailInfoContentWrapper,
  BookCountInfoContainer,
  BookDetailCountTableWrapper,
}
