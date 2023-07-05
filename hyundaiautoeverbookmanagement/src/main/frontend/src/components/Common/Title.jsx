import styled from 'styled-components'
import { Text } from '../index'
import Logo from '../../assets/logo.svg'

const TitleWrap = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  font-weight: 700;
`

const Title = ({ text }) => {
  return (
    <TitleWrap>
      <Text
        text={text}
        color={({ theme }) => theme.colors.black}
        fontWeight={'bold'}
        fontSize={({ theme }) => theme.fontSize.sz32}
        textAlign={'center'}
        verticalAlign={'middle'}
      />
      <Text
        text="|"
        color={({ theme }) => theme.colors.grey3}
        fontSize={({ theme }) => theme.fontSize.sz32}
        fontWeight={'lighter'}
        textAlign={'center'}
        margin={'0 0 0 20px'}
        verticalAlign={'middle'}
      />
      <img
        src={Logo}
        alt="logo"
        width="120px"
        height="36px"
        draggable={false}
      />
    </TitleWrap>
  )
}

export default Title
