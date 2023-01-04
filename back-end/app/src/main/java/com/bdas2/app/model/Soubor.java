package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

import java.io.File;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Soubor {
    
    public Integer id_souboru;
    
    public File data_soubor;
    
    public String nazev_souboru;
    
    public String typ_souboru;
    
    public Date datum_souboru;
}
