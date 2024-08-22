package org.example.serverRMI;

public class LamportClock {
    private int time;

    public LamportClock(){
        this.time = 5;
    }

    //Incrementa o tempo do relógio ao ocorrer um evento
    public synchronized void tick(){
        this.time++;
    }

    //Atualiza o tempo ao receber uma mensagem de outro processo
    public synchronized void update(int receivedTime){
        this.time = Math.max(this.time, receivedTime) + 1;
    }

    public synchronized int getTime(){
        return this.time;
    }
}
