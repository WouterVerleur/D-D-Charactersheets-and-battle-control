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

import org.dndbattle.objects.IUtility;
import org.dndbattle.objects.impl.Utility;
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
    utilities = loadFromFiles(Utility.class);
    Collections.sort(utilities);
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
