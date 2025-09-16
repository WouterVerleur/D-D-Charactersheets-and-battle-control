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

import org.dndbattle.objects.ISpell;
import org.dndbattle.objects.impl.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Spells extends AbstractObjectStorer<ISpell> {

  private static final Logger log = LoggerFactory.getLogger(Weapons.class);

  private static final Spells INSTANCE = new Spells();

  private Map<String, ISpell> spells = null;

  private Spells() {
    super("Spells");
  }

  public static Spells getInstance() {
    return INSTANCE;
  }

  @Override
  public List<ISpell> getAll() {
    List<ISpell> returnVal = new ArrayList(getSpells().values());
    Collections.sort(returnVal);
    return returnVal;
  }

  @Override
  public boolean add(ISpell spell) {
    if (!canCreate(spell)) {
      return false;
    }
    getSpells().put(spell.toString(), spell);
    store(spell, true);
    return true;
  }

  @Override
  public void update(ISpell spell) {
    if (getFile(spell).exists()) {
      store(spell, false);
    }
  }

  @Override
  public void remove(ISpell spell) {
    store(spell, true);// to make sure any change does not undo this remove
    File file = getFile(spell);
    if (file.exists()) {
      file.delete();
      getSpells().remove(spell.toString());
      log.debug("Spell [{}] has been deleted.", spell);
    }
  }

  public ISpell getByString(String spellName) {
    return getSpells().get(spellName);
  }

  private Map<String, ISpell> getSpells() {
    if (!isInitialized()) {
      initialize();
    }
    return spells;
  }

  @Override
  protected void initializeHook() {
    spells = new HashMap<>();
    loadFromFiles(Spell.class).forEach((spell) -> {
      spells.put(spell.toString(), spell);
    });
  }
}
