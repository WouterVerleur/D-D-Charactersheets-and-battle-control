/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public abstract class AbstractRemoteConnector {

    private static final Logger log = LoggerFactory.getLogger(AbstractRemoteConnector.class);

    public AbstractRemoteConnector() {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.debug("Shutting down");
                shutdownHook();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    log.error("Unable to join main thread.", e);
                }
            }
        });
        log.debug("Created shutdown hook for [{}]", this);
    }

    protected abstract void shutdownHook();
}
