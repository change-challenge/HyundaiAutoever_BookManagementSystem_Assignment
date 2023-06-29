import { memo } from 'react'
import styled from 'styled-components'

const StyledText = styled.p`
  color: ${({ color }) => color};
  font-family: 'Apple SD Gothic Neo';
  font-size: ${({ fontSize }) => fontSize};
  font-weight: ${({ fontWeight }) => fontWeight};
  margin: ${({ margin }) => margin};
  text-align: ${({ textAlign }) => textAlign};
  vertical-align: ${({ verticalAlign }) => verticalAlign};
  cursor: ${({ cursor }) => cursor};
  line-height: ${({ lineHeight }) => lineHeight};
  max-width: 100%;
  white-space: pre-line;
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
