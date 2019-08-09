/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.randomizer.randomizers;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wouter.randomizer.view.TimedRandomizerPanel;

public class TimedRandomizer extends Randomizer {

  private final Random random = new Random();

  private int minTime = 1;
  private int maxTime = 1;

  protected TimedRandomizer() {
  }

  public TimedRandomizer(String name) {
    super(name);
  }

  @Override
  protected synchronized void createPanel() {
    if (panel == null) {
      panel = new TimedRandomizerPanel(this);
    }
  }

  public int getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(int maxTime) {
    this.maxTime = maxTime;
  }

  public int getMinTime() {
    return minTime;
  }

  public void setMinTime(int minTime) {
    this.minTime = minTime;
  }

  @JsonIgnore
  public int getRandomTimeout() {
    if (maxTime > minTime) {
      return 60 * (minTime + random.nextInt(maxTime - minTime)) + random.nextInt(60);
    }
    return minTime;
  }
}
