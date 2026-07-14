package com.tdev.winltr.xenvironment.components;

import com.tdev.winltr.alsaserver.ALSAClientConnectionHandler;
import com.tdev.winltr.alsaserver.ALSARequestHandler;
import com.tdev.winltr.xconnector.UnixSocketConfig;
import com.tdev.winltr.xconnector.XConnectorEpoll;
import com.tdev.winltr.xenvironment.EnvironmentComponent;

public class ALSAServerComponent extends EnvironmentComponent {
    private XConnectorEpoll connector;
    private final UnixSocketConfig socketConfig;

    public ALSAServerComponent(UnixSocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

    @Override
    public void start() {
        if (connector != null) return;
        connector = new XConnectorEpoll(socketConfig, new ALSAClientConnectionHandler(), new ALSARequestHandler());
        connector.setMultithreadedClients(true);
        connector.start();
    }

    @Override
    public void stop() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }
}
