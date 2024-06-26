import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import '@/assets/global.css'
import request from "@/utils/request";

// 滑块认证
import SlideVerify from 'vue-monoplasty-slide-verify';
Vue.use(SlideVerify)

Vue.prototype.request = request

Vue.config.productionTip = false
Vue.use(ElementUI);

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
