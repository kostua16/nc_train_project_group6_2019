import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    STUB_MODE: false,
    STUB_USERS: [
      {
        name: "stub@stub.com",
        pass: "stub123",
        balance: 10,
        minutes: 10,
        tariff: "STUB",
        sms: 10,
        internet: 10,
        telephone: 1111111111,
      }
    ],
    STUB_PRICE_PLANS: [
      {
        name: "STUB",
        internet: 10 * 1000,
        call: 10 * 60,
        sms: 10,
      }
    ],
    DEFAULT_USER_NAME: "stub@stub.com",
    DEFAULT_USER_PASS: "stub123",
    VALIDATOR_URL: 'http://localhost:8101',
    PROXY_URL: 'http://localhost:8102',
    TOKEN: "",
    USER_NAME: "Not logged in",
    USER_LOADED: false,
    USER_PRICE_PLAN: '',
    USER_BALANCE: 0,
    USER_MINUTES: 0,
    USER_SMS: 0,
    USER_INTERNET: 0,
    USER_PHONE_NUM: "",
    USER_HISTORY: [],
    LOGIN_SUCCESS: false,
    LOGIN_FAILED: false,
    AVAILABLE_PRICE_PLANS: [],
    ALL_PRICE_PLANS: [],
    DELETED_USER: '',
    CREATED_USER: '',
    ATC_SUCCESS: true,
    LAST_PATH: null,
    SIDEBAR_LINKS: [],
    NAVBAR_NAME: '',
    ACTIVE_MODULES: [
      {
        name: 'ATC',
        url: '/atc',
        links: [
          {title: 'Mobile', url: '/atc'}
        ]
      },
      {
        name: 'CRM',
        url: '/crm',
        links: [
          {title: 'Пользователи', url: '/crm'},
          {title: 'Тарифы', url: '/crm/tariffadmin'},
          {title: 'История', url: '/crm/history'}
        ]
      },
      {
        name: 'SSP',
        url: '/ssp',
        links: [
          {title: 'Счет', url: '/ssp', exact: true},
          {title: 'Пополнить счет', url: '/ssp/bank'},
          {title: 'Изменить тариф', url: '/ssp/tariff'},
        ]
      }
    ]
  },
  mutations: {
    SET_STUB_MODE: (state, payload) => {
      Vue.set(state, 'STUB_MODE', payload);
    },
    SET_LAST_PATH: (state, payload) => {
      Vue.set(state, 'LAST_PATH', payload);
    },
    SET_SIDEBAR_LINKS: (state, payload) => {
      Vue.set(state, 'SIDEBAR_LINKS', payload);
    },
    SET_NAVBAR_NAME: (state, payload) => {
      Vue.set(state, 'NAVBAR_NAME', payload);
    },
    SET_DEFAULT_USER_NAME: (state, payload) => {
      Vue.set(state, 'DEFAULT_USER_NAME', payload);
    },
    SET_DEFAULT_USER_PASS: (state, payload) => {
      Vue.set(state, 'DEFAULT_USER_PASS', payload);
    },
    SET_VALIDATOR_URL: (state, payload) => {
      Vue.set(state, 'VALIDATOR_URL', payload);
    },
    SET_PROXY_URL: (state, payload) => {
      Vue.set(state, 'PROXY_URL', payload);
    },
    SET_TOKEN: (state, payload) => {
      Vue.set(state, 'TOKEN', payload);
    },
    SET_USER_NAME: (state, payload) => {
      Vue.set(state, 'USER_NAME', payload);
    },
    SET_LOGIN_FAIL: (state, payload) => {
      Vue.set(state, 'LOGIN_FAILED', payload);
    },
    SET_LOGIN_SUCCESS: (state, payload) => {
      Vue.set(state, 'LOGIN_SUCCESS', payload);
    },
    SET_USER_LOADED: (state, payload) => {
      Vue.set(state, 'USER_LOADED', payload);
    },
    SET_USER_BALANCE: (state, payload) => {
      Vue.set(state, 'USER_BALANCE', payload);
    },
    SET_USER_MINUTES: (state, payload) => {
      Vue.set(state, 'USER_MINUTES', payload);
    },
    SET_USER_SMS: (state, payload) => {
      Vue.set(state, 'USER_SMS', payload);
    },
    SET_USER_INTERNET: (state, payload) => {
      Vue.set(state, 'USER_INTERNET', payload);
    },
    SET_USER_PHONE: (state, payload) => {
      Vue.set(state, 'USER_PHONE_NUM', payload);
    },
    SET_USER_PRICE_PLAN: (state, payload) => {
      Vue.set(state, 'USER_PRICE_PLAN', payload);
    },
    SET_AVAILABLE_PRICE_PLANS: (state, payload) => {
      Vue.set(state, 'AVAILABLE_PRICE_PLANS', payload);
    },
    SET_ALL_PRICE_PLANS: (state, payload) => {
      Vue.set(state, 'ALL_PRICE_PLANS', payload);
    },
    SET_STUB_PRICE_PLANS: (state, payload) => {
      Vue.set(state, 'STUB_PRICE_PLANS', payload);
    },
    SET_USER_HISTORY: (state, payload) => {
      Vue.set(state, 'USER_HISTORY', payload);
    },
    SET_DELETED_USER: (state, payload) => {
      Vue.set(state, 'DELETED_USER', payload);
    },
    SET_CREATED_USER: (state, payload) => {
      Vue.set(state, 'CREATED_USER', payload);
    },
    SET_STUB_USERS: (state, payload) => {
      Vue.set(state, 'STUB_USERS', payload);
    },
    SET_ATC_SUCCESS: (state, payload) => {
      Vue.set(state, 'ATC_SUCCESS', payload);
    },
  },
  actions: {
    UPDATE_NAVIGATION: async (context, data) => {
      context.commit('SET_NAVBAR_NAME', data.name);
      context.commit('SET_SIDEBAR_LINKS', data.links);
    },
    STORE_LAST_PATH: async (context, lastPath) => {
      context.commit('SET_LAST_PATH', lastPath);
    },
    LOAD_ALL_PRICE_PLANS: async (context) => {
      if (context.state.STUB_MODE) {
        context.commit('SET_ALL_PRICE_PLANS', context.state.STUB_PRICE_PLANS);
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/showT/?token=${context.state.TOKEN}`).then(response => {
          context.commit('SET_ALL_PRICE_PLANS', response.data);
        }).catch(e => {
          console.log(e);
        });
      }
    },

    LOAD_SERVERS_DETAILS: async (context) => {
      Vue.axios.get(`/serversDetails`)
        .then((response) => {
          context.commit('SET_VALIDATOR_URL', response.data.validator);
          context.commit('SET_PROXY_URL', response.data.proxy);
          console.log("switch to production mode");
          context.dispatch("CREATE_USERS");
        }).catch(() => {
        context.commit('SET_VALIDATOR_URL', 'http://localhost:8101');
        context.commit('SET_PROXY_URL', 'http://localhost:8102');
        context.commit('SET_STUB_MODE', true);
        context.commit('SET_DEFAULT_USER_NAME', 'stub@stub.com');
        context.commit('SET_DEFAULT_USER_PASS', 'stub123');
        console.log("switch to stub mode");
      });
    },
    LOGIN: async (context, data) => {
      const setup = (token, error) => {
        if (error != null) {
          console.log("login failed:" + error);
          context.commit('SET_LOGIN_FAIL', true);
          context.commit('SET_LOGIN_SUCCESS', false);
          throw new Error("Login fail:" + error);
        } else {
          console.log("login success");
          context.commit('SET_TOKEN', token);
          context.commit('SET_LOGIN_FAIL', false);
          context.commit('SET_LOGIN_SUCCESS', true);
          context.dispatch("LOAD_USER_DETAILS");
        }
      };

      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.name === data.login && user.pass === data.password);
        if (user != null) {
          setup(user.name, null);
        } else {
          setup(null, "Cant find user");
        }
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/login/?login=${data.login}&password=${data.password}`)
          .then((response) => {
            setup(response, null);
          }).catch((error) => {
            console.log(error);
            setup(null, error);
        });
      }
    },
    LOGOUT: async (context) => {
      context.commit('SET_TOKEN', null);
      context.commit('SET_LOGIN_FAIL', false);
      context.commit('SET_LOGIN_SUCCESS', false);
      context.commit('SET_USER_LOADED', false);
      context.commit('SET_USER_NAME', 'Not Logged in');
      context.commit('SET_USER_BALANCE', 0);
      context.commit('SET_USER_MINUTES', 0);
      context.commit('SET_USER_SMS', 0);
      context.commit('SET_USER_INTERNET', 0);
      context.commit('SET_USER_PHONE', 0);
      context.commit('SET_USER_PRICE_PLAN', '');
    },

    LOAD_USER_DETAILS: async (context) => {
      const setup = (data, error) => {
        if (error != null) {
          console.log(error);
          context.commit('SET_USER_LOADED', false);
          throw new Error("Load User Fail:" + error);
        } else {
          context.commit('SET_USER_NAME', data.name);
          context.commit('SET_USER_BALANCE', data.balance);
          context.commit('SET_USER_MINUTES', data.minutes);
          context.commit('SET_USER_SMS', data.sms);
          context.commit('SET_USER_INTERNET', data.internet);
          context.commit('SET_USER_PHONE', data.telephone);
          context.commit('SET_USER_LOADED', true);
          context.dispatch('LOAD_AVAILABLE_PRICE_PLANS');
          context.dispatch('LOAD_ALL_PRICE_PLANS');
          context.dispatch('LOAD_HISTORY');
          context.commit('SET_USER_HISTORY', []);
        }
      };
      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.name === context.state.TOKEN);
        if (user != null) {
          setup(user, null);
        } else {
          setup(null, "User not found");
        }
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/home/?token=${context.state.TOKEN}`).then(response => {
          setup(response.data, null);
        }).catch(e => {
          setup(null, e);
        });
      }
    },
    LOAD_AVAILABLE_PRICE_PLANS: async (context) => {
      const setup = (data, error) => {
        if (error != null) {
          console.log(error);
          context.commit('SET_USER_PRICE_PLAN', '');
          throw new Error("Load available price plans Fail:" + error);
        } else {
          context.commit('SET_AVAILABLE_PRICE_PLANS', data.tariffs);
          context.commit('SET_USER_PRICE_PLAN', data.user);
        }
      };
      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.name === context.state.TOKEN);
        if (user != null) {
          setup({tariffs: context.state.STUB_PRICE_PLANS, user: user.tariff}, null);
        } else {
          setup(null, "User not found");
        }
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/showtariff/?token=${context.state.TOKEN}`).then(response => {
          setup(response.data, null);
        }).catch(e => {
          setup(null, e);
        });
      }
    },

    LOAD_HISTORY: async (context) => {
      const setup = (data, error) => {
        if (error != null) {
          console.log(error);
          throw new Error("Load history Fail:" + error);
        } else {
          context.commit('SET_USER_HISTORY', data);
        }
      };
      if (context.state.STUB_MODE) {
        setup([], null);
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/showHistory/?token=${context.state.TOKEN}`).then(response => {
          setup(response.data, null);
        }).catch(e => {
          setup(null, e);
        });
      }
    },

    UPDATE_USER_BALANCE: async (context, data) => {
      if (context.state.STUB_MODE) {
        context.commit('SET_USER_BALANCE', money);
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/home/?token=${context.state.TOKEN}&amount=${data.amount}&telephone=${data.phone}`).then(() => {
          context.commit('SET_USER_BALANCE', money);
        }).catch(e => {
          console.log(e);
          throw new Error("Update user balance Fail:" + e);
        });
      }
    },
    UPDATE_USER_PRICE_PLAN: async (context, tariff) => {
      if (context.state.STUB_MODE) {
        context.commit('SET_USER_PRICE_PLAN', tariff);
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/choicetariff/?token=${context.state.TOKEN}&tariff=${tariff}`).then(() => {
          context.commit('SET_USER_PRICE_PLAN', tariff);
        }).catch(e => {
          console.log(e);
          throw new Error("Update user price plan Fail:" + e);
        });
      }
    },
    CREATE_USERS: async (context) => {
      if (!context.state.STUB_MODE) {
        Vue.axios.post(`${context.state.PROXY_URL}/start`).then(() => {
          console.log("SSP user created");
        }).catch(e => {
          console.log(e);
          throw new Error("Create users Fail:" + e);
        });
      }
    },

    DELETE_PRICE_PLAN: async (context, tariff) => {
      if (context.state.STUB_MODE) {
        var pricePlans = context.state.STUB_PRICE_PLANS;
        const idx = pricePlans.findIndex(plan => plan.name === tariff);
        pricePlans.splice(idx, 1);
        context.commit('SET_STUB_PRICE_PLANS', pricePlans);
        await context.dispatch('LOAD_AVAILABLE_PRICE_PLANS');
        await context.dispatch('LOAD_ALL_PRICE_PLANS');
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/deleteT/?token=${context.state.TOKEN}&name=${tariff}`).then(() => {
          context.dispatch('LOAD_AVAILABLE_PRICE_PLANS');
          context.dispatch('LOAD_ALL_PRICE_PLANS');
        }).catch(e => {
          console.log(e);
          throw new Error("Delete price plan Fail:" + e);
        });
      }
    },
    CREATE_PRICE_PLAN: async (context, tariffData) => {
      if (context.state.STUB_MODE) {
        var pricePlans = context.state.STUB_PRICE_PLANS;
        pricePlans.push(tariffData);
        context.commit('SET_STUB_PRICE_PLANS', pricePlans);
        await context.dispatch('LOAD_AVAILABLE_PRICE_PLANS');
        await context.dispatch('LOAD_ALL_PRICE_PLANS');
      } else {
        Vue.axios.post(`${context.state.VALIDATOR_URL}/createT/?token=${context.state.TOKEN}`, tariffData).then(() => {
          context.dispatch('LOAD_AVAILABLE_PRICE_PLANS');
          context.dispatch('LOAD_ALL_PRICE_PLANS');
        }).catch(e => {
          console.log(tariffData);
          console.log(e);
          throw new Error("Create price plan Fail:" + e);
        });
      }
    },

    DELETE_USER: async (context, user) => {
      if (context.state.STUB_MODE) {
        var stubUsers = context.state.STUB_USERS;
        const idx = stubUsers.findIndex(user => user.name === user);
        stubUsers.splice(idx, 1);
        context.commit('SET_STUB_USERS', stubUsers);
        context.commit('SET_DELETED_USER', user);
      } else {
        Vue.axios.get(`${context.state.VALIDATOR_URL}/deleteA/?token=${context.state.TOKEN}&login=${user}`).then((responce) => {
          context.commit('SET_DELETED_USER', user);
          console.log(responce.data);
        }).catch(e => {
          console.log(e);
          throw new Error("Delete user Fail:" + e);
        });
      }
    },
    CREATE_USER: async (context, userData) => {
      if (context.state.STUB_MODE) {
        var stubUsers = context.state.STUB_USERS;
        stubUsers.push(userData);
        context.commit('SET_STUB_USERS', stubUsers);
        context.commit('SET_CREATED_USER', userData.name);
      } else {
        Vue.axios.post(`${context.state.VALIDATOR_URL}/createA/?token=${context.state.TOKEN}`, userData).then((responce) => {
          context.commit('SET_CREATED_USER', userData.name);
          console.log(responce.data);
        }).catch(e => {
          console.log(userData);
          console.log(e);
          throw new Error("Create user Fail:" + e);
        });
      }
    },

    DO_CALL: async (context, info) => {
      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.telephone === info.telephoneFrom);
        if (user != null) {
          const pricePlan = context.state.STUB_PRICE_PLANS.find(plan => plan.name === user.tariff);
          if (pricePlan != null) {
            if (user.minutes > 0) {
              user.minutes = user.minutes - 1;
              user.balance = user.balance - tariff.tariffCall.Call_cost * 1;
            } else {
              user.balance = user.balance - tariff.tariffCall.Default_call_cost * 1;
            }
          } else {
            throw new Error("Tariff not found:" + user.tariff);
          }
        } else {
          throw new Error("User not found:" + info.telephoneFrom);
        }
      } else {
        Vue.axios.get(`${context.state.PROXY_URL}/callFromTo/?telephoneFrom=${info.telephoneFrom}&minutes=1&telephoneTo=${info.telephoneTo}`).then((responce) => {
          context.commit('SET_ATC_SUCCESS', true);
          console.log(responce.data);
        }).catch(e => {
          console.log(userData);
          console.log(e);
          context.commit('SET_ATC_SUCCESS', false);
          throw new Error("Call failed:" + e);
        });
      }
    },
    DO_SMS: async (context, info) => {
      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.telephone === info.telephoneFrom);
        if (user != null) {
          const pricePlan = context.state.STUB_PRICE_PLANS.find(plan => plan.name === user.tariff);
          if (pricePlan != null) {
            if (user.sms > 0) {
              user.sms = user.sms - 1;
              user.balance = user.balance - tariff.tariffSms.Sms_cost * 1;
            } else {
              user.balance = user.balance - tariff.tariffSms.Default_sms_cost * 1;
            }
          } else {
            throw new Error("Tariff not found:" + user.tariff);
          }
        } else {
          throw new Error("User not found:" + info.telephoneFrom);
        }
      } else {
        Vue.axios.get(`${context.state.PROXY_URL}/smsFromTo/?telephoneFrom=${info.telephoneFrom}&sms=1&telephoneTo=${info.telephoneTo}`).then((responce) => {
          context.commit('SET_ATC_SUCCESS', true);
          console.log(responce.data);
        }).catch(e => {
          console.log(userData);
          console.log(e);
          context.commit('SET_ATC_SUCCESS', false);
          throw new Error("Call failed:" + e);
        });
      }
    },
    DO_INTERNET: async (context, info) => {
      if (context.state.STUB_MODE) {
        const user = context.state.STUB_USERS.find(user => user.telephone === info.telephoneFrom);
        if (user != null) {
          const pricePlan = context.state.STUB_PRICE_PLANS.find(plan => plan.name === user.tariff);
          if (pricePlan != null) {
            if (user.internet > 0) {
              user.internet = user.internet - 1;
              user.balance = user.balance - tariff.tariffInternet.Internet_cost * 1;
            } else {
              user.balance = user.balance - tariff.tariffInternet.Default_internet_cost * 1;
            }
          } else {
            throw new Error("Tariff not found:" + user.tariff);
          }
        } else {
          throw new Error("User not found:" + info.telephoneFrom);
        }
      } else {
        Vue.axios.get(`${context.state.PROXY_URL}/useInternet/?telephoneFrom=${info.telephoneFrom}&kilobytes=1000&telephoneTo=${info.telephoneTo}`).then((responce) => {
          context.commit('SET_ATC_SUCCESS', true);
          console.log(responce.data);
        }).catch(e => {
          console.log(userData);
          console.log(e);
          context.commit('SET_ATC_SUCCESS', false);
          throw new Error("Call failed:" + e);
        });
      }
    },
  },
  modules: {}
})
