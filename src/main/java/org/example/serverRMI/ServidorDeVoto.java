package org.example.serverRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.example.modelos.Candidato;
import org.example.validacao.VotacaoTrigger;
import org.example.validacao.Votacao;

public class ServidorDeVoto {

    public static void main(String[] args){
        List<Candidato> candidatos;
        Registry registry;
        Votacao votos;

        try{
            System.setProperty("java.rmi.server.hostname", "192.168.0.102");
            //registry = LocateRegistry.createRegistry(3000);
            registry = LocateRegistry.createRegistry(1099);

            //Cria array de candidatos
            candidatos = new ArrayList<Candidato>();

            candidatos.add(new Candidato("Vitor", 2346621));
            candidatos.add(new Candidato("Victor", 2346613));
            candidatos.add(new Candidato("Pedro", 2346256));
            candidatos.add(new Candidato("Maria Fernanda", 2346694));

            votos = new VotacaoTrigger(candidatos);

            registry.rebind("votar", votos);

            System.out.println("Server running on port "+registry);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
