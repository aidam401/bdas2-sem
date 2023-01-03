import Home from "../views/Home.vue";
import {createRouter, createWebHistory} from "vue-router";
import Linky from "@/views/entities/Linky/Linky.vue";
import Zastavky from "@/views/entities/Zastavky/Zastavky.vue";
import Spoje from "@/views/entities/Spoje.vue";
import CasyZastavek from "@/views/entities/CasyZastavek.vue";
import ZaznamyJizdnichRadu from "@/views/entities/ZaznamyJizdnichRadu.vue";
import JizdniRady from "@/views/entities/JizdniRad/JizdniRady.vue";
import AktualniJizdy from "@/views/entities/AktualniJizdy.vue";
import Uzivatele from "@/views/entities/Uzivatele/Uzivatele.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import store from "@/store";
import Profile from "@/views/Profile.vue";
import Vozidla from "@/views/entities/Vozidla.vue";
import Zamestnanci from "@/views/entities/Zamestnanci.vue";
import UzivatelDetail from "@/views/entities/Uzivatele/UzivatelDetail.vue";
import ZastavkaDetail from "@/views/entities/Zastavky/ZastavkaDetail.vue";
import uzivatelNovy from "@/views/entities/Uzivatele/UzivatelNovy.vue";
import ZastavkaNova from "@/views/entities/Zastavky/ZastavkaNova.vue";
import PoziceZamestnance from "@/views/entities/PoziceZamestnance/PoziceZamestnance.vue";
import PoziceZamestnanceDetail from "@/views/entities/PoziceZamestnance/PoziceZamestnanceDetail.vue";
import PoziceZamestnanceNova from "@/views/entities/PoziceZamestnance/PoziceZamestnanceNova.vue";
import Role from "@/views/entities/Role/Role.vue";
import RoleNova from "@/views/entities/Role/RoleNova.vue";
import RoleDetail from "@/views/entities/Role/RoleDetail.vue";
import LinkaNova from "@/views/entities/Linky/LinkaNova.vue";
import LinkaDetail from "@/views/entities/Linky/LinkaDetail.vue";

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
    },
    {
        path: "/zastavka",
        name: "Zastavka",
        component: Zastavky
    },{
        path: '/zastavka/add',
        name: 'ZastavkaNova',
        component: ZastavkaNova
    }, {
        path: '/zastavka/:id',
        name: 'ZastavkaDetail',
        component: ZastavkaDetail
    },
    {
        path: "/uzivatel",
        name: "Uzivatel",
        component: Uzivatele,
    }, {
        path: '/uzivatel/add',
        name: 'UzivatelNovy',
        component: uzivatelNovy
    }, {
        path: '/uzivatel/:id',
        name: 'UzivatelDetail',
        component: UzivatelDetail
    },
    {
        path: "/pozice",
        name: "Pozice",
        component: PoziceZamestnance
    }, {
        path: "/pozice/add",
        name: "PoziceNova",
        component: PoziceZamestnanceNova
    }, {
        path: "/pozice/:id",
        name: "PoziceDetail",
        component: PoziceZamestnanceDetail
    },
    {
        path: "/role",
        name: "Role",
        component: Role
    }, {
        path: "/role/add",
        name: "RoleNova",
        component: RoleNova
    }, {
        path: "/role/:id",
        name: "RoleDetail",
        component: RoleDetail
    },
    {
        path: "/linka",
        name: "Linka",
        component: Linky
    }, {
        path: "/linka/add",
        name: "LinkaNova",
        component: LinkaNova
    }, {
        path: "/linka/:id",
        name: "LinkaDetail",
        component: LinkaDetail
    },





    {
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
        path: "/vozidlo",
        name: "Vozidlo",
        component: Vozidla
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
    console.log("Je pravda že tuto routu může použít admin?: ", adminRequired);
    // pokud je stránka adminovská
    if (adminRequired && !isAdmin) {
        return next({
            path: '/'
        });
    }

    next();
})

export default router;