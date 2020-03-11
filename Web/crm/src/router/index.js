import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    meta: { layout: 'Main' },
    component: () => import('../views/Home.vue'), // главная страница
  },
  {
    path: '/login', // для адресной строки //при этом имени <router-view/> на главной странице App.vue будет менять контент
    name: 'login', // для себя имя
    meta: { layout: 'Empty' }, // задаём лаяуты //свойства имя, имя
    component: () => import('../views/Login.vue'), // импортируем контент
  },
  {
    path: '/categories',
    name: 'categories',
    meta: { layout: 'Main' },
    component: () => import('../views/Categories.vue'),
  },
  {
    path: '/register',
    name: 'register',
    meta: { layout: 'Empty' },
    component: () => import('../views/Register.vue'),
  },
  {
    path: '/history',
    name: 'history',
    meta: { layout: 'Main' },
    component: () => import('../views/History.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
