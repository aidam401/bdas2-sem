package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Aktualni_Jizda {
    
    public Integer id_aktualni_jizda;
    
    public Integer zpozdeni;
    
    public Integer id_zaznam_jizdniho_radu;
    
    public Integer id_ridic;
    
    public Integer id_vozidlo;
}
