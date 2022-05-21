package com.vet24.models.annotation;


import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;


public class EventListenerIntegrator implements Integrator {

    public static final EventListenerIntegrator INSTANCE = new EventListenerIntegrator();

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        eventListenerRegistry.appendListeners(
                EventType.MERGE,
                MergeEventListenerImpl.INSTANCE
        );
        eventListenerRegistry.appendListeners(
                EventType.PERSIST,
                PersistEventListenerImpl.INSTANCE
        );
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}