package com.cl.practice.nettypractice.Handler.VO;

import lombok.Data;

@Data
public class MessageProtocol {
    private int len;
    private byte[] content;

    public MessageProtocol(byte[] content) {
        this.content = content;
        this.len = content.length;
    }

}
