import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userInfo: {
      name: "김싸피",
    },
  },
  mutations: {},
  actions: {},
  getters: {
    getUserName(state) {
      return state.userInfo.name;
    },
  },
  modules: {},
});
