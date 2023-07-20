import styled from 'styled-components'

const categories = [
  { id: 1, name: '전체' },
  { id: 2, name: '가정/요리/뷰티' },
  { id: 3, name: '건강/취미/레저' },
  { id: 4, name: '경제경영' },
  { id: 5, name: '고등학교참고서' },
  { id: 6, name: '고전' },
  { id: 7, name: '과학' },
  { id: 8, name: '달력/기타' },
  { id: 9, name: '대학교재/전문서적' },
  { id: 10, name: '만화' },
  { id: 11, name: '사회과학' },
  { id: 12, name: '소설/시/희곡' },
  { id: 13, name: '수험생/자격증' },
  { id: 14, name: '어린이' },
  { id: 15, name: '에세이' },
  { id: 16, name: '여행' },
  { id: 17, name: '역사' },
  { id: 18, name: '예술/대중문화' },
  { id: 19, name: '외국어' },
  { id: 20, name: '유아' },
  { id: 21, name: '인문학' },
  { id: 22, name: '자기계발' },
  { id: 23, name: '잡지' },
  { id: 24, name: '전집/중고전집' },
  { id: 25, name: '중교/역학' },
  { id: 26, name: '중학생참고서' },
  { id: 27, name: '청소년' },
  { id: 28, name: '초등학교참고서' },
  { id: 29, name: '컴퓨터/모바일' },
  { id: 30, name: 'Gift' },
]

const Wrapper = styled.aside`
  width: 256px;
  float: left;
`

const SideBarTitle = styled.h2`
  background-color: ${({ theme }) => theme.colors.main};
  font-family: 'Apple SD Gothic Neo';
  text-align: center;
  padding: 30px 0;
  max-height: 120px;
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
`

const SideBar = ({ setSelectedCategory, categoryCounts }) => {
  return (
    <Wrapper>
      <SideBarTitle>자료 검색</SideBarTitle>
      <SearchListWrapper>
        <CategoryList>
          <ul>
            {categories.map(category => (
              <li key={category.id}>
                <CategoryWrapper>
                  <button
                    key={category.id}
                    style={{
                      textDecoration: 'none',
                      color: 'gray',
                      fontWeight: 400,
                      border: 'none',
                      backgroundColor: ' #f8f8f8',
                      cursor: 'pointer',
                    }}
                    onMouseOver={e => {
                      e.target.style.fontWeight = 800
                    }}
                    onMouseOut={e => {
                      e.target.style.fontWeight = 400
                    }}
                    onClick={() => setSelectedCategory(category.name)}
                  >
                    {category.name} ({categoryCounts[category.name] || 0})
                  </button>
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
