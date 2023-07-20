import { useState, useEffect } from 'react'
import { Text, LabelInput, Title } from '../../components/index'
import * as S from './style'
import Button from '@mui/material/Button'
import Stack from '@mui/material/Stack'
import apiClient from '../../axios'
import { fetchUserInfo } from '../../context/UserContext'
import { useNavigate } from 'react-router-dom'
import SearchResultModal from './SearchResultModal'
import { useAlert } from '../../context/AlertContext'
import { useConfirm } from '../../context/ConfirmContext'

// 컴포넌트 시작!
export default function WishBook() {
  const showConfirm = useConfirm()
  const showAlert = useAlert()
  const navigate = useNavigate()
  const [wishBookName, setWishBookName] = useState('')
  const [wishBookAuthor, setWishBookAuthor] = useState('')
  const [wishBookPublisher, setWishBookPublisher] = useState('')
  const [wishBookISBN, setWishBookISBN] = useState('')
  const [user, setUser] = useState(null)

  const [bookSearchResults, setBookSearchResults] = useState([])
  const [openModal, setOpenModal] = useState(false)
  const [wishBook, setWishBook] = useState({})

  const createWish = (book, user) => {
    const categories = [
      '가정/요리/뷰티',
      '건강/취미/레저',
      '경제경영',
      '고등학교참고서',
      '고전',
      '과학',
      '달력/기타',
      '대학교재/전문서적',
      '만화',
      '사회과학',
      '소설/시/희곡',
      '수험생/자격증',
      '어린이',
      '에세이',
      '여행',
      '역사',
      '예술/대중문화',
      '외국어',
      '유아',
      '인문학',
      '자기계발',
      '잡지',
      '전집/중고전집',
      '중교/역학',
      '중학생참고서',
      '청소년',
      '초등학교참고서',
      '컴퓨터/모바일',
      'Gift',
    ]

    const bookCategory = categories.includes(book.category)
      ? book.category
      : '달력/기타'

    return {
      id: null,
      status: 'Pending',
      email: user.email,
      book: {
        title: book.title,
        author: book.author,
        publisher: book.publisher,
        category: bookCategory,
        info: book.info,
        rent_count: 0,
        isbn: book.isbn,
        cover: book.cover,
        pubDate: book.pubDate,
      },
    }
  }

  useEffect(() => {
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
      if (!userInfo) {
        showAlert('로그인이 필요한 기능입니다.')
        navigate('/')
      }
      console.log('user : ', user)
    }
    getUserInfo()
  }, [])

  const onClickWishBook = () => {
    if (!wishBookName || !wishBookAuthor || !wishBookPublisher) {
      showAlert('희망도서명, 저자, 발행자는 필수 항목입니다.')
      return
    }
    showConfirm('희망도서를 신청하시겠습니까?', submitWishBook)
  }

  const submitWishBook = () => {
    apiClient
      .post('/api/wish/create', wishBook)
      .then(response => {
        showAlert('성공적으로 희망도서신청을 하였습니다.')
        navigate('/')
      })
      .catch(error => {
        if (error.response.status === 500) {
          showAlert('이미 존재하는 도서입니다.')
        } else {
          showAlert('잠시 뒤에 다시 신청해주세요.')
        }
        console.error(error) // 오류 처리
      })
  }

  const callAladinAPI = async wishBookName => {
    try {
      const response = await apiClient.get('/aladin-api/ItemSearch.aspx', {
        params: {
          TTBKey: process.env.REACT_APP_TTB_KEY,
          Query: wishBookName,
          Output: 'JS',
          Cover: 'Big',
        },
      })
      let dataString = response.data
      dataString = dataString.trim() // 공백 제거
      dataString = dataString.substring(0, dataString.lastIndexOf('}')) + '}' // 마지막 세미콜론 제거

      let dataObject
      try {
        dataObject = JSON.parse(dataString)
      } catch (e) {
        console.error(e)
      }

      console.log('dataObject : ', dataObject)
      const books = dataObject.item.map(book => {
        const categoryParts = book.categoryName.split('>')
        const category =
          categoryParts.length > 1 ? categoryParts[1].trim() : book.categoryName

        return {
          title: book.title,
          author: book.author,
          publisher: book.publisher,
          pubDate: book.pubDate,
          isbn: book.isbn,
          info: book.description,
          cover: book.cover,
          category: category,
        }
      })
      console.log('books ', books)
      return books
    } catch (error) {
      console.error(error)
      return [] // 에러 발생 시 빈 배열 반환
    }
  }

  // 도서 선택 함수
  const handleSelectBook = book => {
    setBookSearchResults([]) // 검색 결과를 비우고

    console.log('book : ', book)

    const wish = createWish(book, user)
    setWishBook(wish)
    console.log('wish : ', wish)
    // 선택한 도서의 정보로 입력 필드를 채움
    setWishBookName(book.title)
    setWishBookAuthor(book.author)
    setWishBookPublisher(book.publisher)
    setWishBookISBN(book.isbn)
    setOpenModal(false)
  }
  const handleSearch = async () => {
    if (!wishBookName) {
      showAlert('희망 도서명을 입력해주세요.')
      return
    }
    const books = await callAladinAPI(wishBookName)
    setBookSearchResults(books)

    setOpenModal(true)
  }

  return (
    user && (
      <S.InnerContainer>
        <Title text="희망도서 정보" />
        <S.ContentWrap>
          <Stack spacing={3}>
            <S.LabelWrapper>
              <S.LabelTitleWrapper>
                <S.EssentialMark>*</S.EssentialMark>
                <Text
                  text="희망도서명"
                  color={({ theme }) => theme.colors.grey5}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              </S.LabelTitleWrapper>
              <LabelInput
                type="text"
                placeholder=""
                value={wishBookName}
                onChange={e => setWishBookName(e.target.value)}
                marginTop="0"
                marginRight="50"
                width="300px"
              />
              <Button size="large" variant="contained" onClick={handleSearch}>
                검색
              </Button>
              <SearchResultModal
                open={openModal}
                books={bookSearchResults}
                onSelectBook={handleSelectBook}
                handleClose={() => setOpenModal(false)}
              />
            </S.LabelWrapper>
            <S.LabelWrapper>
              <S.LabelTitleWrapper>
                <S.EssentialMark>*</S.EssentialMark>
                <Text
                  text="저자"
                  color={({ theme }) => theme.colors.grey5}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              </S.LabelTitleWrapper>
              <LabelInput
                type="text"
                placeholder=""
                value={wishBookAuthor}
                onChange={e => setWishBookAuthor(e.target.value)}
                marginTop="0"
                width="200px"
              />
            </S.LabelWrapper>
            <S.LabelWrapper>
              <S.LabelTitleWrapper>
                <S.EssentialMark>*</S.EssentialMark>
                <Text
                  text="발행자"
                  color={({ theme }) => theme.colors.grey5}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              </S.LabelTitleWrapper>
              <LabelInput
                type="text"
                placeholder=""
                value={wishBookPublisher}
                onChange={e => setWishBookPublisher(e.target.value)}
                marginTop="0"
                width="200px"
              />
            </S.LabelWrapper>
            <S.LabelWrapper>
              <S.LabelTitleWrapper>
                <Text
                  text="ISBN"
                  color={({ theme }) => theme.colors.grey5}
                  fontSize={({ theme }) => theme.fontSize.sz16}
                />
              </S.LabelTitleWrapper>
              <LabelInput
                type="text"
                placeholder=""
                value={wishBookISBN}
                onChange={e => setWishBookISBN(e.target.value)}
                marginTop="0"
                width="200px"
              />
            </S.LabelWrapper>
          </Stack>
        </S.ContentWrap>
        <S.ButtonWrapper>
          <Button size="large" variant="contained" onClick={onClickWishBook}>
            확인
          </Button>
        </S.ButtonWrapper>
        <div style={{ marginTop: '50px', marginBottom: '50px' }}></div>
      </S.InnerContainer>
    )
  )
}
