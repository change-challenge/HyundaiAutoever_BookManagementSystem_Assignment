import * as S from './style'
import { Text } from '../../../components/index'

const Admin = () => {
  return (
    <S.AdminMainContainer>
      <S.AdminMainWrapper>
        <S.AdminTitle>
          <Text
            text={`관리자님, 반갑습니다.
		  현대오토에버 도서관리시스템 관리자 페이지입니다.`}
            fontSize={({ theme }) => theme.fontSize.sz40}
            fontWeight={'bold'}
            textAlign={'center'}
            color={({ theme }) => theme.colors.main}
            lineHeight="50px"
          />
        </S.AdminTitle>
      </S.AdminMainWrapper>
    </S.AdminMainContainer>
  )
}

export default Admin
