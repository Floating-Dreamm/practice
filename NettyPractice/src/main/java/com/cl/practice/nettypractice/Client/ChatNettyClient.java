package com.cl.practice.nettypractice.Client;

import com.cl.practice.nettypractice.Handler.ChatClientHandler;
import com.cl.practice.nettypractice.Handler.MyEncoder.MyDecoder;
import com.cl.practice.nettypractice.Handler.MyEncoder.MyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;
import java.util.Scanner;

//客户端代码
public class ChatNettyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast("decoder", new StringDecoder());
//                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("encoder",new MyEncoder());
                            pipeline.addLast("decoder",new MyDecoder());
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });

            System.out.println("netty client start。。");
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = cf.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                channel.writeAndFlush(s);
            }
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }


}