import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Login from '@/views/Login.vue';
import Regist from '@/views/Regist.vue';
import Userinfo from '@/views/Userinfo.vue';
import Notice from '@/views/Notice.vue';
import QnA from '@/views/QnA.vue';
import Board from '@/views/Board.vue';
import BoardReg from '@/views/BoardReg.vue';
import store from '@/store';

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  let token = localStorage.getItem('access-token');
  if (store.state.userInfo == null && token) {
    await store.dispatch('GET_MEMBER_INFO', token);
  }
  if (store.state.userInfo === null) {
    alert('로그인이 필요한 페이지입니다..');
    // next({ name: "login" });
    router.push({ name: 'login' });
  } else {
    next();
  }
};

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/userinfo',
    name: 'userinfo',
    beforeEnter: onlyAuthUser,
    component: Userinfo,
  },
  {
    path: '/regist',
    name: 'regist',
    component: Regist,
  },
  {
    path: '/board/:boardType/:no/:offset/:page',
    name: 'board',
    beforeEnter: onlyAuthUser,
    component: Board,
  },
  {
    path: '/boardreg/:boardType/:offset/:page',
    name: 'boardReg',
    beforeEnter: onlyAuthUser,
    component: BoardReg,
  },
  {
    path: '/notice',
    name: 'notice',
    component: Notice,
  },
  {
    path: '/qna',
    name: 'qna',
    component: QnA,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  duplicateNavigationPolicy: 'ignore',
});

export default router;
