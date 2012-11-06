package org.vaadin.sasha.videochat.server.persistence;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class VideoChatEMF {
    
    EntityManagerFactory factory =  Persistence.createEntityManagerFactory("video_chat");
    
    public EntityManagerFactory getFactory() {
        return factory;
    }
    
}
