package com.example.fixplayground.handler;

import quickfix.FieldNotFound;
import quickfix.Message;

public interface FixMessageHandler {
    void handleMessage(Message message) throws FieldNotFound;
    Class<?> getMessageType();
}
