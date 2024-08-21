package org.example.validacao;

import org.example.modelos.Candidato;
import org.example.modelos.Usuario;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Votacao extends Remote {

    public List<Candidato> getCandidatos() throws RemoteException;
    public boolean setVoto(Usuario usuario, Candidato candidato) throws RemoteException;
    public String  getApuracaoDeVotos() throws RemoteException;
    public boolean isUsuario(Usuario usuario) throws RemoteException;

}
