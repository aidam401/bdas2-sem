package com.bdas2.app.model;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zamestnanec {
    
    public Integer ID_ZAMESTNANEC;
    
    public String JMENO;
    
    public String PRIJMENI;
    
    public Date DATUM_NAROZENI;
    
    public Integer POZICE;

}
