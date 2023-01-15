package com.example.fixplayground.controller;

import com.example.fixplayground.client.FixClientApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix42.MarketDataRequest;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final FixClientApplication fixClientApplication;

    @GetMapping("send")
    public void send() throws SessionNotFound {

        var msg = new MarketDataRequest(new MDReqID("asd-1"),
                new SubscriptionRequestType('0'),
                new MarketDepth(0));
        var g = new MarketDataRequest.NoRelatedSym();
        g.set(new Symbol("AAPL"));
        msg.addGroup(g);

        var g2 = new MarketDataRequest.NoMDEntryTypes();
        g2.set(new MDEntryType(MDEntryType.BID));
        msg.addGroup(g2);

        fixClientApplication.send(msg);
    }
}
