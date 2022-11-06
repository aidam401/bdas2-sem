import Home from "../views/Home.vue";
import {createRouter, createWebHistory} from "vue-router";
import Linky from "@/views/Linky.vue";
import Adresy from "@/views/Adresy.vue";
import Zastavky from "@/views/Zastavky.vue";
import Spoje from "@/views/Spoje.vue";
import CasyZastavek from "@/views/CasyZastavek.vue";
import ZaznamyJizdnichRadu from "@/views/ZaznamyJizdnichRadu.vue";
import JizdniRady from "@/views/JizdniRady.vue";
import TypyJizdnichRadu from "@/views/TypyJizdnichRadu.vue";
import AktualniJizdy from "@/views/AktualniJizdy.vue";
import Ridici from "@/views/Ridici.vue";
import Trolejbusy from "@/views/Trolejbusy.vue";
import Autobusy from "@/views/Autobusy.vue";
import Uzivatele from "@/views/Uzivatele.vue";
import UzivatelskeRole from "@/views/UzivatelskeRole.vue";

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
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
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;