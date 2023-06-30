import styled from 'styled-components'

const MypageContainer = styled.div`
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

const SearchContainer = styled.div`
  position: relative;
  flex: 1;
  overflow-y: auto;
  z-index: 3;
  margin-bottom: 45px;
  padding-bottom: 15px;
`

const MypageTitleWrapper = styled.div`
  min-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
	justify-content: space-between;*/
`

const MypageGreetingWrapper = styled.div`
  min-width: 1000px;
  margin-top: 5rem;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
	justify-content: space-between;*/
`

const MypageTabsWrapper = styled.div`
  min-width: 1000px;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  /*align-items: center;
  justify-content: space-between;*/
`

export {
  MypageContainer,
  MypageTitleWrapper,
  InnerWrapper,
  SearchContainer,
  MypageGreetingWrapper,
  MypageTabsWrapper,
}
