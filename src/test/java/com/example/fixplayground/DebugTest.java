package com.example.fixplayground;

import com.example.fixplayground.client.FixClientApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.UnsupportedMessageType;


@SpringBootTest
class DebugTest {

    @Autowired
    FixClientApplication fixClientApplication;

    @Test
    @SneakyThrows
    void debug() throws UnsupportedMessageType, IncorrectTagValue, FieldNotFound, IncorrectDataFormat {
//        var msg = new MarketDataSnapshotFullRefresh();
//        fixClientApplication.fromApp(msg, null);
//
//        var msg2 = new MarketDataIncrementalRefresh();
//        fixClientApplication.fromApp(msg2, null);
    }
}
