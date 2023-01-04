## Spuštění serveru:
Ve složce:
>\back-end\app\target:

Příkaz:

```bash
java -jar app-0.0.1-SNAPSHOT.jar
```
✨  Samostatný build je troju magie, tudíž s každou novou verzí budu přidávat nový zkompilovaný target.

## Upoznění, ke správné funkčnosti je třeba připojení na učebny přes vpn a verze javy alespoň 18.

## Seznam views:
* UZIVATELE_VIEW - kombinace tabulek **SOUBOR, ROLE, UZIVATEL**


* ZASTAVKA_LINKA_VIEW - kombinace tabulek **LINKA, ZASTAVKA_LINKA, ZASTAVKA**


* JIZDNI_RAD_VIEW - kombinace tabulek  **ZASTAVKA, LINKA, SPOJ, ZASTAVKA_LINKA, ZAZNAM_JIZDNIHO_RADU, JIZDNI_RAD**


* JIZDY_VIEW - kombinace tabulek **ZASTAVKA, ZASTAVKA_LINKA, LINKA, SPOJ, ZAZNAM_JIZDNIHO_RADU, JIZDNI_RAD JR, AKTUALNI_JIZDA, ZAMESTNANEC, VOZIDLO, TROLEJBUS, AUTOBUS**


* VOZIDLO_VIEW - kombinace tabulek **VOZIDLO, AUTOBUS, TROLEJBUS**


* ZAMESTNANEC_VIEW - kombinace tabulek **ZAMESTNANEC, POZICE_ZAMESTNANCE**


* RIDIC_VIEW - kombinace tabulek **ZAMESTNANEC, POZICE_ZAMESTNANCE** POUZE ŘIDIČI


# ENDPOINTY:

` ✨ GET http://localhost:8080/<TABULKA>/detail?id=<INTEGER>`

`✨ GET http://localhost:8080/<TABULKA>/get?offset=<INTEGER>&limit=<INTEGER>`

`✨ GET http://localhost:8080/<TABULKA>/search?offset=<INTEGER>&limit=<INTEGER>?query=<STRING>`

`✨ DELETE http://localhost:8080/<TABULKA>/delete?id=<INTEGER>`

`✨ POST http://localhost:8080/<TABULKA>/create`
* `+ body` 

`✨ POST http://localhost:8080/<TABULKA>/update?id=<INTEGER>`
* `+ body`



# SPECIÁLNÍ ENDPOINTY:

## GET

#
#
### ✨Za login wallem

`http://localhost:8080/linky?nazev=<String>&limit=<Integer>&offset=<Integer>`

"SELECT  ID_LINKA, NAZEV_LINKA, COUNT(ID_ZASTAVKA) " +
"FROM ZASTAVKA_LINKA_VIEW " +
"WHERE NAZEV_LINKA LIKE ? " +
"GROUP BY ID_LINKA, NAZEV_LINKA " +
"OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"


`http://localhost:8080/linkyDetail?id=<Integer>`

"SELECT ID_ZASTAVKA_LINKA, ID_ZASTAVKA, NAZEV_ZASTAVKA, PORADI_ZASTAVKY " +
"FROM ZASTAVKA_LINKA_VIEW " +
"WHERE ID_LINKA = ?"

#
#
### ✨Za login wallem
`http://localhost:8080/spoje?nazev=<String>&limit=<Integer>&offset=<Integer>`

"SELECT ID_SPOJ, ID_LINKA, NAZEV_LINKA, NAZEV_SPOJ, PRAVIDELNY_PRIJEZD, PRAVIDELNY_ODJEZD " +
"FROM JIZDNI_RAD_VIEW " +
"WHERE NAZEV_SPOJ LIKE ? " +
"GROUP BY ID_SPOJ, ID_LINKA, NAZEV_LINKA, NAZEV_SPOJ, PRAVIDELNY_PRIJEZD, PRAVIDELNY_ODJEZD " +
"OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"


`http://localhost:8080/spojeDetail?id=<Integer>`

"SELECT ID_ZAZNAM_JIZDNIHO_RADU, ID_ZASTAVKA, NAZEV_ZASTAVKA, PORADI_ZASTAVKY, PRAVIDELNY_PRIJEZD, PRAVIDELNY_ODJEZD, ID_JIZDNI_RAD, NAZEV_JIZDNI_RAD " +
"FROM JIZDNI_RAD_VIEW " +
"WHERE ID_SPOJ = ?"
#
#
### ✨Za login wallem
`http://localhost:8080/jizdy?nazev=<String>&limit=<Integer>&offset=<Integer>`

"SELECT id_aktualni_jizda, JMENO, PRIJMENI, nazev_vozidlo, ID_SPOJ, nazev_spoj, ZPOZDENI " +
"FROM JIZDY_VIEW " +
"WHERE NAZEV_SPOJ LIKE ? " +
"GROUP BY id_aktualni_jizda, JMENO, PRIJMENI, nazev_vozidlo, ID_SPOJ, nazev_spoj, ZPOZDENI "+
"OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"


`http://localhost:8080/jizdyDetail?id=<Integer>`

"SELECT ID_AKTUALNI_JIZDA, ZPOZDENI, ID_VOZIDLO, id_zamestnanec, JMENO, PRIJMENI, nazev_vozidlo FROM JIZDY_VIEW " +
"WHERE ID_AKTUALNI_JIZDA = ?"
#
#