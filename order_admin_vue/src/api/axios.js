import axios from 'axios'
import { throwErr } from './throwErr' // utils 捕捉服务端http状态码的方法
// import store from '@/store'   //引入vuex的相关操作
import { Message } from 'element-ui' // element Toast的提示
// import router from '../router/index'
// import { MessageBox } from 'element-ui'
// import { Loading } from 'element-ui'
import Cookies from 'js-cookie'
const hosturl = 'http://j6entq.natappfree.cc'
var timeoutflag = null
    // const hosturl = 'http://www.hainanqx.cn:8090/HNMajorApp-api'
    // 过滤请求

// function openWin(e) {
//     const loadingInstance = Loading.service({
//         lock: true,
//         text: 'Loading',
//         spinner: 'el-icon-loading',
//         background: 'rgba(0, 0, 0, 0.7)'
//     })
//     if (e == 'clockV') {
//         // this.$nextTick(() => {
//         // 以服务的方式调用的 Loading 需要异步关闭
//         loadingInstance.close()
//             // })
//     }
// }
axios.interceptors.request.use(
        config => {
            // config 为请求的一些配置 例如：请求头 请求时间 Token  可以根据自己的项目需求个性化配置
            // config.timeout = 10 * 1000 // 请求响应时间
            config.headers = {
                'Content-Type': 'application/json',
                token: Cookies.get('token')
            }
            return config
        },
        error => {
            return Promise.reject(error)
        }
    )
    // axios.defaults.headers.common['token'] = localstorage.getItem('token') // 设置请求头，这个是不是必须的
    // 添加响应拦截器
axios.interceptors.response.use(
    response => {

        if (response.data.code === 0) {
            // 服务端定义的响应code码为0时请求成功
            return Promise.resolve(response.data) // 使用Promise.resolve 正常响应
        } else if (response.data.code == 100) {
                // 服务端定义的响应code码为-9时为未登录，token为空
                // 服务端定义的响应code码为100时为未登录，token为失效
            if (timeoutflag != null) {
                clearTimeout(timeoutflag)
            }
            timeoutflag = setTimeout(function() {
                    // MessageBox.alert(response.data.msg, '请点击确定重新登录', {
                    //   confirmButtonText: '确定',
                    //   callback: action => {
                    //     router.push('/')
                    //     Message.error(response.data.msg)
                    //     Cookies.remove('token')
                    //   }
                    // })
                }, 1000)
                // return Promise.reject(response.data) // 使用Promise.reject 抛出错误和异常
        } else {
            return Promise.reject(response.data)
        }
    },
    error => {
        //   请求路径报错
        if (!error.response) {
            // Message.error('网络连接错误')
            return Promise.resolve(error)
        }
        let topTip = throwErr(error.response.data.status)
            Message.error(topTip)
        return Promise.reject(error)
    }
)

/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export function get(url, data = {}) {
    // openLoading != 'open' ? openWin() : ''
    return new Promise((resolve) => {
        axios
            .get(hosturl+url, data)
            .then(response => {
                resolve(response)
                    // openLoading != 'open' ? openWin('clockV') : ''
            })
            .catch(error => {
                // openLoading != 'open' ? openWin('clockV') : ''
                console.log(error)
                resolve(error)
                    // reject(error)
                    // console.log(error)
            })
    })
}

/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function post(url, data = {}) {
    return new Promise((resolve) => {
        axios
            .post(hosturl+url, data)
            .then(response => {
                resolve(response)
            })
            .catch(error => {
                resolve(error)
                    // Message.error(error.msg)
                    // reject(error)
                    // console.log(error)
            })
    })
}

/**
 * 封装post请求 单独调用获取token
 * @param url
 * @param data
 * @returns {Promise}
 */
export function postToken(url, data = {}) {
    return new Promise((resolve) => {
        axios
            .post(hosturl + url, data)
            .then(response => {
                resolve(response)
            })
            .catch(error => {
                // Message.error(error.msg)
                // reject(error)
                console.log(error)
            })
    })
}