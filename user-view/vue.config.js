const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    client: {
      overlay: false
    },
    port: 8082,
    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target: 'http://localhost:8044/',
        secure: true, // 如果是 https ,需要开启这个选项
        changeOrigin: true, // 是否是跨域请求
        pathRewrite: {["^" + process.env.VUE_APP_BASE_API]:''},
      }
    }
  }
})
