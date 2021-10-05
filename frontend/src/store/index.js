import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isAgree: false,
    isIndex: true,
    prevSilde: "",
    prevPage: "",
    curPage: "Main",
    tokens: {
      accessToken: "",
      accessTokenExpire: "",
      refreshToken: "",
      refreshTokenExpire: "",
    },
    loginUserInfo: {
      isLogin: false,
      isLogout: true,
      userId: "",
      name: "",
      nickname: "",
      sido: "",
      gugun: "",
      dong: "",
    },
    userProfile: {
      userId: "",
      name: "",
      nickname: "",
    },
    userInfo: {
      name: "",
    },
    location: {
      lat: "",
      lng: "",
      dong: "",
    },
    curCourse: {
      id: "",
      title: "",
      address: "",
      lat: "",
      lng: "",
      score: "",
      distance: "",
      time: "",
      kcal: "",
      detail: "",
      conv: [],
      cafe: [],

      isBookmarked: false,
      sidoList: [],
      gugunList: [],
      dongList: [],
    },
  },
  mutations: {
    SET_MAIN_TO_START(state) {
      state.curCourse.title = " ";
      state.curCourse.id = "";
    },
    SET_IS_INDEX(state) {
      state.isIndex = true;
    },
    SET_IS_AGREE(state) {
      state.isAgree = true;
    },
    SET_IS_NOT_AGREE(state) {
      state.isAgree = false;
    },
    SET_IS_NOT_INDEX(state) {
      state.isIndex = false;
    },
    SET_USER_INFO(state, payload) {
      state.loginUserInfo.userId = payload.userId;
      state.loginUserInfo.name = payload.name;
    },
    SET_MORE_USER_INFO(state, payload) {
      state.loginUserInfo.nickname = payload.nickname;
      state.loginUserInfo.sido = payload.sido;
      state.loginUserInfo.gugun = payload.gugun;
      state.loginUserInfo.dong = payload.dong;
    },
    SET_USER_TOKEN(state, payload) {
      state.tokens.accessToken = payload.accessToken;
      state.tokens.refreshToken = payload.refreshToken;
      state.tokens.accessTokenExpire = payload.accessTokenExpire;
      state.tokens.refreshTokenExpire = payload.refreshTokenExpire;
    },
    SET_USER_LOCATION(state, payload) {
      state.location.lat = payload.lat;
      state.location.lng = payload.lng;
      state.location.dong = payload.dong;
      state.location.do = payload.do;
    },
    SET_CUR_COURSE(state, payload) {
      state.curCourse.id = payload.id;
      state.curCourse.title = payload.title;
      state.curCourse.address = payload.address;
      state.curCourse.lat = payload.lat;
      state.curCourse.lng = payload.lng;
      state.curCourse.score = payload.score;
      state.curCourse.distance = payload.distance;
      state.curCourse.time = payload.time;
      state.curCourse.kcal = payload.kcal;
      state.curCourse.detail = payload.detail;
      state.curCourse.cafe = payload.cafe;
      state.curCourse.conv = payload.conv;
      state.curCourse.isBookmarked = payload.isBookmarked;
    },
    SET_CUR_COURSE_LIKE(state, payload) {
      state.curCourse.isBookmarked = payload.isBookmarked;
    },
    SET_CUR_TITLE(state, payload) {
      state.curCourse.title = payload;
    },
    SET_CUR_PAGE(state, curPage) {
      state.curPage = curPage;
    },
    SET_PREV_PAGE(state, prevPage) {
      state.prevPage = prevPage;
    },
    SET_PREV_SILDE(state, prevSilde) {
      state.prevSilde = prevSilde;
    },
    SET_SIDO_LIST(state, payload) {
      state.sidoList = payload.sidoList;
    },
    SET_GUGUN_LIST(state, payload) {
      state.gugunList = payload.gugunList;
    },
    SET_DONG_LIST(state, payload) {
      state.dongList = payload.dongList;
    },
    SET_IS_LOGIN(state, payload) {
      state.loginUserInfo.isLogin = payload.isLogin;
      state.loginUserInfo.isLogout = payload.isLogout;
    },
  },
  actions: {},
  getters: {
    getIsIndex(state) {
      return state.isIndex;
    },
    getIsAgree(state) {
      return state.isAgree;
    },
    getLoginUserInfo(state) {
      return state.loginUserInfo;
    },
    getUserName(state) {
      return state.userInfo.name;
    },
    getDong(state) {
      return state.location.dong;
    },
    getCurPage(state) {
      return state.curPage;
    },
    getLocation(state) {
      return state.location;
    },
    getCourseDetail(state) {
      return state.curCourse;
    },
    getPrevPage(state) {
      return state.prevPage;
    },
    getPrevSilde(state) {
      return state.prevSilde;
    },
    getterSidoList(state) {
      return state.sidoList;
    },
    getterGugunList(state) {
      return state.gugunList;
    },
    getterDongList(state) {
      return state.dongList;
    },
    getterLoginInfo(state) {
      return state.loginUserInfo.isLogin;
    },
    getterLogoutInfo(state) {
      return state.loginUserInfo.isLogout;
    },
  },
  modules: {},
  plugins: [createPersistedState()],
});
