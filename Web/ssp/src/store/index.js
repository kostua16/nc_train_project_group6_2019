import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    CURRENT_USER: null,
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
    SET_CURRENT_USER: (state, payload) => {
      Vue.set(state, 'CURRENT_USER', payload);
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
    UPDATE_USER: (state, key, value) => {
      if(state.CURRENT_USER!=null){
        Vue.set(state.CURRENT_USER, key, value);
      }
    },
    SET_STUB_PRICE_PLANS: (state, payload) => {
      Vue.set(state, 'STUB_PRICE_PLANS', payload);
    },
    SET_STUB_USERS: (state, payload) => {
      Vue.set(state, 'STUB_USERS', payload);
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

    INITIALIZE: async (context) => {
      try {
        const serverDetailsResponse = await Vue.axios.get(`/serversDetails`);
        await context.commit('SET_VALIDATOR_URL', serverDetailsResponse.data.validator);
        await context.commit('SET_PROXY_URL', serverDetailsResponse.data.proxy);
        await context.commit('SET_DEFAULT_USER_NAME', '');
        await context.commit('SET_DEFAULT_USER_PASS', '');
        console.log("switch to production mode");
        await context.dispatch("CREATE_USERS");
      } catch(e) {
        await context.commit('SET_VALIDATOR_URL', 'http://localhost:8101');
        await context.commit('SET_PROXY_URL', 'http://localhost:8102');
        await context.commit('SET_STUB_MODE', true);
        await context.commit('SET_DEFAULT_USER_NAME', 'stub@stub.com');
        await context.commit('SET_DEFAULT_USER_PASS', 'stub123');
      }
      await context.dispatch("SYNC_CURRENT_USER");
    },

    SYNC_CURRENT_USER: async (context) => {
      let currentToken = localStorage.getItem("TOKEN");
      if(currentToken!=null){
        try {
          const user = await context.dispatch("LOAD_USER", currentToken);
          await context.commit('SET_TOKEN', currentToken);
          await context.commit("SET_CURRENT_USER", user);
        } catch (e) {
          await context.dispatch("LOGOUT");
        }
      }
    },

    LOGIN: async (context, data) => {
      let currentToken = null;
      if(context.state.STUB_MODE){
        const userDetails = context.state.STUB_USERS.find(user => user.name === data.login && user.pass === data.password);
        if(userDetails!=null){
          currentToken = userDetails.name;
        } else {
          throw new Error("User not found:" + data.login);
        }
      } else {
        const loginResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/login/?login=${data.login}&password=${data.password}`);
        if(loginResponse!=null && loginResponse.data!=null){
          currentToken = loginResponse.data;
        } else {
          throw new Error("User not found:" + data.login);
        }
      }
      const user = await context.dispatch("LOAD_USER", currentToken);
      if(user==null){
        throw new Error("User not found:" + data.login + ", token:" + currentToken);
      }
      await context.commit('SET_TOKEN', currentToken);
      await context.commit("SET_CURRENT_USER", user);
    },

    LOAD_USER: async (context, currentToken) => {
      let userDetails = null;

      if (context.state.STUB_MODE) {
        userDetails = context.state.STUB_USERS.find(user => user.name === currentToken);
        if(userDetails==null){
          throw new Error("User not found by token:"+ currentToken)
        }
        userDetails.availableTariffs = context.state.STUB_PRICE_PLANS;
        userDetails.history = [];
      } else {
        const userResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/home/?token=${currentToken}`);
        userDetails = userResponse.data;

        if(userDetails==null){
          throw new Error("User not found by token:"+ currentToken)
        }

        const userTariffsResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/showtariff/?token=${currentToken}`);
        userDetails.tariff = userTariffsResponse.data.user;

        try {
          const allTariffsResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/showT/?token=${currentToken}`);
          userDetails.availableTariffs = allTariffsResponse.data
        } catch (e) {
          userDetails.availableTariffs = userTariffsResponse.data.tariffs;
        }

        try{
          const historyResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/showHistory/?token=${currentToken}`);
          userDetails.history = historyResponse.data;
        } catch (e) {
          userDetails.history = [];
        }

      }

      return userDetails;

    },
    LOGOUT: async (context) => {
      await context.commit('SET_TOKEN', null);
      await context.commit('SET_CURRENT_USER', null);
      await localStorage.removeItem("TOKEN");
    },

    UPDATE_USER_BALANCE: async (context, data) => {
      if (context.state.STUB_MODE) {
        await context.commit('UPDATE_USER', 'balance', data.amount);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/home/?token=${context.state.TOKEN}&amount=${data.amount}&telephone=${data.phone}`);
        await context.commit('UPDATE_USER', 'balance', data.amount);
      }
    },
    UPDATE_USER_PRICE_PLAN: async (context, tariff) => {
      if (context.state.STUB_MODE) {
        await context.commit('UPDATE_USER', 'tariff', tariff);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/choicetariff/?token=${context.state.TOKEN}&tariff=${tariff}`);
        await context.commit('UPDATE_USER', 'tariff', tariff);
      }
    },
    CREATE_USERS: async (context) => {
      if (!context.state.STUB_MODE) {
        await Vue.axios.post(`${context.state.PROXY_URL}/start`);
      }
    },

    DELETE_PRICE_PLAN: async (context, tariff) => {
      if (context.state.STUB_MODE) {
        var pricePlans = context.state.STUB_PRICE_PLANS;
        const idx = pricePlans.findIndex(plan => plan.name === tariff);
        pricePlans.splice(idx, 1);
        await context.commit('SET_STUB_PRICE_PLANS', pricePlans);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/deleteT/?token=${context.state.TOKEN}&name=${tariff}`);
      }
      await context.dispatch("SYNC_CURRENT_USER");
    },
    CREATE_PRICE_PLAN: async (context, tariffData) => {
      if (context.state.STUB_MODE) {
        var pricePlans = context.state.STUB_PRICE_PLANS;
        pricePlans.push(tariffData);
        context.commit('SET_STUB_PRICE_PLANS', pricePlans);
      } else {
        await Vue.axios.post(`${context.state.VALIDATOR_URL}/createT/?token=${context.state.TOKEN}`, tariffData);
      }
      await context.dispatch("SYNC_CURRENT_USER");
    },

    DELETE_USER: async (context, user) => {
      if (context.state.STUB_MODE) {
        var stubUsers = context.state.STUB_USERS;
        const idx = stubUsers.findIndex(user => user.name === user);
        stubUsers.splice(idx, 1);
        context.commit('SET_STUB_USERS', stubUsers);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/deleteA/?token=${context.state.TOKEN}&login=${user}`);
      }
      await context.dispatch("SYNC_CURRENT_USER");
    },
    CREATE_USER: async (context, userData) => {
      if (context.state.STUB_MODE) {
        var stubUsers = context.state.STUB_USERS;
        stubUsers.push(userData);
        context.commit('SET_STUB_USERS', stubUsers);
      } else {
        await Vue.axios.post(`${context.state.VALIDATOR_URL}/createA/?token=${context.state.TOKEN}`, userData);
      }
      await context.dispatch("SYNC_CURRENT_USER");
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
        await Vue.axios.get(`${context.state.PROXY_URL}/callFromTo/?telephoneFrom=${info.telephoneFrom}&minutes=1&telephoneTo=${info.telephoneTo}`);
      }
      if(info.telephoneFrom === context.state.CURRENT_USER.telephone){
        await context.dispatch("SYNC_CURRENT_USER");
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
        await Vue.axios.get(`${context.state.PROXY_URL}/smsFromTo/?telephoneFrom=${info.telephoneFrom}&sms=1&telephoneTo=${info.telephoneTo}`);
      }
      if(info.telephoneFrom === context.state.CURRENT_USER.telephone){
        await context.dispatch("SYNC_CURRENT_USER");
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
        await Vue.axios.get(`${context.state.PROXY_URL}/useInternet/?telephoneFrom=${info.telephoneFrom}&kilobytes=1000&telephoneTo=${info.telephoneTo}`);
      }
      if(info.telephoneFrom === context.state.CURRENT_USER.telephone){
        await context.dispatch("SYNC_CURRENT_USER");
      }
    },
  },
  modules: {}
})
