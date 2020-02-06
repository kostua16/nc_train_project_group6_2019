import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/vue',
    name: 'Vue',
    component: () => import('../views/Vue.vue'),
  },
  {
    path: '/api',
    name: 'Api',
    component: () => import('../views/Api.vue'),
  },
  {
    path: '/book/:id',
    name: 'Book',
    component: () => import('../views/Book.vue'),
  },
  {
    path: '/search',
    name: 'Search',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Search.vue'),
  },
  {
    path: '/books',
    name: 'Books',
    component: () => import(/* webpackChunkName: "books" */ '../views/Books.vue'),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
