module.exports = {
  outputDir: "../backend/src/main/resources/dist",

  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      '/v1':{
        target:"https://openapi.naver.com",
        // pathRewrite:{'^/':''},
        changeOrigin:true,
        secure:false
      },
      
    },
  },

  pluginOptions: {
    express: {
      shouldServeApp: true,
      serverDir: "./srv",
    },
  },
};
