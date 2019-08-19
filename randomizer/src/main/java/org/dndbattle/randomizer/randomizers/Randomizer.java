/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dndbattle.randomizer.randomizers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.dndbattle.randomizer.view.AbstractRandomizerPanel;
import org.dndbattle.randomizer.view.OnetimeRandomizerPanel;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Randomizer implements Serializable {

  private static final String ITEM_STRING_FORMAT = "%s: %s";

  private String name;
  protected AbstractRandomizerPanel panel;
  private List<RandomItemsList<String>> itemLists = new ArrayList<>();

  protected Randomizer() {
  }

  public Randomizer(String name) {
    this.name = name;
  }

  public void addItemList(RandomItemsList<String> randomItemsList) {
    itemLists.add(randomItemsList);
    resortList();
  }

  public void removeItemList(RandomItemsList<String> randomItemsList) {
    itemLists.remove(randomItemsList);
  }

  public void resortList() {
    Collections.sort(itemLists);
  }

  public List<RandomItemsList<String>> getItemLists() {
    return itemLists;
  }

  public void setItemLists(List<RandomItemsList<String>> itemLists) {
    this.itemLists = itemLists;
    resortList();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  protected synchronized void createPanel() {
    if (panel == null) {
      panel = new OnetimeRandomizerPanel(this);
    }
  }

  @JsonIgnore
  public AbstractRandomizerPanel getPanel() {
    if (panel == null) {
      createPanel();
    }
    return panel;
  }

  @JsonIgnore
  public List<String> getRandomItemStrings() {
    List<String> items = new ArrayList<>();
    itemLists.forEach((itemList) -> {
      items.add(String.format(ITEM_STRING_FORMAT, itemList.getName(), itemList.getRandomItem()));
    });
    return items;
  }

  @JsonIgnore
  public List<String> getRandomItems() {
    List<String> items = new ArrayList<>();
    itemLists.forEach((itemList) -> {
      items.add(itemList.getRandomItem());
    });
    return items;
  }
}
