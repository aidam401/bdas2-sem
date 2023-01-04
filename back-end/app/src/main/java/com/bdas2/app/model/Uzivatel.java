package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Uzivatel {
    
    public Integer id_uzivatel;
    
    public String login;
    
    public String heslo;
    
    public Integer id_soubor;
    
    public Integer id_role;


}
