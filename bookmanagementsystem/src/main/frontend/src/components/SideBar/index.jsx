import * as S from './style'
import { Link } from 'react-router-dom'

const menuList = [
  { linkTo: '/admin', text: '홈' },
  { linkTo: '/admin/user', text: '회원 관리' },
  { linkTo: '/admin/rent', text: '대출 관리' },
  { linkTo: '/admin/book', text: '도서 관리' },
  { linkTo: '/admin/wishbook', text: '신청도서 관리' },
]

const SideBar = () => {
  return (
    <S.Wrapper>
      {menuList.map(item => (
        <Link
          key={item.linkTo}
          to={item.linkTo}
          style={{ textDecoration: 'none' }}
        >
          <S.Menu text={item.text} />
        </Link>
      ))}
    </S.Wrapper>
  )
}

export default SideBar
