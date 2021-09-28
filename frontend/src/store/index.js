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
    },
  },
  mutations: {
    SET_USER_LOCATION(state, payload) {
      state.location.lat = payload.lat;
      state.location.lng = payload.lng;
      state.location.dong = payload.dong;
    },
    SET_CUR_COURSE(state, payload) {
      state.curCourse.id = payload.id;
      state.curCourse.title = payload.title;
      state.curCourse.address = payload.address;
      //..... 현재 선택한 산책로 정보 commit
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
    getCourseDetail(state) {
      return state.curCourse;
    },
  },
  modules: {},
});
