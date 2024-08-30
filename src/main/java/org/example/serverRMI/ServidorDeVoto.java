package org.example.serverRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import org.example.modelos.Candidato;
import org.example.validacao.VotacaoTrigger;
import org.example.validacao.Votacao;

public class ServidorDeVoto {

    private static LamportClock clock = new LamportClock();

    public static void main(String[] args){
        List<Candidato> candidatos;
        Registry registry;
        Votacao votos;

        try{
            System.setProperty("java.rmi.server.hostname", "192.168.0.103");
            clock.tick();
            System.out.println("Servidor inicializado. Tempo lógico: "+clock.getTime());
            registry = LocateRegistry.createRegistry(1099);

            //Cria array de candidatos
            candidatos = new ArrayList<Candidato>();

            Scanner entrada = new Scanner(System.in);
            while(true) {
                System.out.println("Deseja inserir um candidato? (sim/não)");
                String resposta = entrada.next().toLowerCase();
                if(!resposta.equals("sim")) {
                    break;
                }

                System.out.println("Insira o número do candidato: ");
                String numeroCandidato = entrada.next();
                int numero = Integer.parseInt(numeroCandidato);
                System.out.println("Agora, insira o nome do candidato: ");
                String nomeCandidato = entrada.next();

                candidatos.add(new Candidato(nomeCandidato, numero));
            }

            votos = new VotacaoTrigger(candidatos, clock);

            registry.rebind("votar", votos);

            System.out.println("Server running on port "+registry);

            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run(){
                    try{
                        String apuracao = votos.getApuracaoDeVotos();
                        System.out.println("Estado atual dos votos: ");
                        System.out.println(apuracao);
                    } catch(Exception e){
                        System.out.println("Erro ao obter apuração de votos: " + e.getMessage());
                    }
                }
            }, 0, 5000); //Inicia na hora e é atualizado a cada 5 segundos

            //Mantém o servidor conectado
            synchronized (ServidorDeVoto.class){
                ServidorDeVoto.class.wait();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
