import React, { useEffect, useState } from 'react'
import * as S from './style'
import { Outlet, useNavigate } from 'react-router-dom'
import { SideBar } from '../../../components/index'
import { fetchUserInfo } from '../../../context/UserContext'
import apiClient from '../../../axios'
import { useAlert } from '../../../context/AlertContext'

const checkAdmin = async () => {
  try {
    const response = await apiClient.get('/api/admin/allow')

    console.log('response : ', response)
    if (response.status !== 200) {
      throw new Error('Not an admin')
    }
    return response
  } catch (error) {
    console.error('Error:', error)
    return null
  }
}

const AdminLayout = () => {
  const showAlert = useAlert()
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()
  const [user, setUser] = useState(null)

  useEffect(() => {
    console.log('user : ', user)
    const getUserInfo = async () => {
      const userInfo = await fetchUserInfo()
      setUser(userInfo)
    }
    getUserInfo()
    console.log(user)
  }, [])

  useEffect(() => {
    const fetchAdmin = async () => {
      const result = await checkAdmin()
      if (!result) {
        showAlert('접근 불가!')
        navigate('/')
      }
      setLoading(false)
    }

    fetchAdmin()
  }, [])

  if (loading) {
    return <div>Loading...</div>
  }

  return (
    <S.AdminContainer>
      {loading ? (
        <div>Loading...</div>
      ) : (
        <S.AdminWrapper>
          <SideBar />
          <Outlet />
        </S.AdminWrapper>
      )}
    </S.AdminContainer>
  )
}

export default AdminLayout
