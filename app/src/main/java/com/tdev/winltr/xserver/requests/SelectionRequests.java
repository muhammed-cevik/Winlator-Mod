package com.tdev.winltr.xserver.requests;

import static com.tdev.winltr.xserver.XClientRequestHandler.RESPONSE_CODE_SUCCESS;

import com.tdev.winltr.xconnector.XInputStream;
import com.tdev.winltr.xconnector.XOutputStream;
import com.tdev.winltr.xconnector.XStreamLock;
import com.tdev.winltr.xserver.Atom;
import com.tdev.winltr.xserver.Window;
import com.tdev.winltr.xserver.XClient;
import com.tdev.winltr.xserver.errors.BadAtom;
import com.tdev.winltr.xserver.errors.BadWindow;
import com.tdev.winltr.xserver.errors.XRequestError;

import java.io.IOException;

public abstract class SelectionRequests {
    public static void setSelectionOwner(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        int windowId = inputStream.readInt();
        int atom = inputStream.readInt();
        int timestamp = inputStream.readInt();

        Window owner = client.xServer.windowManager.getWindow(windowId);
        if (owner == null) throw new BadWindow(windowId);
        if (!Atom.isValid(atom)) throw new BadAtom(atom);

        client.xServer.selectionManager.setSelection(atom, owner, client, timestamp);
    }

    public static void getSelectionOwner(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        int atom = inputStream.readInt();
        if (!Atom.isValid(atom)) throw new BadAtom(atom);
        Window owner = client.xServer.selectionManager.getSelection(atom).owner;

        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte((byte)0);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(0);
            outputStream.writeInt(owner != null ? owner.id : 0);
            outputStream.writePad(20);
        }
    }
}
