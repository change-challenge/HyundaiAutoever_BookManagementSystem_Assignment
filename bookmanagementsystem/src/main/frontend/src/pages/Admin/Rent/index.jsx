import * as S from '../style'
import { Text, SearchBar } from '../../../components/index'

const AdminRent = () => {
  const handleSubmit = () => {
    console.log('검색 폼 제출')
  }
  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`대출 관리`}
          fontSize={({ theme }) => theme.fontSize.sz40}
          fontWeight={'bold'}
          color={({ theme }) => theme.colors.black}
        />
        <S.SearchBarContainer>
          <SearchBar
            align="row"
            width="400px"
            height="40px"
            onSubmit={handleSubmit}
            placeholder={'검색'}
          />
        </S.SearchBarContainer>
      </S.AdminTitle>
    </S.AdminUserContainer>
  )
}

export default AdminRent
