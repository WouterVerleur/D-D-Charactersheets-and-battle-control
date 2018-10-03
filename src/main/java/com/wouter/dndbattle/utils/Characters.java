/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.dndbattle.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wouter.dndbattle.objects.ICharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Characters extends ObjectStorer<ICharacter> {

    private static final Logger log = LoggerFactory.getLogger(Characters.class);

    private final Map<Class<? extends ICharacter>, List<ICharacter>> CLASS_CHARACTER_MAP = new HashMap<>();
    private static final Characters INSTANCE = new Characters();

    private Characters() {
    }

    public static Characters getInstance() {
        return INSTANCE;
    }

    public boolean addCharacter(ICharacter character) {
        if (!canCreate(character)) {
            return false;
        }
        final List<ICharacter> characters = getCharacters(character.getClass());
        characters.add(character);
        Collections.sort(characters);
        store(character, true);
        return true;
    }

    public void updateCharacter(ICharacter character) {
        if (getFile(character).exists()) {
            store(character, false);
        }
    }

    public List<ICharacter> getCharacters(Class<? extends ICharacter> clazz) {
        if (!CLASS_CHARACTER_MAP.containsKey(clazz)) {
            log.debug("Loading list of characters of class [{}]", clazz.getSimpleName());
            CLASS_CHARACTER_MAP.put(clazz, loadFromFiles(clazz));
        }
        log.debug("Returning list of characters of class [{}]", clazz.getSimpleName());
        return CLASS_CHARACTER_MAP.get(clazz);
    }

    @Override
    public void remove(ICharacter preset) {
        store(preset, true);// to make sure any change does not undo this remove
        File file = getFile(preset);
        if (file.exists()) {
            file.delete();
            getCharacters(preset.getClass()).remove(preset);
            log.debug("Character [{}] has been deleted.", preset);
        }
    }
}
