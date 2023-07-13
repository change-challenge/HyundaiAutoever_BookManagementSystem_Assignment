import React from 'react'
import styled from 'styled-components'

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

const Button = styled.button`
  background-color: ${({ active }) => (active ? 'white' : '#6A4AFC')};
  color: ${({ active }) => (active ? 'black' : 'white')};
  border: none;
  width: 50%;
  height: 50%;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  font-family: 'Apple SD Gothic Neo';
  border-radius: 15px;
  &:hover {
    background-color: ${({ active }) => (active ? 'purple' : 'white')};
    color: ${({ active }) => (active ? 'white' : 'black')};
  }
`

const data = [
  {
    id: '01',
    status: '대출불가',
    reservation: {
      count: 0,
      status: '대출중',
    },
    dueDate: '2023-06-30',
  },
]

const handleClick = () => {
  alert('hey!')
}

const BookCountTable = ({ bookId }) => {
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
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>
                {item.status}
                <br />
                {`[대출중]`}
                <br />
                예약: {item.reservation.count}명
              </TableCell>
              <TableCell>{item.dueDate}</TableCell>
              <TableCell>
                <Button active={false} onClick={handleClick}>
                  대여하기
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableWrapper>
  )
}

export default BookCountTable
