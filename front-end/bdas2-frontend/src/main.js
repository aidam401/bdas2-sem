import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/main.css';

import { createApp } from 'vue';
import App from './App.vue';

import axios from "axios";
import VueAxios from "vue-axios";

import router from "@/router";
import store from '@/store';

const app = createApp(App);
app.use(VueAxios, axios);
app.use(router);
app.use(store);

app.mount('#app');
