package org.example.modelos;

public class Voto {

    private Candidato candidato;
    private Usuario usuario;

    public Voto() {};

    public Voto(Usuario usuario, Candidato candidato){
        this.usuario = usuario;
        this.candidato = candidato;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato){
        this.candidato = candidato;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
