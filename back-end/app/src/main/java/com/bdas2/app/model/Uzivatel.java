package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Uzivatel {
    
    public Integer ID_UZIVATEL;
    
    public String LOGIN;
    
    public String HESLO;
    
    public Integer ID_SOUBOR;
    
    public Integer ID_ROLE;


}
