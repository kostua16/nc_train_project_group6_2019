import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [

  {
    path: '',
    name: 'swith',
    meta: {layout: 'Main'},
    component: () => import('../views/Swith.vue') //главная страница
  },
  {
    path: '/ssp/login', //для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'sspLogin', //для себя имя
    meta: {layout: 'Main'}, //задаём лаяуты //свойства имя, имя
    component: () => import('../views/ssp/SspLogin') //импортируем контент
  },
  {
    path: '/crm/login', //для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'crmLogin', //для себя имя
    meta: {layout: 'Main'}, //задаём лаяуты //свойства имя, имя
    component: () => import('../views/crm/CrmLogin') //импортируем контент
  },
  {
    path: '/crm/tariffadmin',
    name: 'tariffadmin',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/crm/TariffAdmin.vue')
  },
  {
    path: '/crm',
    name: 'crmUser',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/crm/User.vue')
  },
  {
    path: '/crm/history',
    name: 'crmHistory',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/crm/History.vue')
  },
  {
    path: '/ssp',
    name: 'sspHome',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/ssp/Home.vue')
  },
  {
    path: '/ssp/bank',
    name: 'sspBank',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/ssp/Bank.vue')
  },
  {
    path: '/ssp/tariff',
    name: 'sspTariff',
    meta: {layout: 'Main', auth: true},
    component: () => import('../views/ssp/Tariff.vue')
  },
  {
    path: '/atc',
    name: 'atcMobile',
    meta: {layout: 'Main'},
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
