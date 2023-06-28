import styled from 'styled-components'
import { Text } from '../index'
import chevronRight from '../../assets/chevron.svg'

const Wrapper = styled.aside`
  width: 256px;
  min-height: 80vh;
  display: flex;
  align-content: flex-start;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.grey10};
`

const MenuWrapper = styled.div`
  width: 100%;
  height: 50px;
  display: flex;
  margin-top: 10px;
  padding: 0 10px;
  color: ${({ theme }) => theme.colors.grey0};

  &:hover {
    font-weight: 800;
    color: ${({ theme }) => theme.colors.white};
  }

  &:active {
    font-weight: 800;
    color: ${({ theme }) => theme.colors.white};
  }
`

const Item = styled.div`
  padding-left: 5px;
  padding-right: 5px;
  display: flex;
  flex: 1;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`

const Menu = ({ text }) => {
  return (
    <MenuWrapper>
      <Item>
        <Text text={text} fontSize="18px" />
        <img src={chevronRight} alt="logo" width="20x" height="20x" />
      </Item>
    </MenuWrapper>
  )
}

export { Wrapper, Menu }
