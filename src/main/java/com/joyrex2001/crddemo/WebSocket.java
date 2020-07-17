package com.joyrex2001.crddemo;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.joyrex2001.crddemo.crd.BroadcastSpec;

import org.jboss.logging.Logger;

@ServerEndpoint(value = "/broadcast")
@ApplicationScoped
public class WebSocket {

    private static final Logger LOG = Logger.getLogger(WebSocket.class);

    CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    CopyOnWriteArraySet<BroadcastSpec> specs = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(final Session session) {
        sessions.add(session);
        send(session);
    }

    @OnClose
    public void onClose(final Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        sessions.remove(session);
        LOG.error("onError", throwable);
    }

    public void addBroadcast(final BroadcastSpec spec) {
        specs.add(spec);
        LOG.infof("broadcasting '%s'", spec.getMessage());
        broadcast();
    }

    public void deleteBroadcast(final BroadcastSpec spec) {
        specs.remove(spec);
        LOG.infof("removing '%s'", spec.getMessage());
        broadcast();
    }

    private void broadcast() {
        sessions.forEach(s -> send(s));
    }

    private void send(final Session session) {
        session.getAsyncRemote().sendObject(JsonbBuilder.create().toJson(specs), result -> {
        // session.getAsyncRemote().sendObject("some", result -> {
            if (result.getException() != null) {
                LOG.errorf("Unable to send message: %s", result.getException());
            }
        });
    }

}