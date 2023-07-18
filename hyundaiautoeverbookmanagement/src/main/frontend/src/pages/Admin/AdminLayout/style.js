import styled from 'styled-components'

const AdminContainer = styled.div`
  box-sizing: content-box;
  display: flex;
  background-color: ${({ theme }) => theme.colors.white};
`
const AdminWrapper = styled.div`
  min-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
`

export { AdminContainer, AdminWrapper }
