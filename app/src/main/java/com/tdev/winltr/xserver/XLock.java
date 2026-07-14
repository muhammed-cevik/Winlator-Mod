package com.tdev.winltr.xserver;

public interface XLock extends AutoCloseable {
    @Override
    void close();
}
