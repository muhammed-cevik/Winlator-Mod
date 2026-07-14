package com.tdev.winltr.xserver.events;

import com.tdev.winltr.xserver.Bitmask;
import com.tdev.winltr.xserver.Window;

public class EnterNotify extends PointerWindowEvent {
    public EnterNotify(Detail detail, Window root, Window event, Window child, short rootX, short rootY, short eventX, short eventY, Bitmask state, Mode mode, boolean sameScreenAndFocus) {
        super(7, detail, root, event, child, rootX, rootY, eventX, eventY, state, mode, sameScreenAndFocus);
    }
}
