import Vue from 'vue'
import Vuex from 'vuex'


Vue.config.devtools = true

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    INITIALIZATION_COMPLETED: false,
    INITIALIZATION_IN_PROGRESS: false,
    CURRENT_USER: null,
    ACTIONS_HISTORY: [],
    IMITATOR_STATE: false,
    STUB_MODE: false,
    LIST_ACCOUNTS: [],
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
    DEFAULT_USER_NAME: "",
    DEFAULT_USER_PASS: "",
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
    SET_IMITATOR_STATE: (state, payload) => {
      Vue.set(state, 'IMITATOR_STATE', payload);
    },
    SET_ACCOUNTS: (state, payload) => {
      Vue.set(state, 'LIST_ACCOUNTS', payload);
    },
    SET_STUB_MODE: (state, payload) => {
      Vue.set(state, 'STUB_MODE', payload);
    },
    SET_INITIALIZATION_IN_PROGRESS: (state, payload) => {
      Vue.set(state, 'INITIALIZATION_IN_PROGRESS', payload);
    },
    SET_INITIALIZATION_COMPLETED: (state, payload) => {
      Vue.set(state, 'INITIALIZATION_COMPLETED', payload);
    },
    SET_CURRENT_USER: (state, payload) => {
      Vue.set(state, 'CURRENT_USER', payload);
    },
    SET_ACTIONS_HISTORY: (state, payload) => {
      Vue.set(state, 'ACTIONS_HISTORY', payload);
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
    SLEEP: (context, ms) => {
      return new Promise(resolve => setTimeout(resolve, ms));
    },
    SEARCH_ACCOUNTS: async (context, query) => {
      if(!context.state.STUB_MODE){
        try {
          const searchResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/searchA/?query=${query}&token=${context.state.TOKEN}`);
          await context.commit("SET_ACCOUNTS", searchResponse.data);
          console.log(`SEARCH_ACCOUNTS.Done(${query}, ${searchResponse.data})`);
        } catch (e) {
          console.log(`SEARCH_ACCOUNTS.Fail(${e})`);
          throw new Error(`SEARCH_ACCOUNTS.Fail(${e})`);
        }
      }
    },
    SYNC_IMITATOR: async (context) => {
      if(context.state.STUB_MODE){
        await context.commit("SET_IMITATOR_STATE", false);
      } else {
        try {
          const stateResponse = await Vue.axios.get(`${context.state.PROXY_URL}/imitator/status`);
          await context.commit("SET_IMITATOR_STATE", stateResponse.data);
        } catch (e) {
          await context.commit("SET_IMITATOR_STATE", false);
        }
      }
    },
    START_IMITATOR: async (context) => {
      if(!context.state.STUB_MODE){
        try {
          await Vue.axios.get(`${context.state.PROXY_URL}/imitator/start`);
          console.log(`START_IMITATOR.Done()`);
        } catch (e) {
          console.log(`START_IMITATOR.Fail(${e})`);
          throw new Error(`START_IMITATOR.Fail(${e})`);
        }
      }
      await context.dispatch("SYNC_IMITATOR");
    },
    STOP_IMITATOR: async (context) => {
      if(!context.state.STUB_MODE){
        try {
          await Vue.axios.get(`${context.state.PROXY_URL}/imitator/stop`);
          console.log(`STOP_IMITATOR.Done()`);
        } catch (e) {
          console.log(`STOP_IMITATOR.Fail(${e})`);
          throw new Error(`STOP_IMITATOR.Fail(${e})`);
        }
      }
      await context.dispatch("SYNC_IMITATOR");
    },
    RUN_REGISTRATION_IMITATION: async (context) => {
      if(!context.state.STUB_MODE){
        try {
          await Vue.axios.get(`${context.state.PROXY_URL}/imitator/oneGeneration`);
          console.log(`RUN_REGISTRATION_IMITATION.Done()`);
        } catch (e) {
          console.log(`RUN_REGISTRATION_IMITATION.Fail(${e})`);
          throw new Error(`RUN_REGISTRATION_IMITATION.Fail(${e})`);
        }
      }
      await context.dispatch("SYNC_IMITATOR");
    },
    RUN_USAGE_IMITATION: async (context) => {
      if(!context.state.STUB_MODE){
        try {
          await Vue.axios.get(`${context.state.PROXY_URL}/imitator/oneUsage`);
          console.log(`RUN_USAGE_IMITATION.Done()`);
        } catch (e) {
          console.log(`RUN_USAGE_IMITATION.Fail(${e})`);
          throw new Error(`RUN_USAGE_IMITATION.Fail(${e})`);
        }
      }
      await context.dispatch("SYNC_IMITATOR");
    },
    INITIALIZE: async (context) => {
      try {
        context.commit("SET_INITIALIZATION_IN_PROGRESS", true);
        try {
          const serverDetailsResponse = await Vue.axios.get(`/serversDetails`);
          await context.commit('SET_VALIDATOR_URL', serverDetailsResponse.data.validator);
          await context.commit('SET_PROXY_URL', serverDetailsResponse.data.proxy);
          await context.commit('SET_STUB_MODE', false);
          console.log("switch to production mode");
        } catch(e) {
          console.log(e);
          await context.commit('SET_VALIDATOR_URL', 'http://localhost:8101');
          await context.commit('SET_PROXY_URL', 'http://localhost:8102');
          await context.commit('SET_STUB_MODE', true);
        }
        await context.dispatch("SYNC_CURRENT_USER");
        await context.dispatch("SYNC_IMITATOR");
        context.commit("SET_INITIALIZATION_IN_PROGRESS", false);
        context.commit("SET_INITIALIZATION_COMPLETED", true);
      } catch (e) {
        console.log(e);
        context.commit("SET_INITIALIZATION_IN_PROGRESS", false);
      }

    },
    WAIT_INITIALIZATION: async (context) => {
      if(context.state.INITIALIZATION_COMPLETED === false){
        if(context.state.INITIALIZATION_IN_PROGRESS === true){
          while (context.state.INITIALIZATION_IN_PROGRESS === true){
            await context.dispatch("SLEEP", 100);
          }
        } else {
          await context.dispatch("INITIALIZE");
          await context.dispatch("WAIT_INITIALIZATION");
        }
      }
    },

    SYNC_CURRENT_USER: async (context) => {
      let currentToken = localStorage.getItem("TOKEN");
      console.log(`SYNC_CURRENT_USER.Started(${currentToken})`);
      if(currentToken!=null){
        try {
          const user = await context.dispatch("LOAD_USER", currentToken);
          await context.commit('SET_TOKEN', currentToken);
          await context.commit("SET_CURRENT_USER", user);
          console.log(`SYNC_CURRENT_USER.Done(${currentToken}, ${JSON.stringify(user)})`);
        } catch (e) {
          console.log(`SYNC_CURRENT_USER.Fail(${currentToken}})`);
          await context.dispatch("LOGOUT");
        }
      }
    },

    LOGIN: async (context, data) => {

      console.log(`LOGIN.Start(${JSON.stringify(data)}, ${context.state.INITIALIZATION_COMPLETED})`);
      await context.dispatch("WAIT_INITIALIZATION");
      try {
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
        await localStorage.setItem("TOKEN", currentToken);
        console.log(`LOGIN.Done(${currentToken}, ${JSON.stringify(user)})`);
      } catch (e) {
        var message = `Login.Fail(${JSON.stringify(data)}, ${JSON.stringify(e)}, ${e})`;
        console.log(message);
        throw new Error(message);
      }
    },

    LOAD_USER: async (context, currentToken) => {
      let userDetails = null;

      if (context.state.STUB_MODE) {
        userDetails = context.state.STUB_USERS.find(user => user.name === currentToken);
        if(userDetails==null){
          throw new Error("User not found by token:"+ currentToken)
        }
        userDetails.availableTariffs = context.state.STUB_PRICE_PLANS;
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

      }

      return userDetails;

    },
    LOAD_HISTORY: async (context) => {
      if(context.state.STUB_MODE) {
        await context.commit("SET_ACTIONS_HISTORY", []);
      } else {
        try{
          if(context.state.TOKEN!=null){
            const historyResponse = await Vue.axios.get(`${context.state.VALIDATOR_URL}/showHistory/?token=${context.state.TOKEN}`);
            if(historyResponse==null || historyResponse.data==null){
              await context.commit("SET_ACTIONS_HISTORY", []);
            } else {
              await context.commit("SET_ACTIONS_HISTORY", historyResponse.data);
            }
          } else {
            await context.commit("SET_ACTIONS_HISTORY", []);
          }

        } catch (e) {
          await context.commit("SET_ACTIONS_HISTORY", []);
        }
      }
    },
    LOGOUT: async (context) => {
      await context.commit('SET_TOKEN', null);
      await context.commit('SET_CURRENT_USER', null);
      await localStorage.removeItem("TOKEN");
      console.log(`LOGOUT.Done()`);
    },

    UPDATE_USER_BALANCE: async (context, data) => {
      if (context.state.STUB_MODE) {
        const user = {
          ...context.state.CURRENT_USER,
          balance: data.amount
        }
        await context.commit('SET_CURRENT_USER', user);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/topup/?token=${context.state.TOKEN}&amount=${data.amount}&telephone=${data.phone}`);
        await context.dispatch("SYNC_CURRENT_USER");
      }
    },
    UPDATE_USER_PRICE_PLAN: async (context, tariff) => {
      if (context.state.STUB_MODE) {
        const user = {
          ...context.state.CURRENT_USER,
          tariff: tariff
        }
        await context.commit('SET_CURRENT_USER', user);
      } else {
        await Vue.axios.get(`${context.state.VALIDATOR_URL}/choicetariff/?token=${context.state.TOKEN}&tariff=${tariff}`);
        await context.dispatch("SYNC_CURRENT_USER");
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
        const response = await Vue.axios.get(`${context.state.PROXY_URL}/callFromTo/?telephoneFrom=${info.telephoneFrom}&minutes=1&telephoneTo=${info.telephoneTo}`);
        if("true" !== response.data){
          throw new Error("User unable to make call");
        }
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
        const response = await Vue.axios.get(`${context.state.PROXY_URL}/smsFromTo/?telephoneFrom=${info.telephoneFrom}&sms=1&telephoneTo=${info.telephoneTo}`);
        if("true" !== response.data){
          throw new Error("User unable to send sms");
        }
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
        const response = await Vue.axios.get(`${context.state.PROXY_URL}/useInternet/?telephoneFrom=${info.telephoneFrom}&kilobytes=1000&telephoneTo=${info.telephoneTo}`);
        if("true" !== response.data){
          throw new Error("User unable to establish internet connection");
        }
      }
      if(info.telephoneFrom === context.state.CURRENT_USER.telephone){
        await context.dispatch("SYNC_CURRENT_USER");
      }
    },
  },
  modules: {}
})
