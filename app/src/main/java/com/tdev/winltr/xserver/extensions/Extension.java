package com.tdev.winltr.xserver.extensions;

import com.tdev.winltr.xconnector.XInputStream;
import com.tdev.winltr.xconnector.XOutputStream;
import com.tdev.winltr.xserver.XClient;
import com.tdev.winltr.xserver.errors.XRequestError;

import java.io.IOException;

public interface Extension {
    String getName();

    byte getMajorOpcode();

    byte getFirstErrorId();

    byte getFirstEventId();

    void handleRequest(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError;
}
