import Vue from 'vue'
import App from './App.vue'
import { get, post,postToken } from './api/axios'
Vue.config.productionTip = false
Vue.prototype.$post = post // 方法注册到vue的原型对象，直接通过this.$get,this.$post发送请求
Vue.prototype.$get = get
Vue.prototype.$postToken = postToken
import ElementUI from 'element-ui';
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
