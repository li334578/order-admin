/*
 * @Author: your name
 * @Date: 2020-05-07 08:57:54
 * @LastEditTime: 2020-11-17 09:36:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \HNQX_YWNWc:\Users\admin\Desktop\产品库\config\index.js
 */
'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
    dev: {
        // Paths
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {
            '/apis': {
                // 使用"/api"来代替"http://f.apiplus.c"
                // target: 'http://192.168.3.18:8080', // 源地址
                // target: 'http://119.3.36.107:81/', // 源地址
                // target: 'http://192.168.3.222:8084', // 源地址
                target: 'http://j6entq.natappfree.cc', // 源地址
                changeOrigin: true, // 改变源
                pathRewrite: {
                    '^/apis': '' // 路径重写
                }
            },
            '/api': {
                // 使用"/api"来代替"http://f.apiplus.c"
                // target: 'http://192.168.3.18:8080', // 源地址
                target: 'http://119.3.36.107:81/', // 源地址
                // target: 'http://192.168.3.222:8084', // 源地址
                // target: 'http://www.hainanqx.cn:8090', // 源地址
                changeOrigin: true, // 改变源
                pathRewrite: {
                    '^/apis': '' // 路径重写
                }
            },
        },

        // Various Dev Server settings:
        host: '127.0.0.1',
        // host: '192.168.3.62', // can be overwritten by process.env.HOST
        port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
        // host: 'http://119.3.36.107:81/', // can be overwritten by process.env.HOST
        // port: 8090, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
        autoOpenBrowser: false,
        errorOverlay: true,
        notifyOnErrors: true,
        poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

        /**
         * Source Maps
         */

        // https://webpack.js.org/configuration/devtool/#development
        devtool: 'cheap-module-eval-source-map',

        // If you have problems debugging vue-files in devtools,
        // set this to false - it *may* help
        // https://vue-loader.vuejs.org/en/options.html#cachebusting
        cacheBusting: true,

        cssSourceMap: true
    },

    build: {
        // Template for index.html
        index: path.resolve(__dirname, '../dist/index.html'),

        // Paths
        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',
        assetsPublicPath: './',

        /**
         * Source Maps
         */

        productionSourceMap: true,
        // https://webpack.js.org/configuration/devtool/#production
        devtool: '#source-map',

        // Gzip off by default as many popular static hosts such as
        // Surge or Netlify already gzip all static assets for you.
        // Before setting to `true`, make sure to:
        // npm install --save-dev compression-webpack-plugin
        productionGzip: false,
        productionGzipExtensions: ['js', 'css'],

        // Run the build command with an extra argument to
        // View the bundle analyzer report after build finishes:
        // `npm run build --report`
        // Set to `true` or `false` to always turn it on or off
        bundleAnalyzerReport: process.env.npm_config_report
    }
}