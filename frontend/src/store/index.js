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
      userId: '',
      name: '',
      nickname: '',
      sido: '',
      gugun:'',
      dong:'',
    },
    userProfile: {
      userId:'',
      name: '',
      nickname: '',
    },
    userInfo: {
      name: "김싸피",
    },
    location: {
      lat: "37.47085376325646",
      lng: "126.95986125726075",
      dong: "봉천동",
    },
    curCourse: {
      id: 1016,
      title: "경기도 삼남길 05코스 중복들길",
      address: "경기 과천시 중앙동",
      lat: "37.4265296",
      lng: "126.986664",
      score: "4.8",
      distance: "7",
      time: "120",
      kcal: "80",
      detail:
        "서호공원에서 출발하여 수원시와 화성시의 경계인 배양교에 이르는 구간입니다. 서호(축만제)는 정조임금이 수원을 신도시로 개발하면서 농업을 장려하기 위해 조성한 인공저수지로 제방 너머에는 아직도 농촌진흥청 시험장이 남아있습니다. 서호 남쪽의 항미정에서 바라본 해질녘 풍경은 손꼽히는 절경입니다. 서호공원을 지나 길을 따라가면 지금은 운영하지 않는 옛 수인선 철로를 만날 수도 있습니다. 수원비행장 서쪽으로 펼쳐진 중복들을 따라 계속 걷다보면 배양교에서 화성시와 만나게 됩니다. ",
      conv: [
        {
          name: "test",
          address: "test",
          latitude: "37.4265296",
          longitude: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
      ],
      cafe: [
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
        {
          title: "test",
          address: "test",
          lat: "37.4265296",
          lng: "126.986664",
        },
      ],

      isBookmarked: false,
      sidoList: [],
      gugunList: [],
      dongList: []
    },
  },
  mutations: {
    SET_MAIN_TO_START(state) {
      state.curCourse.title = " ";
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
    SET_MORE_USER_INFO(state, payload){
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
      // state.curCourse.kcal = payload.kcal;
      // state.curCourse.detail = payload.detail;
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
    SET_SIDO_LIST(state, payload){
      state.sidoList = payload.sidoList;
    },
    SET_GUGUN_LIST(state, payload){
      state.gugunList = payload.gugunList;
    },
    SET_DONG_LIST(state, payload){
      state.dongList = payload.dongList;
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
    getterSidoList(state){
      return state.sidoList;
    },
    getterGugunList(state){
      return state.gugunList;
    },
    getterDongList(state){
      return state.dongList;
    }
  },
  modules: {},
  plugins: [createPersistedState()],
});
