package com.example.a1107513806.viernestaller1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;

/**
 * Created by 1107513806 on 30/09/2016.
 */
public class Comunicacion extends Observable implements Runnable {
    DatagramSocket dgs;
    private InetAddress ipServer;
    String server;
    private final int PUERTO= 5000;


    private static  Comunicacion ref;




    private Comunicacion(String ipserver) {


        this.server= ipserver;
        try {
            ipServer = InetAddress.getByName(server);
            dgs= new DatagramSocket(PUERTO);
            System.out.println("iniciado socket en puerto:"+PUERTO);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Comunicacion getInstance(String ipserver) {
        if (ref == null) {
            ref = new Comunicacion(ipserver);
            Thread runner = new Thread(ref);
            runner.start();
        }

        return ref;
    }


    public void run() {
        while (true) {
            recibir();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private byte[] serializar(Object m){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(m);
            oos.flush();
            oos.close();
            return baos.toByteArray();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    private Object deserializar(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object aux =  ois.readObject();
            return aux;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void recibir() {
        try {
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);

            dgs.receive(dp);

            Object recibido = deserializar(dp.getData());

            setChanged();
            notifyObservers(recibido);
            clearChanged();



/*if(recibido instanceof Categoria){
    //TODO hacer algo con la categoria
}*/

    /*        if(recibido instanceof String){
//TODO hacer algo con el string (valida si el usuario existe o si el usuario se registro satisfactoriamente)
            }
*/





        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void enviar(Object m) {
        try {
            byte[] datos = serializar(m);
            DatagramPacket paq = new DatagramPacket(datos, datos.length, ipServer, PUERTO);
            dgs.send(paq);
            System.out.println("objeto enviado");
            System.out.println(ipServer.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
