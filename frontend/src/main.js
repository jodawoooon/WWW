import Vue from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import VueCookies from "vue-cookies";

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap-vue/dist/bootstrap-vue.css";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import VueGeolocationApi from "vue-geolocation-api";

import vueMoment from "vue-moment";
Vue.use(vueMoment);

Vue.use(VueGeolocationApi);
Vue.use(ElementUI);
// Install BootstrapVue
Vue.use(BootstrapVue);
// Install BootstrapVue icon
Vue.use(IconsPlugin);
Vue.config.productionTip = false;

Vue.use(VueCookies);
Vue.$cookies.config("7d");

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
