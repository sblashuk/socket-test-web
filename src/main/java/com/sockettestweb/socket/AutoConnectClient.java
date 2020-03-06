package com.sockettestweb.socket;

import com.sockettestweb.dto.ClientDto;
import com.sockettestweb.dto.client.ClientStatus;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.function.Consumer;

import static com.sockettestweb.dto.client.ClientStatus.*;
import static java.util.Objects.nonNull;


public class AutoConnectClient {

    private ClientStatus status = UNDEFINED;

    private ClientDto client;
    private AsynchronousSocketChannel socketChannel;
    private Consumer<ClientStatus> onClientStatusChanged;

    public AutoConnectClient(final ClientDto client, final Consumer<ClientStatus> onClientStatusChanged) {
        this.client = client;
        this.onClientStatusChanged = onClientStatusChanged;
        reconnect();
    }

    private void reconnect() {
        changeStatus(CONNECTING);

        Mono.fromCallable(new ReconnectSocket(client.getHost(), client.getPort()))
                .subscribe(this::onChannelConnect);
    }

    private void onChannelConnect(final AsynchronousSocketChannel channel) {
        this.socketChannel = channel;
        changeStatus(CONNECTED);
    }

    private void changeStatus(final ClientStatus newStatus) {
        this.status = newStatus;

        if (nonNull(onClientStatusChanged)) {
            onClientStatusChanged.accept(this.status);
        }
    }

    public void sendMessage(final String message) {
        ByteBuffer src = ByteBuffer.allocate(message.getBytes().length);
        src.put(message.getBytes());
        src.flip();

        try {
            socketChannel.write(src);
        } catch (Exception e) {
            reconnect();
        }
    }
}
