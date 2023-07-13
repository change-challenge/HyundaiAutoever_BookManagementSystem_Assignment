const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function (app) {
  app.use(
    '/api', // API가 /api로 시작하는 요청을 대상으로 함
    createProxyMiddleware({
      target: 'http://localhost:8080', // 백엔드 서버 주소
      changeOrigin: true,
    })
  )
  app.use(
    '/aladin-api', // Aladin API를 호출할 때 사용하는 라우트
    createProxyMiddleware({
      target: 'http://www.aladin.co.kr/ttb/api', // Aladin API 서버 주소
      changeOrigin: true,
      pathRewrite: {
        '^/aladin-api': '', // 요청 URL의 /aladin-api 부분을 제거하고 요청을 전달
      },
    })
  )
}
