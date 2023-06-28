import styled from 'styled-components'

const HeaderContainer = styled.header`
  box-sizing: content-box;
  margin: 0 32px;
  padding: 10px 0;
  display: flex;
  background-color: white;
  padding-top: 20px;
`

const Layout = styled.div`
  min-width: 1024px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  border-bottom: 1px solid ${({ theme }) => theme.colors.grey3};
`

const LogoWrapper = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: column;
  padding-bottom: 30px;
`

const NavWrapper = styled.nav`
  display: flex;
  justify-content: space-between;
  width: 200px;
  padding-bottom: 38px;
`

const LinkButtonWrapper = styled.ul`
  display: flex;
  justify-content: right;
  margin: 1rem 0 0 0;
`

export { HeaderContainer, LogoWrapper, Layout, LinkButtonWrapper, NavWrapper }
