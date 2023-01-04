package com.bdas2.app.model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zaznam_Jizdniho_Radu {

    public Integer id_zaznam_jizdniho_radu;

    public Date pravidelny_prijezd;

    public Date pravidelny_odjezd;

    public Integer id_spoj;

    public Integer id_jizdni_rad;

    public Integer id_zastavka_linka;
}
