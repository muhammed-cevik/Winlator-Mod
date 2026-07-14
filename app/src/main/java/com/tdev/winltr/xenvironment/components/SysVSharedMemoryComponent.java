package com.tdev.winltr.xenvironment.components;

import com.tdev.winltr.sysvshm.SysVSHMConnectionHandler;
import com.tdev.winltr.sysvshm.SysVSHMRequestHandler;
import com.tdev.winltr.sysvshm.SysVSharedMemory;
import com.tdev.winltr.xconnector.UnixSocketConfig;
import com.tdev.winltr.xconnector.XConnectorEpoll;
import com.tdev.winltr.xenvironment.EnvironmentComponent;
import com.tdev.winltr.xserver.SHMSegmentManager;
import com.tdev.winltr.xserver.XServer;

public class SysVSharedMemoryComponent extends EnvironmentComponent {
    private XConnectorEpoll connector;
    public final UnixSocketConfig socketConfig;
    private SysVSharedMemory sysVSharedMemory;
    private final XServer xServer;

    public SysVSharedMemoryComponent(XServer xServer, UnixSocketConfig socketConfig) {
        this.xServer = xServer;
        this.socketConfig = socketConfig;
    }

    @Override
    public void start() {
        if (connector != null) return;
        sysVSharedMemory = new SysVSharedMemory();
        connector = new XConnectorEpoll(socketConfig, new SysVSHMConnectionHandler(sysVSharedMemory), new SysVSHMRequestHandler());
        connector.start();

        xServer.setSHMSegmentManager(new SHMSegmentManager(sysVSharedMemory));
    }

    @Override
    public void stop() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }

        sysVSharedMemory.deleteAll();
    }
}
