import * as S from './style'
import { Text } from '../../../components/index'

const Admin = () => {
  return (
    <S.AdminTitle>
      <Text
        text="관리자님, 반갑습니다."
        fontSize={({ theme }) => theme.fontSize.sz40}
        fontWeight={'bold'}
      />
    </S.AdminTitle>
  )
}

export default Admin
