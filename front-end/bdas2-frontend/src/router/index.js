import Home from "../views/Home.vue";
import {createRouter, createWebHistory} from "vue-router";
import Linky from "@/views/entities/Linky/Linky.vue";
import Zastavky from "@/views/entities/Zastavky/Zastavky.vue";
import Spoje from "@/views/entities/Spoje/Spoje.vue";
import ZaznamyJizdnichRadu from "@/views/entities/ZaznamyJizdnichRadu.vue";
import JizdniRady from "@/views/entities/JizdniRad/JizdniRady.vue";
import AktualniJizdy from "@/views/entities/AktualniJizdy.vue";
import Uzivatele from "@/views/entities/Uzivatele/Uzivatele.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import store from "@/store";
import Profile from "@/views/Profile.vue";
import Vozidla from "@/views/entities/Vozidla/Vozidla.vue";
import Zamestnanci from "@/views/entities/Zamestnanci/Zamestnanci.vue";
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
import VozidloNove from "@/views/entities/Vozidla/VozidloNove.vue";
import VozidloDetail from "@/views/entities/Vozidla/VozidloDetail.vue";
import ZamestnanecNovy from "@/views/entities/Zamestnanci/ZamestnanecNovy.vue";
import ZamestnanecDetail from "@/views/entities/Zamestnanci/ZamestnanecDetail.vue";
import JizdniRadNovy from "@/views/entities/JizdniRad/JizdniRadNovy.vue";
import JIzdniRadDetail from "@/views/entities/JizdniRad/JIzdniRadDetail.vue";
import SpojNovy from "@/views/entities/Spoje/SpojNovy.vue";
import SpojDetail from "@/views/entities/Spoje/SpojDetail.vue";
import HledatSpoj from "@/views/entities/HledatSpoj.vue";
import HledatLinku from "@/views/entities/HledatLinku.vue";

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
    }, {
        path: "/vozidlo",
        name: "Vozidlo",
        component: Vozidla
    }, {
        path: "/vozidlo/add",
        name: "VozidloNove",
        component: VozidloNove
    }, {
        path: "/vozidlo/:id",
        name: "VozidloDetail",
        component: VozidloDetail
    }, {
        path: "/zamestnanec",
        name: "Zamestnanec",
        component: Zamestnanci
    }, {
        path: "/zamestnanec/add",
        name: "ZamestnanecNovy",
        component: ZamestnanecNovy
    }, {
        path: "/zamestnanec/:id",
        name: "ZamestnanecDetail",
        component: ZamestnanecDetail
    }, {
        path: "/jizdni-rad",
        name: "JizdniRad",
        component: JizdniRady
    }, {
        path: "/jizdni-rad/add",
        name: "JizdniRadNovy",
        component: JizdniRadNovy
    }, {
        path: "/jizdni-rad/:id",
        name: "JizdniRadDetail",
        component: JIzdniRadDetail
    }, {
        path: "/spoj",
        name: "Spoj",
        component: Spoje
    }, {
        path: "/spoj/add",
        name: "SpojNovy",
        component: SpojNovy
    }, {
        path: "/spoj/:id",
        name: "SpojDetail",
        component: SpojDetail
    }, {
        path: "/hledatSpoj",
        name: "SpojSearch",
        component: HledatSpoj
    }, {
        path: "/hledatLinku",
        name: "LinkaSearch",
        component: HledatLinku
    },




    {
        path: "/aktualni-jizda",
        name: "Aktualni_jizda",
        component: AktualniJizdy
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
    const userPaths = ['/', '/profile', '/spoj', '/login', '/register', '/hledatLinku', '/hledatSpoj']
    //const adminPaths = ['/zamestnanec', '/vozidlo',  '/uzivatel', '/aktualni-jizda', '/jizdni_rad', '/cas_zastavek', '/zastavka'];
    const authRequired = !publicPaths.includes(to.path);
    const adminRequired = !userPaths.includes(to.path);
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