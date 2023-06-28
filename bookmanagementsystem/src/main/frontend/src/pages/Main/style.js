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

const InnerWrapper = styled.div`
  padding: 5rem 0 15rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`
const UpperLine = styled.div`
  width: 3rem;
  height: 0.3rem;
  background-color: ${({ theme }) => theme.colors.grey9};
  margin-bottom: 4rem;
  padding: 0;
  border: 0;
`

const Title = styled.div`
  font-size: ${({ theme }) => theme.fontSize.sz40};
  text-align: center;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.main};
`

const SubGuide = styled.span`
  margin-top: 1.6rem;
  margin-bottom: 6rem;
  color: ${({ theme }) => theme.colors.grey4};
`

const SearchContainer = styled.div`
  position: relative;
  flex: 1;
  overflow-y: auto;
  z-index: 3;
  margin-bottom: 45px;
  padding-bottom: 15px;
`

const SearchWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 500px;
  max-height: 100%;
  background-color: #f2f1fa;
  border-radius: 25px;
  overflow-y: auto;
  margin: auto;
`

export {
  Container,
  InnerWrapper,
  UpperLine,
  Title,
  SubGuide,
  SearchContainer,
  SearchWrapper,
}
