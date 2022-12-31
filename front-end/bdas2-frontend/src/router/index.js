import Home from "../views/Home.vue";
import {createRouter, createWebHistory} from "vue-router";
import Linky from "@/views/entities/Linky.vue";
import Zastavky from "@/views/entities/Zastavky.vue";
import Spoje from "@/views/entities/Spoje.vue";
import CasyZastavek from "@/views/entities/CasyZastavek.vue";
import ZaznamyJizdnichRadu from "@/views/entities/ZaznamyJizdnichRadu.vue";
import JizdniRady from "@/views/entities/JizdniRady.vue";
import AktualniJizdy from "@/views/entities/AktualniJizdy.vue";
import Uzivatele from "@/views/entities/Uzivatele.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import store from "@/store";
import Profile from "@/views/Profile.vue";
import Vozidla from "@/views/entities/Vozidla.vue";
import Zamestnanci from "@/views/entities/Zamestnanci.vue";

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
    },{
        path: "/profile",
        name: "Profile",
        component: Profile
    }, {
        path: "/linka",
        name: "Linka",
        component: Linky
    }, {
        path: "/vozidlo",
        name: "Vozidlo",
        component: Vozidla
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
        path: "/aktualni-jizda",
        name: "Aktualni_jizda",
        component: AktualniJizdy
    }, {
        path: "/zamestnanec",
        name: "Zamestnanec",
        component: Zamestnanci
    }, {
        path: "/uzivatel",
        name: "Uzivatel",
        component: Uzivatele
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
    const publicPaths = ['/', '/spoj', '/login', '/register'];
    const adminPaths = ['/zamestnanec', '/vozidlo',  '/uzivatel', '/aktualni-jizda', '/jizdni_rad', '/cas_zastavek', '/zastavka'];
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