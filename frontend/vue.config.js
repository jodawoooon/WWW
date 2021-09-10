module.exports = {
    configureWebpack: {},
    devServer: {
      host: '0.0.0.0',
      https: false,
      port: 8083,
      open: true,
      proxy: {
        '/api': {
          target: 'http://localhost:8080/',
          logLevel: 'debug',
        },
      },
      historyApiFallback: true,
      hot: true,
      disableHostCheck: true,
    },
    transpileDependencies: ['vuetify'],
    outputDir: '../backend/src/main/resources/dist',
  };
  