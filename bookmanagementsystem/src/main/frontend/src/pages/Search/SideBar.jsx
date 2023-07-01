import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { Text } from '../../components/index'

const categories = [
  { id: 1, name: '전체' },
  { id: 2, name: '총류' },
  { id: 3, name: '철학' },
  { id: 4, name: '종교' },
  { id: 5, name: '사회과학' },
  { id: 6, name: '자연과학' },
  { id: 7, name: '기술과학' },
  { id: 8, name: '예술' },
  { id: 9, name: '언어' },
  { id: 10, name: '문학' },
  { id: 11, name: '역사' },
]

const Wrapper = styled.aside`
  width: 256px;
  float: left;
  background-color: ${({ theme }) => theme.colors.main};
`

const SideBarTitle = styled.h2`
  font-family: 'Apple SD Gothic Neo';
  text-align: center;
  padding: 30px 0;
  font-weight: normal;
  font-size: 1.533em;
  line-height: 60px;
  color: ${({ theme }) => theme.colors.white};
`

const SearchListWrapper = styled.div`
  position: relative;
`

const CategoryList = styled.div`
  font-family: 'Apple SD Gothic Neo';
  padding: 10px 15px;
  background-color: #f8f8f8;
`

const CategoryWrapper = styled.div`
  width: 100%;
  height: 10px;
  margin-top: 20px;
  margin-bottom: 20px;
  &:hover {
    font-weight: 800;
  }

  &:active {
    font-weight: 800;
  }
`

const SideBar = () => {
  return (
    <Wrapper>
      <SideBarTitle>자료 검색</SideBarTitle>
      <SearchListWrapper>
        <CategoryList>
          <ul>
            {categories.map(category => (
              <li key={category.id}>
                <CategoryWrapper>
                  <Link
                    to={`/search/`}
                    style={{
                      textDecoration: 'none',
                      color: 'gray',
                      fontWeight: 400,
                    }}
                    onMouseOver={e => {
                      e.target.style.fontWeight = 800
                    }}
                    onMouseOut={e => {
                      e.target.style.fontWeight = 400
                    }}
                  >
                    {category.name}
                  </Link>
                </CategoryWrapper>
              </li>
            ))}
          </ul>
        </CategoryList>
      </SearchListWrapper>
    </Wrapper>
  )
}

export default SideBar
