import styled from 'styled-components'
import SearchIcon from '../../assets/searchIcon.svg'
import { useState } from 'react'

const SearchWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  max-height: 100%;
  background-color: #f2f1fa;
  border-radius: 25px;
  overflow-y: auto;
  /*margin: auto;*/
`

const SearchForm = styled.form`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
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

const SearchBar = ({ onSubmit, placeholder, width, height }) => {
  const [searchValue, setSearchValue] = useState('')

  const handleInputChange = e => {
    setSearchValue(e.target.value)
    console.log('SearchBar handleInputChange:', e.target.value)
  }

  const handleClearClick = () => {
    setSearchValue('')
  }

  const handleSubmit = event => {
    event.preventDefault() // form의 기본 submit 동작을 막음
    if (onSubmit) {
      // onSubmit이 제공되었는지 확인
      onSubmit(searchValue) // onSubmit 함수를 searchValue와 함께 호출
    }
    console.log(' SearchBar handleSubmit : ', searchValue)
  }

  return (
    <SearchWrapper width={width} height={height}>
      <SearchForm onSubmit={handleSubmit} width={width} height={height}>
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
