import * as S from './style'

export default function Main() {
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
      </S.InnerWrapper>
    </S.Container>
  )
}
