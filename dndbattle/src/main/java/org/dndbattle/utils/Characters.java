/*
 * Copyright (C) 2018 Wouter
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dndbattle.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dndbattle.objects.ICharacter;
import org.dndbattle.view.comboboxes.ClassComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Characters extends AbstractObjectStorer<ICharacter> {

  private static final Logger log = LoggerFactory.getLogger(Characters.class);

  private final Map<Class<? extends ICharacter>, List<ICharacter>> CLASS_CHARACTER_MAP = new HashMap<>();
  private static final Characters INSTANCE = new Characters();

  private Characters() {
    super("Characters");
  }

  public static Characters getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean add(ICharacter character) {
    if (!canCreate(character)) {
      return false;
    }
    final List<ICharacter> characters = getCharacters(character.getClass());
    characters.add(character);
    Collections.sort(characters);
    store(character, true);
    return true;
  }

  @Override
  public void update(ICharacter character) {
    if (getFile(character).exists()) {
      store(character, false);
    }
  }

  @Override
  public List<ICharacter> getAll() {
    List<ICharacter> all = new ArrayList<>();
    for (Class<? extends ICharacter> clazz : ClassComboBox.getAllClasses()) {
      all.addAll(getCharacters(clazz));
    }
    return all;
  }

  public List<ICharacter> getCharacters(Class<? extends ICharacter> clazz) {
    if (!isInitialized()) {
      initialize();
    }
    log.debug("Returning list of characters of class [{}]", clazz.getSimpleName());
    return CLASS_CHARACTER_MAP.get(clazz);
  }

  @Override
  protected void initializeHook() {
    Class<? extends ICharacter>[] classes = ClassComboBox.getAllClasses();
    for (int i = 0; i < classes.length; i++) {
      Class<? extends ICharacter> clazz = classes[i];
      log.debug("Loading list of characters of class [{}]", clazz.getSimpleName());
      CLASS_CHARACTER_MAP.put(clazz, loadFromFiles(clazz, false));
      setProgress(Math.floorDiv((i + 1) * 100, classes.length));
    }
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
