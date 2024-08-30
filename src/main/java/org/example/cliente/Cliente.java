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

    public static void main(String[] args) {

        Votacao votacao;
        List<Candidato> candidatos;
        Candidato candidato;
        String machineId;
        Usuario usuario;
        Scanner entrada;
        Registry registro;

        try{
            registro = LocateRegistry.getRegistry(1099);

            votacao = (Votacao) registro.lookup("votar");

            machineId = InetAddress.getLocalHost().getHostAddress();
            usuario = new Usuario(machineId);

            if (votacao.isUsuario(usuario)) {
                candidatos = votacao.getCandidatos();
                listCandidatos(candidatos);

                entrada = new Scanner(System.in);


                while (true) {
                    System.out.println("Deseja votar? (sim/não): ");
                    //System.out.println(votacao.getApuracaoDeVotos());
                    String resposta = entrada.next().toLowerCase();

                    if (!resposta.equals("sim")) {
                        System.out.println("Encerrando o processo de votação.");
                        break;
                    }

                    int voto = -1;

                    System.out.println("Insira o número do candidato: ");
                    String inserirNumVoto = entrada.next();


                    if (isNumber(inserirNumVoto)) {
                        voto = Integer.parseInt(inserirNumVoto);
                    } else {
                        System.out.println("Entrada inválida. Por favor, insira um número.");
                        continue;
                    }

                    candidato = checkIfCandidatoExists(voto, candidatos);

                    if (candidato != null) {

                        if (votacao.setVoto(usuario, candidato)) {
                            System.out.println("Voto cadastrado com sucesso!\n");
                            System.out.println("Votos apurados: ");
                            System.out.println(votacao.getApuracaoDeVotos());
                        }

                    } else {
                        System.out.println("Candidato não encontrado. Tente novamente.");
                    }
                }

            } else {
                System.out.println("Usuário não autorizado a votar ou já votou.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Candidato checkIfCandidatoExists(int voto, List<Candidato> candidatos) {
        for (Candidato c : candidatos) {
            if (c.getNumero() == voto) {
                return c;
            }
        }
        return null;
    }

    private static void listCandidatos(List<Candidato> candidatos) {
        System.out.println("Candidatos: \n");
        for (Candidato c : candidatos) {
            System.out.println("Candidato: " + c.getNome() + " | Número: " + c.getNumero());
        }
    }
}
