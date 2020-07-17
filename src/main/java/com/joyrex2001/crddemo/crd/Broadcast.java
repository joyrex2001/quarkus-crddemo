package com.joyrex2001.crddemo.crd;

import io.fabric8.kubernetes.client.CustomResource;

public class Broadcast extends CustomResource {

    private static final long serialVersionUID = 1L;

    private BroadcastSpec spec;

    public BroadcastSpec getSpec() {
        return spec;
    }

    public void setSpec(BroadcastSpec spec) {
        this.spec = spec;
    }
}