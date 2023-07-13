import React from 'react'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import axios from 'axios'
import Button from '@mui/material/Button'

const TableWrapper = styled.div`
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
`

const Table = styled.table`
  width: 100%;
`

const TableHeader = styled.th`
  background-color: ${({ theme }) => theme.colors.grey1};
  /*color: ${({ theme }) => theme.colors.white};*/
  padding: 10px;
  text-align: center;
  font-weight: bold;
  font-family: 'Apple SD Gothic Neo';
  border: 1px solid #ccc;
`

const TableBody = styled.tbody`
  color: ${({ theme }) => theme.colors.grey9};
`

const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #f9f9f9;
  }
`

const TableCell = styled.td`
  padding: 10px;
  border: 1px solid #ccc;
  text-align: center;
  font-family: 'Apple SD Gothic Neo';
  vertical-align: middle;
`

//const Button = styled.button`
//  background-color: ${({ active }) => (active ? 'white' : '#6A4AFC')};
//  color: ${({ active }) => (active ? 'black' : 'white')};
//  border: none;
//  width: 50%;
//  height: 50%;
//  padding: 5px 10px;
//  cursor: pointer;
//  transition: background-color 0.3s, color 0.3s;
//  font-family: 'Apple SD Gothic Neo';
//  border-radius: 15px;
//  &:hover {
//    background-color: ${({ active }) => (active ? 'purple' : 'white')};
//    color: ${({ active }) => (active ? 'white' : 'black')};
//  }
//`

const handleClick = () => {
  alert('hey!')
}

const BookCountTable = ({ bookId }) => {
  const [copyDetail, setCopyDetail] = useState([])

  const fetchRents = async () => {
    const response = await axios.get(`/api/bookdetail/${bookId}`)
    setCopyDetail(response.data)
    console.log(response.data)
  }

  useEffect(() => {
    fetchRents()
  }, [])

  return (
    <TableWrapper>
      <Table>
        <thead>
          <tr>
            <TableHeader>번호</TableHeader>
            <TableHeader>대출상태</TableHeader>
            <TableHeader>반납 예정일</TableHeader>
            <TableHeader>도서 대여</TableHeader>
          </tr>
        </thead>
        <TableBody>
          {copyDetail.map((item, index) => (
            <TableRow key={index}>
              <TableCell>{index + 1}</TableCell>
              <TableCell style={{ fontWeight: 'bold' }}>
                {item.rentEndDate === null ? (
                  '대출가능'
                ) : (
                  <div>
                    대출불가
                    <br />
                    [대출중]
                    <br />
                  </div>
                )}
              </TableCell>
              <TableCell>
                {item.rentEndDate === null ? '-' : `${item.rentEndDate}`}
              </TableCell>
              <TableCell>
                {item.rentEndDate === null ? (
                  <Button variant="contained" onClick={handleClick}>
                    대여하기
                  </Button>
                ) : (
                  <Button variant="contained" onClick={handleClick} disabled>
                    대여하기
                  </Button>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableWrapper>
  )
}

export default BookCountTable
