import React from 'react'
import { useNavigate } from 'react-router-dom'
import Button from '@mui/material/Button'
import Box from '@mui/material/Box'
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'
import WarningAmberIcon from '@mui/icons-material/WarningAmber'
import Stack from '@mui/material/Stack'
import styled from 'styled-components'

const CenteredBox = styled(Box)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh;
  gap: 20px;
`

const Error = () => {
  const navigate = useNavigate()

  const goToPreviousPage = () => {
    navigate(-1)
  }

  const goToHomePage = () => {
    navigate('/')
  }

  return (
    <Container>
      <CenteredBox>
        <WarningAmberIcon style={{ fontSize: 70 }} />
        <Typography variant="h4">해당 페이지를 찾을 수 없습니다.</Typography>
        <Box>
          <Stack direction="row" spacing={2}>
            <Button variant="outlined" onClick={goToPreviousPage}>
              이전 페이지로 가기
            </Button>
            <Button variant="contained" onClick={goToHomePage}>
              홈으로 가기
            </Button>
          </Stack>
        </Box>
      </CenteredBox>
    </Container>
  )
}

export default Error
