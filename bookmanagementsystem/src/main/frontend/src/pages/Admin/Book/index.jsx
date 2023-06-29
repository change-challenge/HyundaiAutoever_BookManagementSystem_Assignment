import * as S from '../style'
import { Text, SearchBar } from '../../../components/index'

const AdminBook = () => {
  const handleSubmit = () => {
    console.log('검색 폼 제출')
  }
  return (
    <S.AdminUserContainer>
      <S.AdminTitle>
        <Text
          text={`도서 관리`}
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

export default AdminBook
