import { createApp } from 'vue'
import App from './App.vue'
import './assets/main.css'
import router from "@/router";
import axios from "axios";
import VueAxios from "vue-axios";

const app = createApp(App)
app.use(router);
app.use(VueAxios, axios);
app.mount('#app')
