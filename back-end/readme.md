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
* JIZDNI_RAD_VIEW - kombinace tabulek zastavka **LINKA, SPOJ, ZASTAVKA_LINKA, ZAZNAM_JIZDNIHO_RADU, JIZDNI_RAD**


# ENDPOINTY:


## GET
### ✨Za login wallem

`localhost:8080/<nazevTabulky>/read` -- Všechny záznamy z tabulky

### Example:

http://localhost:8080/adresa/read

###  Response:

[
{
"ULICE": "Palác pod schody",
"ID_ADRESA": 1,
"MESTO": "Šalingrad"
},
{
"ULICE": "Zombie ulice",
"ID_ADRESA": 2,
"MESTO": "Šalingrad"
},
{
"ULICE": "Palác asdasdasdawda",
"ID_ADRESA": 42,
"MESTO": "Šalingrad"
},
{
"ULICE": "Palác asdasdasdawda",
"ID_ADRESA": 43,
"MESTO": "Šalingrad"
},
{
"ULICE": "Bratrství",
"ID_ADRESA": 23,
"MESTO": "Šalingrad"
},
{
"ULICE": "Obvodová",
"ID_ADRESA": 24,
"MESTO": "Šalingrad"
},
{
"ULICE": "Palác asdasdasdawda",
"ID_ADRESA": 41,
"MESTO": "Šalingrad"
},
{
"ULICE": "Palác asdasdasdawda",
"ID_ADRESA": 44,
"MESTO": "Šalingradaaaaaa"
}
]

#
#

### ✨Za login wallem

`http://localhost:8080/<nazevTabulky>/read?limit=<číslo>&offset=<číslo>` -- Stránkovaný záznam z tabulky

### Example:

http://localhost:8080/adresa/read?limit=1&offset=1

###  Response:

[
{
"ULICE": "Zombie ulice",
"ID_ADRESA": 2,
"MESTO": "Šalingrad"
}
]

#
#

### ✨Za login wallem

`localhost:8080/<nazevTabulky>/read?query=<String>` -- Vyhledávání podle query

### Example:

localhost:8080/spoj/read?query=ten

###  Response:

[
{
"NAZEV_SPOJ": "Ten spojenej",
"ID_LINKA": 1,
"ID_SPOJ": 1
}
]

#
#

### ✨Za login wallem

`localhost:8080/<nazevTabulky>/read?id=<číslo>` -- Specifická záznam z tabulky

### Example:

http://localhost:8080/adresa/read?id=1

###  Response:

[
{
"ULICE": "Palác pod schody",
"ID_ADRESA": 1,
"MESTO": "Šalingrad"
}
]

#
#

### ✨Za login wallem

`localhost:8080/<nazevTabulky>/count` -- Počet záznamů v tabulce

### Example:

http://localhost:8080/adresa/count

###  Response:

8

#
#

### ✨✨Bez login wallu

`localhost:8080/login?name=<String>&password=<String>&anotherUser=<JmenuUsera>` -- Ověření existence uživatele

### Example:

http://localhost:8080/login?name=Igor&password=Igorovic

###  Response:

[true,{"NAZEV_ROLE":"admin","LOGIN":"Igor","ID_UZIVATELE":9,"ID_ROLE":1,"HESLO":"Igorovic"}]

#
#

## POST

### ✨Za login wallem

`http://localhost:8080/{nazevTabulky}/create`
`+ body`
-- Vytvoření záznamu

### Example:

http://localhost:8080/adresa/create

`body`:
{
"ULICE": "test",
"MESTO": "test2"
}

###  Response:

true

#
#
### ✨Za login wallem

`http://localhost:8080/{nazevTabulky}/update?id={číslo}`
`+ body`
-- Úprava záznamu

### Example:

http://localhost:8080/adresa/update?id=1

`body`:
{
"ULICE": "test",
"MESTO": "test2"
}

###  Response:

true
#
#

## DELETE

### ✨Za login wallem

`http://localhost:8080/{nazevTabulky}/delete?id={číslo}` -- Smazání záznamu

### Example:

http://localhost:8080/adresa/delete?id=1


###  Response:

true