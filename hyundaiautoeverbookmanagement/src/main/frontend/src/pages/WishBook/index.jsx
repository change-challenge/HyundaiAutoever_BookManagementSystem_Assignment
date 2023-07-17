import { useState, useEffect } from 'react'
import { Text, LabelInput, Title } from '../../components/index'
import * as S from './style'
import Button from '@mui/material/Button'
import Stack from '@mui/material/Stack'
import axios from 'axios'
import { fetchUserInfo } from '../../context/UserContext'
import { useNavigate } from 'react-router-dom'
import SearchResultModal from './SearchResultModal'

export default function WishBook() {
  const [wishBookName, setWishBookName] = useState('')
  const [wishBookAuthor, setWishBookAuthor] = useState('')
  const [wishBookPublisher, setWishBookPublisher] = useState('')
  const [wishBookISBN, setWishBookISBN] = useState('')
  const navigate = useNavigate() // useNavigate로 변경
  const [user, setUser] = useState(null)
  // 새로운 상태 변수 추가
  const [bookSearchResults, setBookSearchResults] = useState([])
  const [openModal, setOpenModal] = useState(false)
  const [wishBook, setWishBook] = useState({})

  const createWish = (book, user) => {
    return {
      id: null,
      status: 'Pending',
      email: user.email,
      book: {
        title: book.title,
        author: book.author,
        publisher: book.publisher,
        category: book.category,
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
      console.log('user : ', user)
    }
    getUserInfo()
  }, [])

  const onClickWishBook = () => {
    if (!wishBookName || !wishBookAuthor || !wishBookPublisher) {
      alert('희망도서명, 저자, 발행자는 필수 항목입니다.')
      return
    }
    const confirm = window.confirm('희망도서를 신청하시겠습니까?')
    if (confirm) {
      axios
        .post('/api/wishbook/create', wishBook)
        .then(response => {
          alert('성공적으로 희망도서신청을 하였습니다.')
          navigate('/')
        })
        .catch(error => {
          alert('잠시 뒤에 다시 신청해주세요.')
          console.error(error) // 오류 처리
        })
    }
  }

  const callAladinAPI = async wishBookName => {
    try {
      const response = await axios.get('/aladin-api/ItemSearch.aspx', {
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
      // 배열로 변환된 data.item을 이용
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
      alert('희망 도서명을 입력해주세요.')
      return
    }
    const books = await callAladinAPI(wishBookName)
    setBookSearchResults(books)

    setOpenModal(true)
  }

  return (
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
}
