package com.joyrex2001.crddemo;

import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.joyrex2001.crddemo.crd.Broadcast;

@ApplicationScoped
public class EventHandler implements ResourceEventHandler<Broadcast> {

    @Inject
    WebSocket websocket;

    @Override
    public void onAdd(final Broadcast broadcast) {
        websocket.addBroadcast(broadcast.getSpec());
    }

    @Override
    public void onUpdate(final Broadcast oldBroadcast, final Broadcast newBroadcast) {
        if(!oldBroadcast.getMetadata().getResourceVersion().equals(newBroadcast.getMetadata().getResourceVersion())) {
            websocket.deleteBroadcast(oldBroadcast.getSpec());
            websocket.addBroadcast(newBroadcast.getSpec());    
        }
    }

    @Override
    public void onDelete(final Broadcast broadcast, final boolean deletedFinalStateUnknown) {
        websocket.deleteBroadcast(broadcast.getSpec());
    }
 
}
