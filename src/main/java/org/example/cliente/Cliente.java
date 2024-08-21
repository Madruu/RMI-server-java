package org.example.cliente;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import org.example.modelos.Usuario;
import org.example.modelos.Candidato;
import org.example.validacao.Votacao;


public class Cliente {
    /*public static void main(String[] args) {
            Votacao votacao;
            List<Candidato> candidatos;
            Candidato candidato;
            String machineId;
            Usuario usuario;
            Scanner entrada;
            Registry registro;

            try {
                registro = LocateRegistry.getRegistry(3000);

                votacao = (Votacao) registro.lookup("localhost/vote");

                machineId = InetAddress.getLocalHost().getHostAddress();

                usuario = new Usuario(machineId);

                if(votacao.isUsuario(usuario)) {

                    candidatos = votacao.getCandidatos();

                    listCandidatos(candidatos);

                    entrada = new Scanner(System.in);

                    while(true) {

                        int voto;
                        String[] nomeCandidato;

                        while(true) {
                            System.out.println("Insira o número ou nome do candidato: ");
                            String inserirNumVoto = entrada.next();
                            if(isNumber(inserirNumVoto)){

                                voto = Integer.parseInt(inserirNumVoto);
                                break;

                            } else if(entrada.equals(candidatos.nome)){

                                nomeCandidato = entrada.nextLine().split(" ");
                                break;
                            }
                        }
                    }

                    candidatos = checkIfCandidatoExists(voto, votacao.getCandidatos());

                    if(candidatos != null){
                        if(votacao.setVoto(usuario, candidato)){
                            System.out.println("Voto cadastrado com sucesso!\n");
                            System.out.println("Votos apurados: ");
                            System.out.println(votacao.getApuracaoDeVotos());
                        }
                    } else {
                        System.out.println("O candidato não existe. tente novamente.");
                    }

                }
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
    }

    private static boolean isNumber(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private static Candidato checkIfCandidatoExists(int voto, List<Candidato> candidatos){
        for(Candidato c : candidatos){
            if(c.getNumero() == voto){
                return c;
            }
        }
        return null;
    }

    private static void listCandidatos(List<Candidato> candidatos){
        System.out.println("Candidatos: \n");
        for(Candidato c : candidatos){
            System.out.println("Candidato "+c.getNome() + "|| Número do candidato: " + c.getNumero());
        }
    }*/
    public static void main(String[] args) {
        Votacao votacao;
        List<Candidato> candidatos;
        Candidato candidato = null;
        String machineId;
        Usuario usuario;
        Scanner entrada;
        Registry registro;

        try {
            //registro = LocateRegistry.getRegistry(1099);
            //registro = LocateRegistry.getRegistry("192.168.0.102", 1099);
            //registro = LocateRegistry.getRegistry("192.168.0.102", 1099);
            registro = LocateRegistry.getRegistry(1099);

            votacao = (Votacao) registro.lookup("votar");

            machineId = InetAddress.getLocalHost().getHostAddress();

            usuario = new Usuario(machineId);

            if(votacao.isUsuario(usuario)) {

                candidatos = votacao.getCandidatos();

                listCandidatos(candidatos);

                entrada = new Scanner(System.in);

                while(true) {

                    System.out.println("Insira o número ou nome do candidato: ");
                    String inserirNumVoto = entrada.nextLine();

                    if(isNumber(inserirNumVoto)){
                        int voto = Integer.parseInt(inserirNumVoto);
                        candidato = checkIfCandidatoExists(voto, candidatos);
                        if(candidato != null) {
                            break;
                        }
                    } else {
                        candidato = checkIfCandidatoExists(inserirNumVoto, candidatos);
                        if(candidato != null) {
                            break;
                        }
                    }
                    System.out.println("Candidato não encontrado. Tente novamente.");
                }

                if(votacao.setVoto(usuario, candidato)){
                    System.out.println("Voto cadastrado com sucesso!\n");
                    System.out.println("Votos apurados: ");
                    System.out.println(votacao.getApuracaoDeVotos());
                }

            } else {
                System.out.println("Usuário já votou ou não é válido.");
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static boolean isNumber(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private static Candidato checkIfCandidatoExists(int voto, List<Candidato> candidatos){
        for(Candidato c : candidatos){
            if(c.getNumero() == voto){
                return c;
            }
        }
        return null;
    }

    private static Candidato checkIfCandidatoExists(String nome, List<Candidato> candidatos){
        for(Candidato c : candidatos){
            if(c.getNome().equalsIgnoreCase(nome)){
                return c;
            }
        }
        return null;
    }

    private static void listCandidatos(List<Candidato> candidatos){
        System.out.println("Candidatos: \n");
        for(Candidato c : candidatos){
            System.out.println("Candidato " + c.getNome() + " || Número do candidato: " + c.getNumero());
        }
    }
}
