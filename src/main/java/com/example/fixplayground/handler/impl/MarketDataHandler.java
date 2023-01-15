package com.example.fixplayground.handler.impl;

import com.example.fixplayground.handler.FixMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntryType;
import quickfix.field.NoMDEntries;
import quickfix.fix42.MarketDataSnapshotFullRefresh;

@Component
@Slf4j
public class MarketDataHandler implements FixMessageHandler {
    @Override
    public void handleMessage(Message message) throws FieldNotFound {
        log.info("Got message {}", message);
        MarketDataSnapshotFullRefresh mdsfrMessage = (MarketDataSnapshotFullRefresh) message;
        var groups = mdsfrMessage.getGroups(NoMDEntries.FIELD);
        for (Group group : groups) {
            log.info("getMDEntryType={}",group.getChar(MDEntryType.FIELD));
            log.info("getMDEntryPx={}",group.getDouble(MDEntryPx.FIELD));
        }
    }

    @Override
    public Class<?> getMessageType() {
        return MarketDataSnapshotFullRefresh.class;
    }
}
