package com.cl.practice.nettypractice.Handler.MyEncoder;

import cn.hutool.core.util.StrUtil;
import com.cl.practice.common.Serializer.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String message, ByteBuf byteBuf) throws Exception {
        byte[] content = ProtostuffUtil.serializer(message);
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
        String msg = StrUtil.format("{}已转码，长度为{}",message,content.length);
        System.out.println(msg);
    }
}
