import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userInfo: {
      name: "김싸피",
    },
    location: {
      lat: "37.508690392046",
      lng: "127.05618275253",
      dong: "삼성동",
    },
  },
  mutations: {
    SET_USER_LOCATION(state, payload) {
      state.location.lat = payload.lat;
      state.location.lng = payload.lng;
      state.location.dong = payload.dong;
    },
  },
  actions: {},
  getters: {
    getUserName(state) {
      return state.userInfo.name;
    },
    getDong(state) {
      return state.location.dong;
    },
    getLocation(state) {
      return state.location;
    },
  },
  modules: {},
});
