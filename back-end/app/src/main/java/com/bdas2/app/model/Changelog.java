package com.bdas2.app.model;

import lombok.*;
import org.springframework.lang.Nullable;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Changelog {
    
    public Integer id;
    
    public String operation;
    
    public Date time;
    
    public String oldVal;
    
    public String newVal;

}
