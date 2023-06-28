import styled from 'styled-components'

const Hi = styled.h2`
  color: ${({ theme }) => theme.colors.red1};
  font-size: ${({ theme }) => theme.fontSize.sz40};
`

function Main() {
  return <Hi>Main</Hi>
}

export default Main
