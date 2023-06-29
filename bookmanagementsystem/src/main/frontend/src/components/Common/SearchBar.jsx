import styled from 'styled-components'
import SearchIcon from '../../assets/searchIcon.svg'
import { useState } from 'react'

const SearchWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: ${({ width }) => width};
  max-height: 100%;
  background-color: #f2f1fa;
  border-radius: 25px;
  overflow-y: auto;
  margin: auto;
`

const SearchForm = styled.form`
  width: ${({ width }) => width};
  height: ${({ width }) => width / 10};
  min-height: 50px;
  padding: 0 16px;
  gap: 16px;
  display: flex;
  align-items: center;
  overflow: hidden;
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
  width: 25px;
  height: 25px;
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

const IconButton = ({ icon, type = 'button', ...rest }) => {
  return (
    <Button type={type} {...rest}>
      <img src={icon} alt="logo" width="20px" height="20px" />
    </Button>
  )
}

const SearchBar = ({ onSubmit, placeholder, width }) => {
  const [searchValue, setSearchValue] = useState('')

  const handleInputChange = e => {
    setSearchValue(e.target.value)
  }

  const handleClearClick = () => {
    setSearchValue('')
  }

  return (
    <SearchWrapper width={width}>
      <SearchForm onSubmit={onSubmit} width={width}>
        <SearchInput
          type="text"
          value={searchValue}
          onChange={handleInputChange}
          placeholder={placeholder || '도서명을 입력해주세요.'}
        />
        <ClearButton visible={searchValue !== ''} onClick={handleClearClick}>
          X
        </ClearButton>
        <IconButton icon={SearchIcon} type="submit" />
      </SearchForm>
    </SearchWrapper>
  )
}

export default SearchBar
