import * as S from './style'
import { Outlet } from 'react-router-dom'
import { SideBar } from '../../../components/index'

const AdminLayout = () => {
  return (
    <S.AdminContainer>
      <S.AdminWrapper>
        <SideBar />
        <Outlet />
      </S.AdminWrapper>
    </S.AdminContainer>
  )
}

export default AdminLayout
