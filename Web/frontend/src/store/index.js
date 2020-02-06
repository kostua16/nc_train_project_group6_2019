import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    BOOKS: [],
    BOOKS_MAX_PAGE: 1,
    BOOK: null,
    STATUS_DELETE_BOOK: true,
    API_BOOKS: [],
    API_PROXY_BOOKS: [],
    BASE_API_URL: '/api/',
    BOOKS_API_URL: '/api/proxy_books/',
  },
  getters: {

  },
  mutations: {
    ADD_BOOK: (state, payload) => {
      state.BOOKS.push(payload);
    },
    SET_BOOKS_MAX_PAGE: (state, payload) => {
      Vue.set(state, 'BOOKS_MAX_PAGE', payload);
    },
    SET_BOOKS: (state, payload) => {
      Vue.set(state, 'BOOKS', payload);
    },
    SET_API_BOOKS: (state, payload) => {
      Vue.set(state, 'API_BOOKS', payload);
    },
    SET_API_PROXY_BOOKS: (state, payload) => {
      Vue.set(state, 'API_PROXY_BOOKS', payload);
    },
    SET_BOOK: (state, payload) => {
      Vue.set(state, 'BOOK', payload);
      if (payload.id) {
        Vue.set(state, 'STATUS_DELETE_BOOK', true);
      } else {
        Vue.set(state, 'STATUS_DELETE_BOOK', false);
      }
    },
    DELETE_BOOK: (state, payload) => {
      Vue.set(state, 'STATUS_DELETE_BOOK', false);
      Vue.set(state, 'BOOK', null);
    },
    MUTATE_BOOK: (state, payload) => {
      if (payload !== null
          && Object.getOwnPropertyDescriptor(payload, 'id')
          && Object.getOwnPropertyDescriptor(payload, 'name')
          && Object.getOwnPropertyDescriptor(payload, 'isbn')
      ) {
        const { id } = payload;
        if (id !== null) {
          const bookIndex = state.BOOKS.findIndex(book => book.id === id);
          if (bookIndex !== null) {
            Vue.set(state.BOOKS, bookIndex, payload);
          }
        }
      }
    },
  },
  actions: {
    ADD_BOOK: (context, payload) => {
      if (Object.getOwnPropertyDescriptor(payload, 'name')
        && Object.getOwnPropertyDescriptor(payload, 'isbn')) {
        const { name, isbn } = payload;
        if (name !== null && isbn !== null && name !== '' && isbn !== '') {
          Vue.axios.post(`${context.state.BOOKS_API_URL}`, payload)
            .then((response) => {
              if (response.status === 200) {
                context.commit('ADD_BOOK', response.data);
              }
            }).catch((error) => {
              console.log(error);
            });
        }
      }
    },
    MODIFY_BOOK: (context, payload) => {
      if (
        Object.getOwnPropertyDescriptor(payload, 'id')
        && Object.getOwnPropertyDescriptor(payload, 'property')
        && Object.getOwnPropertyDescriptor(payload, 'value')) {
        const { id, property, value } = payload;
        const data = context.state.BOOKS.find(book => book.id === id);

        if (property === 'name') {
          data.name = value;
        } else {
          data.isbn = value;
        }

        Vue.axios.post(`${context.state.BOOKS_API_URL}/byId/${id}`, data)
          .then((response) => {
            if (response.status === 200) {
              context.commit('MUTATE_BOOK', response.data);
            }
          }).catch((error) => {
            console.log(error);
          });
      }
    },
    GET_BOOKS: async (context, viewProp) => {
      if(develp == true){
        context.commit('SET_BOOKS', testAcc);
      }else
      Vue.axios.get(`${context.state.BOOKS_API_URL}?size=${viewProp.size}&page=${viewProp.page}`).then((responce) => {
        if (context != null) {
          if (responce.status === 200) {
            context.commit('SET_BOOKS', responce.data.results);
            context.commit('SET_BOOKS_MAX_PAGE', responce.data.countPages);
          }
        }
      }).catch((error) => {
        console.log(error);
      });
    },
    GET_BOOK: async (context, id) => {
      Vue.axios.get(`${context.state.BOOKS_API_URL}/byId/${id}`).then((responce) => {
        if (context != null) {
          if (responce.status === 200) {
            context.commit('SET_BOOK', responce.data);
          }
        }
      }).catch((error) => {
        console.log(error);
      });
    },
    DELETE_BOOK: async (context, id) => {
      Vue.axios.delete(`${context.state.BOOKS_API_URL}/byId/${id}`).then((responce) => {
        if (context != null) {
          if (responce.status === 200) {
            context.commit('DELETE_BOOK', responce.data);
          }
        }
      }).catch((error) => {
        console.log(error);
      });
    },
    GET_API_BOOKS: async (context, id) => {
      Vue.axios.get(`${context.state.BASE_API_URL}/endPointsBooks`).then((responce) => {
        if (context != null) {
          if (responce.status === 200) {
            const compare = function (a, b) {
              if (a.url[0] < b.url[0]) {
                return -1;
              }
              if (a.url[0] > b.url[0]) {
                return 1;
              }
              return 0;
            };
            context.commit('SET_API_BOOKS', responce.data.sort(compare));
          }
        }
      }).catch((error) => {
        console.log(error);
      });
    },
    GET_API_PROXY_BOOKS: async (context, id) => {
      Vue.axios.get(`${context.state.BASE_API_URL}/endPointsProxyBooks`).then((responce) => {
        if (context != null) {
          if (responce.status === 200) {
            const compare = function (a, b) {
              if (a.url[0] < b.url[0]) {
                return -1;
              }
              if (a.url[0] > b.url[0]) {
                return 1;
              }
              return 0;
            };
            context.commit('SET_API_PROXY_BOOKS', responce.data.sort(compare));
          }
        }
      }).catch((error) => {
        console.log(error);
      });
    },
  },
  modules: {
  },
});
