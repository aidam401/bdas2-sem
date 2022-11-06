package com.bdas2.app.dto;

import com.bdas2.app.dto.interfaces.IDatabased;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
@Data
@NoArgsConstructor
public class User implements IDatabased<User> {

    private String jmeno;
    private String heslo;
    private UrovenUzivatele urovenUzivatele;

    @Override
    public User fetchMe(ResultSet data) throws SQLException {
        this.jmeno = data.getString("JMENO");
        this.heslo = data.getString("HESLO");
        this.urovenUzivatele = new UrovenUzivatele().fetchMe(data);
        return this;
    }
}
