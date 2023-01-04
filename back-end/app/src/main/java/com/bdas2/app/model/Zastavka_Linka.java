package com.bdas2.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zastavka_Linka {
    public Integer id_zastavkaLinka;
    public Integer id_zastavka;
    public Integer id_linka;
    public Integer poradi_zastavky;
}
