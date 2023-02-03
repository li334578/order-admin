import Vue from 'vue'
import Router from 'vue-router'

const Home = () => import('./components/Home.vue')
const Welcome = () => import('./components/Welcome.vue')
const HelloWorld = () => import('./components/HelloWorld.vue')
const Cate = () => import( './components/List.vue')

Vue.use(Router)

const router = new Router({
  routes: [
    { path: '/', redirect: '/home' },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/categories', component: Cate },
        { path: '/HelloWorld', component: HelloWorld },
      ]
    }
  ]
})

// 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数，表示放行
//   //     next()  放行    next('/login')  强制跳转

//   if (to.path === '/login') return next()
//   // 获取token
//   const tokenStr = window.sessionStorage.getItem('token')
//   if (!tokenStr) return next('/login')
//   next()
// })

export default router
