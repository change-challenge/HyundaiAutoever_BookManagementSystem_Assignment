import styled from 'styled-components'

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 0 12rem 0 12rem;
  min-height: 80vh;
  min-width: 50.8rem;
`

const BookSearchContainer = styled.div`
  box-sizing: content-box;
  /*background-color: ${({ theme }) => theme.colors.black};*/
`

const InnerWrapper = styled.div`
  padding: 2rem 0 5rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`

const BookSearchTitleWrapper = styled.div`
  min-width: 1000px;
  margin-top: 5rem;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
	justify-content: space-between;*/
`

const BookSearchGreetingWrapper = styled.div`
  min-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
	justify-content: space-between;*/
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

export {
  Container,
  BookSearchContainer,
  BookSearchTitleWrapper,
  InnerWrapper,
  BookSearchGreetingWrapper,
  BookSearchTabsWrapper,
}
