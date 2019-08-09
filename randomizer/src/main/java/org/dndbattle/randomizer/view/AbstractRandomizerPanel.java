/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wouter.randomizer.view;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wouter.randomizer.randomizers.Randomizer;

public abstract class AbstractRandomizerPanel<R extends Randomizer> extends JPanel {

  private final R randomizer;

  public AbstractRandomizerPanel(R randomizer) {
    this.randomizer = randomizer;
  }

  public R getRandomizer() {
    return randomizer;
  }

  protected void setOutput(JLabel label) {
    List<String> items = randomizer.getRandomItemStrings();
    StringBuilder builder = new StringBuilder("<html><body>Selected values:");
    items.forEach((item) -> {
      builder.append("<br/>").append(item);
    });
    builder.append("</body></html>");
    label.setText(builder.toString());
  }
}
