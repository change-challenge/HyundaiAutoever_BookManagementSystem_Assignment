import styled from 'styled-components'

const Container = styled.div`
  min-width: 1024px;
  min-height: 500px;
  flex-direction: column;
  align-items: center;
  padding: 50px 12rem;
`

const InnerContainer = styled.div`
  position: relative;
  top: 0;
  bottom: 0;
  width: 100%;
  max-width: 450px;
  padding: 50px 50px;
  left: 50%;
  transform: translate(-50%, 0);
  background-color: ${({ theme }) => theme.colors.white};
  overflow: hidden;
  display: flex;
  justify-content: center;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.colors.grey4};
  border-radius: 5px;
`

const TitleWrap = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  font-size: ${({ theme }) => theme.fontSize.sz32};
  font-weight: 700;
  color: #262626;
`

const ContentWrap = styled.div`
  margin-top: 26px;
  flex: 1;
`

const ErrorMessageWrap = styled.div`
  margin-top: 8px;
  color: #ef0000;
  font-size: 12px;
`

const LoginButton = styled.button`
  width: 100%;
  height: 48px;
  border: none;
  font-weight: 700;
  background-color: ${({ theme }) => theme.colors.main};
  border-radius: 8px;
  color: ${({ theme }) => theme.colors.white};
  margin-bottom: 16px;
  cursor: pointer;

  &:disabled {
    background-color: #dadada;
    color: ${({ theme }) => theme.colors.white};
  }
`

const SignUpButton = styled.button`
  width: 100%;
  height: 48px;
  border: 0.5px solid ${({ theme }) => theme.colors.main};
  font-weight: 700;
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 8px;
  color: ${({ theme }) => theme.colors.main};
  margin-bottom: 16px;
  transition-duration: 0.2s;
  cursor: pointer;

  &:hover {
    background-color: ${({ theme }) => theme.colors.main2};
  }
`

export {
  Container,
  InnerContainer,
  TitleWrap,
  ContentWrap,
  ErrorMessageWrap,
  LoginButton,
  SignUpButton,
}
