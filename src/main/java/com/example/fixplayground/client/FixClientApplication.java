package com.example.fixplayground.client;

import com.example.fixplayground.handler.FixMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FixClientApplication implements Application {
    private final Map<Class<?>, FixMessageHandler> handlers;
    private SessionID sessionId;

    @Autowired
    public FixClientApplication(List<FixMessageHandler> fixMessageHandlers) {
        handlers = fixMessageHandlers.stream()
                .collect(Collectors.toMap(FixMessageHandler::getMessageType, handler -> handler));
    }

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon");
        this.sessionId = sessionId;
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info("toAdmin {}", message);
        log.info(messageToText(message, sessionId));
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin {}", message);
        log.info(messageToText(message, sessionId));
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        log.info("toApp {}", message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp {}", message);

        log.info(messageToText(message, sessionId));


        if (!handlers.containsKey(message.getClass())) {
            throw new UnsupportedOperationException(String.format("Unsupported handler %s", message.getClass().getName()));
        }
        try {
            handlers.get(message.getClass())
                    .handleMessage(message);
        } catch (FieldNotFound fnfEx) {
            log.error("Not found: {}", fnfEx.getMessage());
        }
    }

    private static String messageToText(Message message, SessionID sessionId) {
        var dd = Session.lookupSession(sessionId).getDataDictionary();
        var sb = new StringBuilder(String.format("Dictionary version=%s%n", dd.getFullVersion()));
        sb.append(String.format("MessageClass=%s%n", message.getClass().getName()));

        sb.append("---\nHeader:\n---\n");
        sectionToText(message.getHeader(), sb, dd);

        sb.append("---\nBody:\n---\n");
        sectionToText(message, sb, dd);

        sb.append("---\nTrailer:\n---\n");
        sectionToText(message.getTrailer(), sb, dd);

        return sb.toString();
    }

    private static void sectionToText(FieldMap section, StringBuilder sb, DataDictionary dd) {
        var i2 = section.iterator();
        while (i2.hasNext()) {
            Field<?> f = i2.next();
            sb.append(String.format("%s=%s%n", dd.getFieldName(f.getField()), f.getObject()));
        }

        Iterator<Integer> ig1 = section.groupKeyIterator();
        while (ig1.hasNext()) {
            List<Group> groups = section.getGroups(ig1.next());
            for (Group g : groups) {
                sb.append(String.format("[start group=%s]%n", dd.getFieldName(g.getFieldTag())));
                sectionToText(g, sb, dd);
                sb.append(String.format("[end group=%s]%n", dd.getFieldName(g.getFieldTag())));
            }
        }

    }


    public void send(Message message) throws SessionNotFound {
        log.info(messageToText(message, sessionId));
        Session.sendToTarget(message, sessionId);
    }


}
