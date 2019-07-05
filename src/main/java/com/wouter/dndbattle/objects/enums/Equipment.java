/*
 * Copyright (C) 2019 wverl
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
package com.wouter.dndbattle.objects.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wouter.dndbattle.objects.IEquipment;
import com.wouter.dndbattle.objects.IInventoryItem;
import com.wouter.dndbattle.objects.ISaveableClass;
import com.wouter.dndbattle.utils.Armors;
import com.wouter.dndbattle.utils.Utilities;
import com.wouter.dndbattle.utils.Weapons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wverl
 */
public class Equipment implements IEquipment {

    private static final Logger log = LoggerFactory.getLogger(Equipment.class);

    private static final Armors ARMORS = Armors.getInstance();
    private static final Weapons WEAPONS = Weapons.getInstance();
    private static final Utilities UTILITIES = Utilities.getInstance();

    private static final String TOSTRING_MULTIPLE_FORMAT = "%d× %s";
    private static final String INVENTORY_ITEM_FORMAT = "%s.%s";

    private int amount;
    private IInventoryItem inventoryItem;

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @JsonIgnore
    @Override
    public IInventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(IInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public String getInventoryItemString() {
        return String.format(INVENTORY_ITEM_FORMAT, inventoryItem.getName(), inventoryItem.getClass().getName());
    }

    public void setInventoryItemString(String inventoryItemString) {
        String[] split = inventoryItemString.split("\\.");
        switch (split[1]) {
            case "Armor":
                inventoryItem = ARMORS.getForString(split[0]);
                break;
            case "Weapon":
                inventoryItem = WEAPONS.getForString(split[0]);
                break;
            case "Utility":
                inventoryItem = UTILITIES.getForString(split[0]);
                break;
            default:
                log.error("Unable to parse [{}], [{}] is unknown", inventoryItemString, split[1]);
                break;
        }
    }

    @Override
    public float getTotalWeight() {
        return inventoryItem.getWeight() * amount;
    }

    @Override
    public String toString() {
        if (amount == 1) {
            return inventoryItem.getName();
        }
        return String.format(TOSTRING_MULTIPLE_FORMAT, amount, inventoryItem.getName());
    }

    @Override
    public int compareTo(ISaveableClass other) {
        if (other instanceof IEquipment) {
            int compare = inventoryItem.compareTo(((IEquipment) other).getInventoryItem());
            if (compare == 0) {
                compare = amount - ((IEquipment) other).getAmount();
            }
            return compare;
        }
        return IEquipment.super.compareTo(other);
    }
}
