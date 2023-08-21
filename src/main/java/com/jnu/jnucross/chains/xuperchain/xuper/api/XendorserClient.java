package com.jnu.jnucross.chains.xuperchain.xuper.api;

import com.jnu.jnucross.chains.xuperchain.xuper.pb.XendorserGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class XendorserClient {
    private final ManagedChannel channel;
    private final XendorserGrpc.XendorserBlockingStub blockingClient;

    public XendorserClient(String target) {
        this(ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build());
    }

    private XendorserClient(ManagedChannel channel) {
        this.channel = channel;
        blockingClient = XendorserGrpc.newBlockingStub(channel);
    }

    public void close() {
        channel.shutdown();
    }

    XendorserGrpc.XendorserBlockingStub getBlockingClient() {
        return blockingClient;
    }
}
