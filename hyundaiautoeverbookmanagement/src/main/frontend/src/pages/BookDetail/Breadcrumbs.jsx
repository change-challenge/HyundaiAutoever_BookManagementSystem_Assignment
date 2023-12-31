import Link from '@mui/material/Link'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'

function handleClick() {
  console.info('You clicked a breadcrumb.')
}

export default function CustomSeparator() {
  const searchValue = sessionStorage.getItem('lastSearch')

  const breadcrumbs = [
    <Link underline="hover" key="1" color="inherit" href="/">
      홈
    </Link>,
    <Link
      underline="hover"
      key="2"
      color="inherit"
      href={`/search?query=${searchValue}`}
    >
      도서 검색
    </Link>,
    <Typography key="3" color="text.primary">
      상세페이지
    </Typography>,
  ]

  return (
    <Breadcrumbs separator="›" aria-label="breadcrumb">
      {breadcrumbs}
    </Breadcrumbs>
  )
}
