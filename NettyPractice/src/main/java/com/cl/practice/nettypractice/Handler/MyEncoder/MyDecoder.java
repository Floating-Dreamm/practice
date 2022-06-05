package com.cl.practice.nettypractice.Handler.MyEncoder;

import com.cl.practice.common.Serializer.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder  extends ByteToMessageDecoder {

    int len = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= 4){
            if (len == 0){
                len = byteBuf.readInt();
            }
            if (byteBuf.readableBytes()<len){
                System.out.println("当前可读数据不够");
                return;
            }

            byte[] bytes = new byte[len];
            byteBuf.readBytes(bytes);

            String content = ProtostuffUtil.deserializer(bytes, String.class);
            list.add(content);
            len = 0;
        }
    }
}
