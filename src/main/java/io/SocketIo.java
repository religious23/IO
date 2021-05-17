package io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 王文
 * @version : V1.0
 * @className: SocketIo
 * @description: 网络IO
 * @date 2021-05-17
 */
public class SocketIo {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090, 20);
        System.out.println("step1: new ServerSocket(9090)");

        while (true) {
            //阻塞1
            Socket client = server.accept();
            System.out.println("step2:client\t" + client.getPort());

            new Thread(() -> {
                InputStream in = null;
                try {
                    in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while (true) {
                        String dataline = reader.readLine();//阻塞2
                        if (null != dataline) {
                            System.out.println(dataline);
                        }else{
                            client.close();
                            break;
                        }
                    }
                    System.out.println("客户端断开");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
