package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author 王文
 * @version : V1.0
 * @className: C10Kclient
 * @description: 测压客户端
 * @date 2021-05-19
 */
public class C10Kclient {
    public static void main(String[] args) throws IOException {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        InetSocketAddress address = new InetSocketAddress("192.168.182.129", 9090);

        for (int i = 10000; i < 65000; i++) {
            try {
                SocketChannel client = SocketChannel.open();
                client.bind(new InetSocketAddress("192.168.182.1",i));
                client.connect(address);
                boolean c1 = client.isOpen();
                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("clients"+clients.size());
    }
}
