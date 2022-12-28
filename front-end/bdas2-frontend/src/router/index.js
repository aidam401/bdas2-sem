import Home from "../views/Home.vue";
import {createRouter, createWebHistory} from "vue-router";
import Linky from "@/views/entities/Linky.vue";
import Adresy from "@/views/entities/Adresy.vue";
import Zastavky from "@/views/entities/Zastavky.vue";
import Spoje from "@/views/entities/Spoje.vue";
import CasyZastavek from "@/views/entities/CasyZastavek.vue";
import ZaznamyJizdnichRadu from "@/views/entities/ZaznamyJizdnichRadu.vue";
import JizdniRady from "@/views/entities/JizdniRady.vue";
import TypyJizdnichRadu from "@/views/entities/TypyJizdnichRadu.vue";
import AktualniJizdy from "@/views/entities/AktualniJizdy.vue";
import Ridici from "@/views/entities/Ridici.vue";
import Trolejbusy from "@/views/entities/Trolejbusy.vue";
import Autobusy from "@/views/entities/Autobusy.vue";
import Uzivatele from "@/views/entities/Uzivatele.vue";
import UzivatelskeRole from "@/views/entities/UzivatelskeRole.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import store from "@/store";

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
    }, {
        path: "/login",
        name: "Login",
        component: Login
    }, {
        path: "/register",
        name: "Register",
        component: Register
    }, {
        path: "/linka",
        name: "Linka",
        component: Linky
    }, {
        path: "/adresa",
        name: "Adresa",
        component: Adresy
    }, {
        path: "/zastavka",
        name: "Zastavka",
        component: Zastavky
    }, {
        path: "/spoj",
        name: "Spoj",
        component: Spoje
    }, {
        path: "/cas-zastavky",
        name: "Casy_zastavek",
        component: CasyZastavek
    }, {
        path: "/zaznam-jizdniho-radu",
        name: "Zaznam_jizdniho_radu",
        component: ZaznamyJizdnichRadu
    }, {
        path: "/jizdni-rad",
        name: "Jizdni_rad",
        component: JizdniRady
    }, {
        path: "/typ-jizdniho-radu",
        name: "Typ_jizdniho_radu",
        component: TypyJizdnichRadu
    }, {
        path: "/aktualni-jizdy",
        name: "Aktualni_jizda",
        component: AktualniJizdy
    }, {
        path: "/ridic",
        name: "Ridic",
        component: Ridici
    }, {
        path: "/trolejbus",
        name: "Trolejbus",
        component: Trolejbusy
    }, {
        path: "/autobus",
        name: "Autobus",
        component: Autobusy
    }, {
        path: "/uzivatel",
        name: "Uzivatel",
        component: Uzivatele
    }, {
        path: "/uzivatelske-role",
        name: "Uzivatelske_role",
        component: UzivatelskeRole
    },

    // otherwise redirect to home
    {
        path: "/:catchAll(.*)",
        redirect: '/'
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const publicPaths = ['/', '/linka', '/login', '/register'];
    const adminPaths = ['/ridic', '/autobus', '/trolejbus', '/uzivatel'];
    const authRequired = !publicPaths.includes(to.path);
    const adminRequired = adminPaths.includes(to.path);
    const loggedIn = store.getters["auth/isLoggedIn"];
    const isAdmin = store.getters["auth/isAdmin"];

    // pokud vyžaduje přihlašení
    if (authRequired && !loggedIn) {
        return next({
           path: '/login',
           query: { returnUrl: to.path }
        });
    }

    // pokud je stránka adminovská
    if (adminRequired && !isAdmin) {
        return next({
            path: '/'
        });
    }

    next();
})

export default router;