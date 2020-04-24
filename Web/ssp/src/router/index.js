import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/ssp/Home.vue'

Vue.use(VueRouter)

const routes = [

  {
    path: '',
    name: 'swith',
    meta: {layout: 'Empty'},
    component: () => import('../views/Swith.vue') //главная страница
  },
  {
    path: '/ssp/login', //для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'login', //для себя имя
    meta: {layout: 'Empty'}, //задаём лаяуты //свойства имя, имя
    component: () => import('../views/ssp/SspLogin') //импортируем контент
  },
  {
    path: '/crm/login', //для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'login', //для себя имя
    meta: {layout: 'Empty'}, //задаём лаяуты //свойства имя, имя
    component: () => import('../views/crm/CrmLogin') //импортируем контент
  },
  {
    path: '/crm',
    name: 'crm',
    meta: {layout: 'Crm', auth: true},
    component: () => import('../views/crm/CRM.vue')
  },
  {
    path: '/crm/tariffadmin',
    name: 'tariffadmin',
    meta: {layout: 'Crm', auth: true},
    component: () => import('../views/crm/TariffAdmin.vue')
  },
  {
    path: '/crm/user',
    name: 'user',
    meta: {layout: 'Crm', auth: true},
    component: () => import('../views/crm/User.vue')
  },
  {
    path: '/crm/history',
    name: 'history',
    meta: {layout: 'Crm', auth: true},
    component: () => import('../views/crm/History.vue')
  },
  {
    path: '/ssp',
    name: 'ssp',
    meta: {layout: 'Ssp', auth: true},
    component: () => import('../views/ssp/SSP.vue')
  },
  {
    path: '/ssp/home',
    name: 'home',
    meta: {layout: 'Ssp', auth: true},
    component: () => import('../views/ssp/Home.vue')
  },
  {
    path: '/ssp/bank',
    name: 'bank',
    meta: {layout: 'Ssp', auth: true},
    component: () => import('../views/ssp/Bank.vue')
  },
  {
    path: '/ssp/tariff',
    name: 'tariff',
    meta: {layout: 'Ssp', auth: true},
    component: () => import('../views/ssp/Tariff.vue')
  },
  {
    path: '/atc',
    name: 'atc',
    meta: {layout: 'Atc'},
    component: () => import('../views/atc/ATC.vue')
  },
  {
    path: '/atc/mobile',
    name: 'mobile',
    meta: {layout: 'Atc'},
    component: () => import('../views/atc/Mobile.vue')
  },
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
//       next('swith')
//     } else {
//       next()
//     }
// })


export default router
