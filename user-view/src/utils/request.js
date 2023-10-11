import axios from 'axios'
import ElementUI from "element-ui";
// create an axios instance
const request = axios.create({
  baseURL: '/api', // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
request.interceptors.request.use(
  config => {
    // do something before request is sent
    let token = localStorage.getItem('token');
    if (token != null) {
        config.headers.token = token
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
    (response) => {
      console.log(response)
      if (response.status === 200) {
        return Promise.resolve(response.data)
      } else {
        return Promise.reject(response.data)
      }
    },
    (error) => {
      console.log(error)
      let message = ''
      let status = error.response.success
      switch (status) {
          // 401: 未登录
          // 未登录则跳转登录页面，并携带当前页面的路径
          // 在登录成功后返回当前页面，这一步需要在登录页操作。
        case 401:
          message = '未登录'
          break
          // 403 token过期
          // 登录过期对用户进行提示
          // 清除本地token和清空vuex中token对象
          // 跳转登录页面
        case 403:
          message = '登录过期，请重新登录'
          break
        case 404:
          message = '网络请求不存在'
          break
        case 500:
          message = '服务器出现问题'
          break
        default:
          message = '服务器出现问题'
          break
      }
      return Promise.reject(message)
    },
)

export default request
