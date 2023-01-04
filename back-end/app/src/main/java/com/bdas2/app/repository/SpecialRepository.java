package com.bdas2.app.repository;


import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;


import java.sql.Types;

@Repository
public class SpecialRepository {
    public final Dao dao;

    public SpecialRepository(Dao dao) {
        this.dao = dao;
    }

    public JSONArray linky(String nazev, Integer limit, Integer offset) {
        var sql = "SELECT  ID_LINKA, NAZEV_LINKA, COUNT(ID_ZASTAVKA) " +
                "FROM ZASTAVKA_LINKA_VIEW " +
                "WHERE NAZEV_LINKA LIKE ? " +
                "GROUP BY ID_LINKA, NAZEV_LINKA " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        return dao.fetchJsonArray(sql, new Object[]{"%"+nazev+"%", offset, limit}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
    }

    public JSONArray linkyDetail(Integer id) {
        var sql = "SELECT ID_ZASTAVKA_LINKA, ID_ZASTAVKA, NAZEV_ZASTAVKA, PORADI_ZASTAVKY " +
                "FROM ZASTAVKA_LINKA_VIEW " +
                "WHERE ID_LINKA = ?";

        return dao.fetchJsonArray(sql,  new Object[]{id}, new int[]{Types.INTEGER});
    }


    public JSONArray spoje(Integer limit, Integer offset, Integer id) {
        var sql = "SELECT ID_SPOJ, ID_LINKA, NAZEV_SPOJ, ID_JIZDNI_RAD " +
                "FROM ZAZNAM_JIZDNIHO_RADU_VIEW " +
                "where ID_SPOJ = ? " +
                "GROUP BY ID_SPOJ, ID_LINKA, NAZEV_SPOJ, ID_JIZDNI_RAD " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        return dao.fetchJsonArray(sql, new Object[]{id, offset, limit}, new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER});
    }

    public JSONArray spojeDetail(Integer id) {
        var sql = "SELECT ID_ZAZNAM_JIZDNIHO_RADU, ID_ZASTAVKA_LINKA, NAZEV_ZASTAVKA, PRAVIDELNY_PRIJEZD, PRAVIDELNY_ODJEZD, " +
                "ID_JIZDNI_RAD  FROM ZAZNAM_JIZDNIHO_RADU_VIEW  WHERE ID_SPOJ = ? " +
                "ORDER BY PORADI_ZASTAVKY";

        return dao.fetchJsonArray(sql,  new Object[]{id}, new int[]{Types.INTEGER});
    }

    public JSONArray jizdy(String nazev, Integer limit, Integer offset) {
        var sql = "SELECT id_aktualni_jizda, JMENO, PRIJMENI, nazev_vozidlo, ID_SPOJ, nazev_spoj, ZPOZDENI " +
                "FROM JIZDY_VIEW " +
                "WHERE NAZEV_SPOJ LIKE ? " +
                "GROUP BY id_aktualni_jizda, JMENO, PRIJMENI, nazev_vozidlo, ID_SPOJ, nazev_spoj, ZPOZDENI "+
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        return dao.fetchJsonArray(sql, new Object[]{"%"+nazev+"%", offset, limit}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
    }

    public JSONArray jizdyDetail(Integer id) {
        var sql = "SELECT ID_AKTUALNI_JIZDA, ZPOZDENI, ID_VOZIDLO, id_zamestnanec, JMENO, PRIJMENI, nazev_vozidlo FROM JIZDY_VIEW " +
                "WHERE ID_AKTUALNI_JIZDA = ?";

        return dao.fetchJsonArray(sql,  new Object[]{id}, new int[]{Types.INTEGER});
    }


}
