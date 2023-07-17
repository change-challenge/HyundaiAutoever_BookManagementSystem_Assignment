import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Logo from '../../assets/logo.svg'
import * as S from './style'
import { Text, SearchBar } from '../index'

const basicNavMenu = [
  {
    linkTo: '/signup',
    text: '회원가입',
  },
  {
    linkTo: '/login',
    text: '로그인',
  },
]

function HeaderWithSearchBarLogOut() {
  const navigate = useNavigate()

  const [searchValue, setSearchValue] = useState('')
  const [isSearchRequested, setIsSearchRequested] = useState(false)
  useEffect(() => {
    if (isSearchRequested && searchValue) {
      sessionStorage.setItem('lastSearch', searchValue) // Save the search value
      navigate(`/search?query=${searchValue}`)
      setIsSearchRequested(false)
    }
  }, [searchValue, isSearchRequested, navigate])

  const handleSearchValueChange = value => {
    setSearchValue(value)
    setIsSearchRequested(true)
  }

  const logoClick = () => {
    navigate('/')
  }

  return (
    <S.HeaderContainer>
      <S.Layout>
        <S.LogoWrapper onClick={logoClick}>
          <img
            src={Logo}
            alt="logo"
            width="120px"
            height="36px"
            draggable={false}
          />
          <Text
            text="현대오토에버 도서관리시스템"
            color={({ theme }) => theme.colors.main}
            fontWeight={'bold'}
            fontSize={({ theme }) => theme.fontSize.sz10}
            text-align={'center'}
          />
        </S.LogoWrapper>
        <S.SearchContainer>
          <SearchBar
            width="500px"
            height="50px"
            onSearchValueChange={handleSearchValueChange}
          />
        </S.SearchContainer>
        <S.NavWrapper>
          <S.LinkButtonWrapper>
            {basicNavMenu.map(menu => {
              return (
                <li key={menu.linkTo}>
                  <Link
                    style={{
                      marginRight: '2rem',
                      padding: '0 10px',
                      lineHeight: '1em',
                      height: '1em',
                    }}
                    to={menu.linkTo}
                  >
                    <span
                      style={{
                        display: 'inline-block',
                      }}
                    >
                      <Text
                        text={menu.text}
                        color={({ theme }) => theme.colors.main}
                        fontSize={({ theme }) => theme.fontSize.sz16}
                        fontFamily={'Roboto'}
                        cursor={'pointer'}
                      />
                    </span>
                  </Link>
                </li>
              )
            })}
          </S.LinkButtonWrapper>
        </S.NavWrapper>
      </S.Layout>
    </S.HeaderContainer>
  )
}

export default HeaderWithSearchBarLogOut
