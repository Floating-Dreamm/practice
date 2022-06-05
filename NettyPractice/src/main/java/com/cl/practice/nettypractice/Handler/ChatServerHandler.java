package com.cl.practice.nettypractice.Handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.List;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static final List<ChannelHandlerContext> chc = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        for (ChannelHandlerContext handlerContext : chc) {
            if (handlerContext.equals(channelHandlerContext)){
                channelHandlerContext.channel().writeAndFlush("消息已发送。");
            }else {
                String msg = StrUtil.format("来自{}的消息：{}",channelHandlerContext.channel().remoteAddress(),s);
                handlerContext.channel().writeAndFlush(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!chc.isEmpty()) {
            for (ChannelHandlerContext channelHandlerContext : chc) {
                String msg = "客户端" + ctx.channel().remoteAddress() + "已连接";
                channelHandlerContext.channel().writeAndFlush(msg);
            }
        }
        System.out.println("客户端" + ctx.channel().remoteAddress() + "已连接");
        chc.add(ctx);
        ctx.channel().writeAndFlush("已连接服务端：" + ctx.channel().localAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chc.remove(ctx);
        for (ChannelHandlerContext channelHandlerContext : chc) {
            String msg = StrUtil.format("{}客户端已下线",ctx.channel().remoteAddress());
            channelHandlerContext.channel().writeAndFlush(msg);
        }
        System.out.println(StrUtil.format("{}客户端已下线",ctx.channel().remoteAddress()));
    }
}
