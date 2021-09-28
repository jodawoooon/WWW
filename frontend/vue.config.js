module.exports = {

  outputDir: "../backend/src/main/resources/dist",
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
    }

    
  