import styled from 'styled-components'

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  /*padding: 0 12rem 0 12rem;*/
  min-height: 80vh;
  min-width: 50.8rem;
`

const BookSearchTitleWrapper = styled.div`
  display: flex;
  margin-top: 4rem;
  margin-left: 256px;
  width: 925px;
`

const BookSearchContainer = styled.div`
  box-sizing: content-box;
  width: 1200px;
  padding: 2rem 0 5rem 0;
  display: flex;
  margin-left: auto;
  margin-right: auto;
`

const BookSearchContentWrapper = styled.div`
  float: right;
  width: 925px;
  margin-left: auto;
  margin-right: auto;
  /*display: flex;*/
`

const BookSearchFilterWrapper = styled.div`
  font-family: 'Apple SD Gothic Neo';
  width: 925px;
  height: 30px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey3};
`

const FilterWrapper = styled.div`
  flex-direction: column;
  height: 10px;
  margin-top: 20px;
  margin-bottom: 20px;
`

const BookSearchTabsWrapper = styled.div`
  min-width: 1000px;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
  justify-content: space-between;*/
`

const BookListWrapper = styled.div`
  margin: 20px 20px 20px 20px;
`

const PaginationWrapper = styled.div`
  display: flex;

  justify-content: center;
  width: 100%;
`

export {
  Container,
  BookSearchTitleWrapper,
  BookSearchContainer,
  BookSearchContentWrapper,
  BookSearchFilterWrapper,
  BookSearchTabsWrapper,
  FilterWrapper,
  BookListWrapper,
  PaginationWrapper,
}
