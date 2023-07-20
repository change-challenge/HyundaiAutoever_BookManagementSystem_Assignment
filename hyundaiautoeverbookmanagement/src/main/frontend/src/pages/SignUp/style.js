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

const ContentWrap = styled.div`
  margin-top: 26px;
`

const EmailWrap = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: space-between;
`

const InputWrap = styled.div`
  display: flex;
  border-radius: 8px;
  padding: 16px;
  margin-top: 8px;
  background-color: ${({ theme }) => theme.colors.white};
  border: 1px solid ${({ theme }) => theme.colors.grey4};
  margin-top: 15px;

  &:focus-within {
    border: 1px solid ${({ theme }) => theme.colors.main};
  }
`

const Input = styled.input`
  width: 100%;
  outline: none;
  border: none;
  height: 17px;
  font-size: ${({ theme }) => theme.fontSize.sz14};
  font-weight: 400;

  &::placeholder {
    color: ${({ theme }) => theme.colors.grey2};
  }
`

const OkMessageWrap = styled.div`
  margin-top: 8px;
  color: ${({ theme }) => theme.colors.green1};
  font-size: ${({ theme }) => theme.fontSize.sz12};
`

const ErrorMessageWrap = styled.div`
  margin-top: 8px;
  color: ${({ theme }) => theme.colors.red1};
  font-size: ${({ theme }) => theme.fontSize.sz12};
`

const SignUpButton = styled.button`
  width: 100%;
  height: 48px;
  border: none;
  font-weight: 700;
  background-color: ${({ theme }) => theme.colors.main};
  border-radius: 8px;
  color: white;
  margin-bottom: 16px;
  cursor: pointer;

  &:disabled {
    background-color: #dadada;
    color: white;
  }
`
const ToLoginButton = styled.button`
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

const CheckEmailButton = styled.button`
  height: 100%;
  margin-top: 10px;
  margin-left: 8px;
  background-color: ${({ theme }) => theme.colors.main};
  color: white;
  padding: 5px 10px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
`

export {
  Container,
  InnerContainer,
  ContentWrap,
  InputWrap,
  Input,
  OkMessageWrap,
  ErrorMessageWrap,
  SignUpButton,
  ToLoginButton,
  CheckEmailButton,
  EmailWrap,
}
