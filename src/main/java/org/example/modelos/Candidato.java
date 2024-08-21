package org.example.modelos;

import java.io.Serial;
import java.io.Serializable;

public class Candidato implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private int numero;

    public Candidato(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    @Override
    public String toString(){
        return nome + " " + numero;
    }
}
