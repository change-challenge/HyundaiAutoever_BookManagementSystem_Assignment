import styled from 'styled-components'
import SearchIcon from '../../assets/searchIcon.svg'
import { useState } from 'react'

const SearchContainer = styled.div`
  display: flex;
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  max-height: 100%;
  background-color: #f2f1fa;
  border-radius: 25px;
  overflow-y: auto;
`

const SearchWrapper = styled.div`
  align-items: center;
  display: flex;
`

const SearchForm = styled.form`
  min-width: 400px;
  padding: 0 16px;
  gap: 16px;
`

const SearchInput = styled.input`
  border: none;
  width: 100%;
  height: 100%;
  background-color: ${({ theme }) => theme.colors.main3};
  color: ${({ theme }) => theme.colors.grey9};
  text-align: left;
  padding-left: 20px;
  font-size: medium;
  &:focus {
    outline: none;
  }
`

const Button = styled.button`
  background-color: transparent;
  display: flex;
  align-items: center;
  width: 30px;
  height: 70%;
  cursor: pointer;
  border: none;
`

const ClearButton = styled.button`
  background-color: transparent;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30px;
  height: 30px;
  cursor: pointer;
  border: none;
  opacity: ${({ visible }) => (visible ? '1' : '0')};
  transition: opacity 0.3s ease;
  font-weight: 500;
  font-size: 17px;
  color: ${({ theme }) => theme.colors.black};

  &:focus {
    outline: none;
  }
`

const IconButton = ({ icon, type = 'submit', ...rest }) => {
  return (
    <Button type={type} {...rest}>
      <img src={icon} alt="logo" width="20px" height="20px" />
    </Button>
  )
}

const SearchBar = ({ onSearchValueChange, placeholder, width, height }) => {
  const [searchValue, setSearchValue] = useState('')

  const handleInputChange = e => {
    const value = e.target.value
    const regExp = /^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]*$/
    if (regExp.test(value)) {
      setSearchValue(value)
    } else {
      const newValue = value.replace(/[^ㄱ-ㅎ가-힣a-zA-Z0-9\s]/g, '')
      setSearchValue(newValue)
    }
  }

  const handleClearClick = () => {
    setSearchValue('')
  }

  const handleSubmit = event => {
    event.preventDefault()
    onSearchValueChange(searchValue)
  }

  return (
    <SearchContainer width={width} height={height}>
      <SearchWrapper>
        <SearchForm onSubmit={handleSubmit}>
          <SearchInput
            type="text"
            value={searchValue}
            onChange={handleInputChange}
            placeholder={placeholder || '도서명을 입력해주세요.'}
          />
        </SearchForm>
        <ClearButton visible={searchValue !== ''} onClick={handleClearClick}>
          X
        </ClearButton>
        <IconButton icon={SearchIcon} type="submit" />
      </SearchWrapper>
    </SearchContainer>
  )
}

export default SearchBar
