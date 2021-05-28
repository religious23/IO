package io;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author 王文
 * @version : V1.0
 * @className: SocketNIO
 * @description: NIO
 * @date 2021-05-27
 */
public class SocketNIO {
    public static void main(String[] args) throws Exception {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        //设置为非阻塞
        ss.configureBlocking(false);
        //ss.setOption(StandardSocketOptions.TCP_NODELAY, false);
        while (true) {
            Thread.sleep(1000);
            //不会阻塞 -1 null
            SocketChannel client = ss.accept();
            if (client == null) {
                System.out.println("null....");
            } else {
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client...port" + port);
                clients.add(client);
            }
            //可以在堆里 也可以在堆外
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            //串行化 多线程
            for (SocketChannel c : clients) {
                //不会阻塞
                int num = c.read(buffer);
                if(num>0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);

                    String b=new String(bytes);
                    System.out.println(c.socket().getPort()+":"+b);
                    buffer.clear();
                }
            }
        }
    }
}
