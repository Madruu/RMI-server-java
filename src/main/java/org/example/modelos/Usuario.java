package org.example.modelos;

import java.io.Serial;
import java.io.Serializable;

public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    public Usuario(String identify){
        this.id = identify;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
