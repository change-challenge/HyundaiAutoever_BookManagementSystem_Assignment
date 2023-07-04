import React, { forwardRef } from 'react'
import styled from 'styled-components'

const InputWrap = styled.div`
  display: flex;
  border-radius: 8px;
  padding: 16px;
  background-color: white;
  border: 1px solid ${({ theme }) => theme.colors.grey4};
  margin-top: ${({ marginTop }) => marginTop}px;
  margin-left: ${({ marginLeft }) => marginLeft}px;
  margin-right: ${({ marginRight }) => marginRight}px;

  &:focus-within {
    border: 1.5px solid ${({ theme }) => theme.colors.main};
  }
`

const Input = styled.input`
  width: ${({ width }) => width}px;
  outline: none;
  border: none;
  height: 17px;
  font-size: 14px;
  font-weight: 400;

  &::placeholder {
    color: ${({ theme }) => theme.colors.grey4};
  }
`

const LabelInput = forwardRef(
  (
    {
      type = 'text',
      value = '',
      onChange,
      placeholder = 'Default',
      marginTop = 15, // 기본값 설정
      marginLeft = 0, // 기본값 설정
      marginRight = 0, // 기본값 설정
      width = 100, // 기본값 설정
      ...restProps
    },
    ref
  ) => {
    return (
      <InputWrap
        marginTop={marginTop}
        marginLeft={marginLeft}
        marginRight={marginRight}
      >
        <Input
          type={type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          width={width}
          {...restProps}
          ref={ref}
        />
      </InputWrap>
    )
  }
)

LabelInput.displayName = 'LabelInput'

export default LabelInput
