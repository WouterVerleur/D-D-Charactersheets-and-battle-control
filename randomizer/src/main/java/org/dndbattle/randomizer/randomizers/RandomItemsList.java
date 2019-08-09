/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.randomizer.randomizers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RandomItemsList<T extends Comparable & Serializable> implements Serializable, Comparable<RandomItemsList<T>> {

  private final Random random = new Random();
  private String name;
  private List<T> items = new ArrayList<>();

  protected RandomItemsList() {
  }

  public RandomItemsList(String name) {
    this.name = name;
  }

  @Override
  public int compareTo(RandomItemsList<T> other) {
    return name.compareTo(other.getName());
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addItem(T item) {
    items.add(item);
    if (item instanceof Comparable) {
      Collections.sort(items);
    }
  }

  public void removeItem(T selectedValue) {
    items.remove(selectedValue);
  }

  @Override
  public String toString() {
    return getName();
  }

  @JsonIgnore
  protected T getRandomItem() {
    if (items.size() <= 0) {
      return null;
    }
    return items.get(random.nextInt(items.size()));
  }

}
