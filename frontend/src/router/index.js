import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store/index.js";
import Swal from "sweetalert2";

// 메인
import Main from "@/views/main/Main";
// 메인
import Index from "@/views/main/Index";

// 코스 정보
import Course from "@/views/course/Course";

// 코스 상세
import CourseDetail from "@/views/course/CourseDetail";

// 유저 산책 정보
import MyWalk from "@/views/user/Walk";

// 로그인
import Login from "@/views/user/Login";

// 관심 코스 정보
import MyCourse from "@/views/mypage/MyCourse";

// 산책 기록 페이지
import Record from "@/views/walk/Record";
// Redirect
import KakaoCallback from "@/views/user/kakao-callback";

// 회원가입
import SignUp from "@/views/user/Signup";

Vue.use(VueRouter);

const routes = [
  { path: "/index.html", component: Index, alias: "/" },
  // 메인

  { path: "", redirect: "/index" },
  { path: "/", redirect: "/index" },

  {
    path: "/main",
    name: "Main",
    component: Main,
    meta: { requiresLocation: true },
  },
  { path: "/index", name: "Index", component: Index },
  // 동네 코스 목록
  { path: "/course", component: Course, meta: { requiresLocation: true } },

  // 동네 코스 상세 목록
  { path: "/course/detail", component: CourseDetail },

  // 나의 산책 분석
  { path: "/user/walk", component: MyWalk, meta: { requiresAuth: true } },

  // 로그인
  { path: "/login", component: Login },

  { path: "/user/mycourse", component: MyCourse, meta: { requiresAuth: true } },

  // 산책 기록 페이지
  { path: "/record", component: Record, meta: { requiresLocation: true } },
  // Redirect
  { path: "/kakao/callback", component: KakaoCallback },

  // 회원가입
  { path: "/signup", name: "Signup", component: SignUp },
  {
    path: "/notfound",
    name: "NotFound",
    component: () => import("@/views/main/NotFound.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/notfound",
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

router.beforeEach(function (to, from, next) {
  if (to.matched.some((record) => record.meta.requiresLocation)) {
    // 위치정보 동의가 필요한 페이지라면
    console.log(store.getters.getIsAgree);
    if (!store.getters.getIsAgree) {
      // 동의 받았는지 확인한다.
      Swal.fire({
        width: 250,
        titleSize: 10,
        title: "위치 정보 수집을 허용하셔야 <br/> 서비스 이용이 가능합니다🏃‍♂️",
      });
      next({ path: "/index" });
    } else {
      next();
    }
  } else if (to.matched.some((record) => record.meta.requiresAuth)) {
    //로그인이 필요한 페이지라면

    if (
      store.getters.getLoginUserInfo.userId === null ||
      store.getters.getLoginUserInfo.userId === undefined ||
      store.getters.getLoginUserInfo.userId == ""
    ) {
      // 확인한다.
      Swal.fire({
        width: 250,
        titleSize: 10,
        title: "로그인 회원만 <br/> 이용 가능합니다🔐",
      });
      next({ path: "/login" });
    } else {
      next();
    }
  } else {
    next();
  }
});

// URL이 변경된 후, 현재 Path를 저장하는 함수
router.afterEach((to) => {
  console.log(to.path);

  if (to.path == "/index") {
    store.commit("SET_IS_INDEX");
  } else if (to.path == "/") {
    store.commit("SET_IS_INDEX");
  } else {
    store.commit("SET_IS_NOT_INDEX");
  }
});
