package com.sockettestweb.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class ReconnectSocket implements Callable<AsynchronousSocketChannel> {
    private final InetSocketAddress socketAddress;

    public ReconnectSocket(final String host, final Integer port) {
        this.socketAddress = new InetSocketAddress(host, port);
    }

    @Override
    public AsynchronousSocketChannel call() throws Exception {
        try (AsynchronousSocketChannel channel = AsynchronousSocketChannel.open()) {
            while (true) {
                try {
                    channel.connect(socketAddress);
                    return channel;
                } catch (Exception ex) {
                    try {
                        sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
