package com.joyrex2001.crddemo;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.joyrex2001.crddemo.crd.Broadcast;
import com.joyrex2001.crddemo.crd.BroadcastList;

@ApplicationScoped
public class Main {

    @Inject
    KubernetesClient client;

    @Inject
    ResourceEventHandler<Broadcast> handler;
    
    void onStart(@Observes StartupEvent ev) {

        final CustomResourceDefinitionContext crdContext = new CustomResourceDefinitionContext.Builder()
            .withVersion("v1")
            .withScope("Namespaced")
            .withGroup("crddemo.joyrex2001.com")
            .withPlural("broadcasts")
            .build();

        final SharedInformerFactory sharedInformerFactory = client.informers();
        final SharedIndexInformer<Broadcast> broadcastInformer = sharedInformerFactory.sharedIndexInformerForCustomResource(crdContext, Broadcast.class, BroadcastList.class, 1 * 60 * 1000);

        broadcastInformer.addEventHandler(handler);
        sharedInformerFactory.startAllRegisteredInformers();    
    }

}