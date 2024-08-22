package org.example.validacao;

import org.example.modelos.Candidato;
import org.example.modelos.Usuario;
import org.example.modelos.Voto;
import org.example.serverRMI.ServidorDeVoto;
import org.example.serverRMI.LamportClock;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VotacaoTrigger extends UnicastRemoteObject implements Votacao, Serializable {
    private List<Candidato> candidatos;
    private List<Voto> votos = new ArrayList<>();
    private int quantiaMaximaVotos = 10;
    private LamportClock clock;

    @Serial
    private static final long serialVersionUID = 1L;

    public VotacaoTrigger() throws RemoteException {
        super();
    }

    public VotacaoTrigger(List<Candidato> candidatos, LamportClock clock) throws RemoteException{
        super();
        this.candidatos = candidatos;
        this.clock = clock;
    }

    @Override
    public List<Candidato> getCandidatos() throws RemoteException{
        return this.candidatos;
    }

    @Override
    public boolean setVoto(Usuario usuario, Candidato candidato) throws RemoteException{
        if(isUsuario(usuario)){
            Voto voto = new Voto(usuario, candidato);
            votos.add(voto);

            clock.tick();
            System.out.println("Voto registrado, tempo lógico atualizado: " + clock.getTime());
            return true;

        }
        return false;
    }

    public String getApuracaoDeVotos() throws RemoteException{
        return contabilizaVotoDeUsuario(votos, candidatos);
    }

    public boolean isUsuario(Usuario usuario) {
        int quantiaVotos =  contabilizaVotoUsuario(votos, usuario);

        if(quantiaVotos < quantiaMaximaVotos) {
            return true;
        }

        return false;
    }

    private int contabilizaVotoUsuario(List<Voto> votos, Usuario usuario){
        int contaVotos = 0;
        for(Voto voto : votos) {
            if(voto.getUsuario().getId().equals(usuario.getId())){
                contaVotos++;
            }
        }
        return contaVotos;
    }

    private String contabilizaVotoDeUsuario(List<Voto> votos, List<Candidato> candidatos){
        StringBuilder apuracao = new StringBuilder();

        for(Candidato c : candidatos){
            int contabiliza = 0;

            for(Voto v : votos){

                if(v.getCandidato().getNumero() == c.getNumero()){
                    contabiliza++;
                }

            }

            apuracao.append("Candidato " +c.getNome() + "| Numero: "+ c.getNumero() + "| Votos: "+ contabiliza + "\n");
        }

        return apuracao.toString();
    }
}
