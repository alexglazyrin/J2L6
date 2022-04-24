package ru.gb;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(8189)){
            System.out.println("Сервер запущен. Ждем подключения клиента.");
            socket = serverSocket.accept();
            System.out.println("Клиент подключен");
            final DataInputStream  in = new DataInputStream(socket.getInputStream());
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            final String message = in.readUTF();
                            System.out.println("Клиент: " + message);
                            if ("/end".equalsIgnoreCase(message)){
                                out.writeUTF("/end");
                                break;
                            }
                            //out.writeUTF("Эхо: " + message);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Server stoped.");
                }
            });
            thread.start();


            try {
                final Scanner scanner = new Scanner(System.in);
                while (true) {
                    final String msg = scanner.nextLine();
                    out.writeUTF(msg);
                    if ("/end".equalsIgnoreCase(msg)) {
                        break;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
