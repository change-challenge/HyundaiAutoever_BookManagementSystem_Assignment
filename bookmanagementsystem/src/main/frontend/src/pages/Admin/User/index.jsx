import * as S from './style'
import { Text } from '../../../components/index'

const AdminUser = () => {
  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`회원 관리`}
          fontSize={({ theme }) => theme.fontSize.sz48}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
      </S.AdminTitle>
    </S.AdminUserContainer>
  )
}

export default AdminUser
