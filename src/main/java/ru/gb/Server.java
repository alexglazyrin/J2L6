package ru.gb;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)){
            System.out.println("Сервер запущен. Ждем подключения клиента.");
            socket = serverSocket.accept();
            System.out.println("Клиент подключен");
            final DataInputStream  in = new DataInputStream(socket.getInputStream());
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true){
                final String message = in.readUTF();
                System.out.println("Клиент: " + message);
                if ("/end".equalsIgnoreCase(message)){
                    out.writeUTF("/end");
                    break;
                }
                out.writeUTF("Эхо: " + message);
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Сервер остановлен");
    }
}
