import { memo } from 'react'
import styled from 'styled-components'

const StyledText = styled.p`
  color: ${({ color }) => color};
  font-family: ${({ fontFamily }) => fontFamily};
  font-size: ${({ fontSize }) => fontSize};
  font-weight: ${({ fontWeight }) => fontWeight};
  margin: ${({ margin }) => margin};
  text-align: ${({ textAlign }) => textAlign};
  vertical-align: ${({ verticalAlign }) => verticalAlign};
  cursor: ${({ cursor }) => cursor};
`

const Text = ({ text, children, ...props }) => {
  return (
    <StyledText {...props}>
      {text}
      {children}
    </StyledText>
  )
}

export default memo(Text)
