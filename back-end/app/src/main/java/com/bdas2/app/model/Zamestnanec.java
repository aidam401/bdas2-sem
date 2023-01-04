package com.bdas2.app.model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zamestnanec {
    
    public Integer id_zamestnanec;
    
    public String jmeno;
    
    public String prijmeni;
    
    public Date datum_narozeni;
    
    public Integer pozice;

}
