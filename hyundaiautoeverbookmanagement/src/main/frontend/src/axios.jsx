import axios from 'axios'

const apiClient = axios.create()

apiClient.interceptors.response.use(
  response => {
    return response
  },
  async error => {
    const originalRequest = error.config

    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true

      const res = await apiClient.post('/api/auth/reissue', {})
      const newToken = res.data.accessToken

      localStorage.setItem('token', newToken)
      apiClient.defaults.headers.common['Authorization'] = `Bearer ${newToken}`

      return apiClient(originalRequest)
    }

    return Promise.reject(error)
  }
)

export default apiClient
