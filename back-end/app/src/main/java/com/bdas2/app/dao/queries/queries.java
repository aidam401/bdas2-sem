package com.bdas2.app.dao.queries;

public class queries {
    public static final String SELECT_ALL_USERS = "SELECT JMENO, HESLO, JMENO_UROVNE FROM UZIVATEL UZ JOIN UROVEN_UZIVATELE UR ON UZ.UROVEN_UZIVATELE = UR.ID_UROVNE_UZIVATELE";
}
