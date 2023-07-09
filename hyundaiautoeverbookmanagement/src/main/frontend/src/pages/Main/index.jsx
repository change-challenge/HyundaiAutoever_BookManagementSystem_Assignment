import { useState } from 'react'
import { SearchBar } from '../../components/index'
import { useNavigate } from 'react-router-dom'
import * as S from './style'
import axios from 'axios'

export default function Main() {
  const navigate = useNavigate()
  const [searchValue, setSearchValue] = useState('')

  const handleSubmit = value => {
    setSearchValue(value) // 검색어 상태 업데이트
    navigate(`/search?query=${value}`) // 검색어를 쿼리 매개변수로 전달
    console.log(' Main handleSubmit : ', value)
  }

  const token = localStorage.getItem('token')

  if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    console.log(
      'Token set in headers:',
      axios.defaults.headers.common['Authorization']
    )
  }
  return (
    <S.Container>
      <S.InnerWrapper>
        <S.UpperLine />
        <S.Title>
          어서오세요,
          <br /> 현대오토에버 도서관리시스템입니다.
        </S.Title>
        <S.SubGuide>
          원하는 책이 있다면, 검색창에 도서를 입력해주세요.
        </S.SubGuide>
        {/*검색창*/}
        <S.SearchContainer>
          <SearchBar width="500px" height="50px" onSubmit={handleSubmit} />
        </S.SearchContainer>
        {/*검색창 끝*/}
      </S.InnerWrapper>
    </S.Container>
  )
}
