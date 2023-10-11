import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/login.vue')
  },
  {
    path: '/home',
    name: '/home',
    component: () => import("../views/home.vue")
  },
  {
    path: "/error",
    name: "error",
    component: () =>
        import(/* webpackChunkName: "about" */ "../views/ErrorView.vue"),
  },
  {path:'*',redirect: '/error'},
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  if (to.path === "/" || to.path === "/login") {//白名单页面放行
    next();
  } else {//判断有无token
    let token = localStorage.getItem("token");
    if (token) {
          next();
      } else {//无token，该去登录啦
          next("/");
      }

  }});

export default router
