import React, { forwardRef } from 'react'
import styled from 'styled-components'

const InputWrap = styled.div`
  display: flex;
  border-radius: 8px;
  padding: 16px;
  margin-top: 8px;
  background-color: white;
  border: 1px solid ${({ theme }) => theme.colors.grey4};
  margin-top: 15px;

  &:focus-within {
    border: 1.5px solid ${({ theme }) => theme.colors.main};
  }
`

const Input = styled.input`
  width: 100%;
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
      ...restProps
    },
    ref
  ) => {
    return (
      <InputWrap>
        <Input
          type={type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          {...restProps}
          ref={ref}
        />
      </InputWrap>
    )
  }
)

LabelInput.displayName = 'LabelInput'

export default LabelInput
