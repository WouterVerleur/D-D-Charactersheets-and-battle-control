/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.wouter.dndbattle.objects.IUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Utilities extends AbstractObjectStorer<IUtility> {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    private static final Utilities INSTANCE = new Utilities();

    private List<IUtility> utilities = null;

    private Utilities() {
        super("Utilities");
    }

    public static Utilities getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(IUtility utility) {
        if (!canCreate(utility)) {
            return false;
        }
        getAll().add(utility);
        Collections.sort(getAll());
        store(utility, true);
        return true;
    }

    @Override
    public void update(IUtility utility) {
        if (getFile(utility).exists()) {
            store(utility, false);
        }
    }

    @Override
    public List<IUtility> getAll() {
        if (!isInitialized()) {
            initialize();
        }
        return utilities;
    }

    @Override
    protected void initializeHook() {
        utilities = loadFromFiles(IUtility.class);
    }

    @Override
    public void remove(IUtility utility) {
        store(utility, true);// to make sure any change does not undo this remove
        File file = getFile(utility);
        if (file.exists()) {
            file.delete();
            getAll().remove(utility);
            log.debug("Weapon [{}] has been deleted.", utility);
        }
    }
}
