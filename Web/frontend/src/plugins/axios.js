

import Vue from 'vue';
import axios from 'axios';

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const axiosInstance = axios.create(config);

axiosInstance.interceptors.request.use(
  axiosConfig => axiosConfig, // Do something before request is sent
  error => Promise.reject(error), // Do something with request error
);

// Add a response interceptor
axiosInstance.interceptors.response.use(
  axiosConfig => axiosConfig, // Do something before request is sent
  error => Promise.reject(error), // Do something with request error

);

const Plugin = {
  install(VueInstance, _) {
    VueInstance.axios = axiosInstance;
    // VueInstance.axiosOptions = options;
    window.axios = axiosInstance;
    Object.defineProperties(VueInstance.prototype, {
      axios: {
        get() {
          return axiosInstance;
        },
      },
      $axios: {
        get() {
          return axiosInstance;
        },
      },
    });
  },
};

Vue.use(Plugin);

export default Plugin;
