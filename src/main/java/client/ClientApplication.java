package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            var bootstrap = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder());
                        }
                    });
            var future = bootstrap.connect("127.0.0.1", 9090).sync();
            handleLogin(future);


            future.channel().closeFuture().sync();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        finally {
            loopGroup.shutdownGracefully();
        }
    }

    private static void handleLogin(ChannelFuture channelFuture){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username");
        while (true){
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("exit")){
                channelFuture.channel().close();
                return;
            }
            channelFuture.channel().writeAndFlush(s);
        }
    }
}
