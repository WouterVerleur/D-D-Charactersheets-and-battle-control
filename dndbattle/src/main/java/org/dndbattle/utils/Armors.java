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
import java.util.Collections;
import java.util.List;

import org.dndbattle.objects.IArmor;
import org.dndbattle.objects.impl.Armor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wouter
 */
public class Armors extends AbstractObjectStorer<IArmor> {

  private static final Logger log = LoggerFactory.getLogger(Armors.class);

  private static final Armors INSTANCE = new Armors();

  private List<IArmor> armors = null;

  private Armors() {
    super("Armors");
  }

  public static Armors getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean add(IArmor armor) {
    if (!canCreate(armor)) {
      log.debug("Unable to add new armor. Already exists.");
      return false;
    }
    getAll().add(armor);
    Collections.sort(getAll());
    store(armor, true);
    return true;
  }

  @Override
  public void update(IArmor armor) {
    if (getFile(armor).exists()) {
      store(armor, false);
    }
  }

  @Override
  public List<IArmor> getAll() {
    if (!isInitialized()) {
      initialize();
    }
    return armors;
  }

  @Override
  protected void initializeHook() {
    armors = loadFromFiles(Armor.class);
    Collections.sort(armors);
  }

  @Override
  public void remove(IArmor armor) {
    store(armor, true);// to make sure any change does not undo this remove
    File file = getFile(armor);
    if (file.exists()) {
      file.delete();
      getAll().remove(armor);
      log.debug("Armor [{}] has been deleted.", armor);
    }
  }
}
