import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userInfo: {
      name: "김싸피",
    },
    location: {
      dong: "싸피동",
    },
  },
  mutations: {},
  actions: {},
  getters: {
    getUserName(state) {
      return state.userInfo.name;
    },
    getDong(state) {
      return state.location.dong;
    },
  },
  modules: {},
});
