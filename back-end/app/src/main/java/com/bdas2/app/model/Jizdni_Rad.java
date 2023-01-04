package com.bdas2.app.model;

import lombok.*;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jizdni_Rad {
    
    public Integer id_jizdni_rad;
    
    public Date datum_zahajeni_platnosti;
    
    public Date datum_konce_platnosti;
    
    public String nazev_jizdni_rad;
}
