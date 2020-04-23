import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login', //для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'login', //для себя имя
    meta: {layout: 'Empty'}, //задаём лаяуты //свойства имя, имя
    component: () => import('../views/Login.vue') //импортируем контент
  },
  {
    path: '/register',
    name: 'register',
    meta: {layout: 'Empty'},
    component: () => import('../views/Register.vue')
  },
  {
    path: '/swith',
    name: 'swith',
    meta: {layout: 'Empty'},
    component: () => import('../views/Swith.vue')
  },
  {
    path: '/mobile',
    name: 'mobile',
    meta: {layout: 'Main'},
    component: () => import('../views/atc/Mobile.vue')
  },
  {
    path: '/tariffadmin',
    name: 'tariffadmin',
    meta: {layout: 'Main'},
    component: () => import('../views/crm/TariffAdmin.vue')
  },
  {
    path: '/user',
    name: 'user',
    meta: {layout: 'Main'},
    component: () => import('../views/crm/User.vue')
  },
  {
    path: '/',
    name: 'home',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/Home.vue') //главная страница
  },
  {
    path: '/categories',
    name: 'categories',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/Categories.vue')
  },
  {
    path: '/bank',
    name: 'bank',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/Bank.vue')
  },
  {
    path: '/tariff',
    name: 'tariff',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/Tariff.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/Profile.vue')
  },
  // {
  //   path: '/history',
  //   name: 'history',
  //   meta: {layout: 'Main', auth: true},
  //   component: () => import('../views/History.vue')
  // },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// router.beforeEach((to, from, next) => { //to - куда мы идём // from - откуда мы пришли // next - какую мы должны вызвать функцию что бы продожить выполнеие роутера
// const currentUser = localStorage.getItem('token')
// const requireAuth = to.matched.some(record => record.meta.auth)
//     if ((!currentUser)&&(requireAuth)) {
//       console.log('метод сработал')
//       next('/login')
//     } else {
//       next()
//     }
// })


export default router
