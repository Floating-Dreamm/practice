package com.cl.practice.nettypractice.Client;

import com.cl.practice.nettypractice.Handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static Bootstrap bootstrap;
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("netty clent start..");

            connect();

        } catch (InterruptedException e) {
            group.shutdownGracefully();
        }


    }

    public static ChannelFuture connect() throws InterruptedException {
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 9000);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()){
                    channelFuture.channel().eventLoop().schedule(()->{
                        System.out.println("重新连接服务器");
                        try {
                            connect();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    },3, TimeUnit.MILLISECONDS);
                }else {
                    System.out.println("连接成功");
                }
            }
        });
        //对关闭通道进行监听
        cf.channel().closeFuture().sync();
        return cf;
    }


}
