
import Axios from "./axios"
const Plugins = {}

Plugins.install = function(Vue) {
  Vue.use(Axios);
};


export default Plugins
