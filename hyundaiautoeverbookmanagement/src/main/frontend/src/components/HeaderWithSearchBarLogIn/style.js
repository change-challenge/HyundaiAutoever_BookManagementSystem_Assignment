import styled from 'styled-components'

const HeaderContainer = styled.header`
  box-sizing: content-box;
  margin: 0 32px;
  padding: 20px 0 10px;
  display: flex;
  background-color: ${({ theme }) => theme.colors.white};
`

const Layout = styled.div`
  min-width: 1024px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey3};
`

const LogoWrapper = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: column;
  padding-bottom: 20px;
`

const NavWrapper = styled.nav`
  display: flex;
  justify-content: space-between;
  width: 200px;
  padding-bottom: 30px;
`

const LinkButtonWrapper = styled.ul`
  display: flex;
  justify-content: right;
  margin: 1rem 0 0 0;
`

const SearchContainer = styled.div`
  justify-content: center;
`

export {
  HeaderContainer,
  LogoWrapper,
  Layout,
  LinkButtonWrapper,
  NavWrapper,
  SearchContainer,
}
