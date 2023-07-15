import styled from 'styled-components'

const RentCountWrapper = styled.div`
  min-width: 1000px;
  min-height: 30px;
  display: flex;

  border-bottom: 1px solid ${({ theme }) => theme.colors.grey9};
`

const RentDetailContainer = styled.div`
  justify-content: space-between;
  min-width: 1000px;
  min-height: 150px;
  display: flex;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey3};
`

const RentInfoWrapper = styled.div`
  flex-direction: column;
  display: flex;
  flex-wrap: wrap;
  align-content: space-between;
`

const RentTitleWrapper = styled.div`
  max-width: 800px;
  max-height: 30px;
  margin-top: 15px;
  margin-left: 15px;
`

const RentDetailWrapper = styled.div`
  margin-top: 40px;
  margin-left: 15px;
`

const ButtonWrapper = styled.div`
  margin-top: 20px;
  display: flex;
  align-content: center;
`

const PaginationWrapper = styled.div`
  display: flex;

  justify-content: center;
  width: 100%;
`

export {
  RentCountWrapper,
  RentDetailContainer,
  RentInfoWrapper,
  RentTitleWrapper,
  RentDetailWrapper,
  ButtonWrapper,
  PaginationWrapper,
}
