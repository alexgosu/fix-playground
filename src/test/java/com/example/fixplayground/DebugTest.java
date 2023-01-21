package com.example.fixplayground;

import com.example.fixplayground.client.FixClientApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import quickfix.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


//@SpringBootTest
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

        DataDictionary dd = new DataDictionary("FIX50.xml");
        var factory = new DefaultMessageFactory();
        String s = "8=FIX.4.2\u00019=71\u000135=A\u000134=1\u000149=BANZAI\u000152=20230117-15:24:44.365\u000156=EXEC\u000198=0\u0001108=30\u0001141=Y\u000110=005\u0001";
        var s2 = getResourceFileAsString("messages/Logon.fix");
        var c = new String( getClass().getResourceAsStream("/messages/Logon.fix").readAllBytes());
        var c2 = new String( getClass().getClassLoader().getResourceAsStream("messages/MarketDataSnapshotFullRefresh.fix").readAllBytes());
        var message = MessageUtils.parse(factory, dd, c2);
        System.out.println("test");
    }

    public static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (var is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (var isr = new InputStreamReader(is);
                 var reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
