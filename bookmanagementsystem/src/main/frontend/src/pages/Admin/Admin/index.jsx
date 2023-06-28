import { Outlet } from 'react-router-dom'
import { SideBar } from '../../../components/index'
import * as S from './style'

const Admin = () => {
  return (
    <S.AdminContainer>
      <S.AdminWrapper>
        <SideBar />
        <Outlet />
      </S.AdminWrapper>
    </S.AdminContainer>
  )
}

export default Admin
