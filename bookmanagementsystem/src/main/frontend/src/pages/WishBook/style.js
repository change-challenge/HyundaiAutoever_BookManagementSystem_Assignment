import styled from 'styled-components'

const InnerContainer = styled.div`
  max-width: 1000px;
  background-color: ${({ theme }) => theme.colors.white};
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
  flex-direction: column;
`

const ContentWrap = styled.div`
  margin-top: 50px;
  min-width: 1000px;
  align-items: center;
  justify-content: center;
  margin-left: auto;
  margin-right: auto;
  flex: 1;
`

const LabelWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
`

const EssentialMark = styled.span`
  padding: 0 5px;
  font-weight: bold;
  color: #eb6100;
`

const LabelTitleWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-right: 50px;
  width: 300px;
`

const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 50px;
  width: 100px;
`

export {
  InnerContainer,
  ContentWrap,
  LabelWrapper,
  EssentialMark,
  LabelTitleWrapper,
  ButtonWrapper,
}
