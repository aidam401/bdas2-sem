package com.bdas2.app.dto;


import com.bdas2.app.dto.interfaces.IDatabased;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
public class UrovenUzivatele implements IDatabased<UrovenUzivatele> {
    String jmenoUrovne;

    @Override
    public UrovenUzivatele fetchMe(ResultSet data) throws SQLException {
        this.jmenoUrovne = data.getString("JMENO_UROVNE");
        return this;
    }
}
