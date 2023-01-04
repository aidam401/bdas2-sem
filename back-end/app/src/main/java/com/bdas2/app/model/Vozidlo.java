package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Vozidlo {
    
    public Integer id_vozidlo;
    
    public String spz;
    
    public Integer max_rychlost;
    
    public String discr;
    
    public String nazev_vozidlo;
}
