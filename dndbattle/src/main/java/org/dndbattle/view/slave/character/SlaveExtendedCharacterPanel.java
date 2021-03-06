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
package org.dndbattle.view.slave.character;

import org.dndbattle.objects.IExtendedCharacter;

/**
 *
 * @author Wouter
 */
public class SlaveExtendedCharacterPanel extends javax.swing.JPanel {

    private final IExtendedCharacter character;

    public SlaveExtendedCharacterPanel(IExtendedCharacter character) {
        this.character = character;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        spCharacterFields = new javax.swing.JScrollPane();
        pCharacterFields = new javax.swing.JPanel();
        lPlayerName = new javax.swing.JLabel();
        lPlayerNameValue = new javax.swing.JLabel();
        lRace = new javax.swing.JLabel();
        lRaceValue = new javax.swing.JLabel();
        lBackground = new javax.swing.JLabel();
        lBackgroundValue = new javax.swing.JLabel();
        lAlignment = new javax.swing.JLabel();
        lAlignmentValue = new javax.swing.JLabel();
        lExperiencePoints = new javax.swing.JLabel();
        lExperiencePointsValue = new javax.swing.JLabel();
        sepChar1 = new javax.swing.JSeparator();
        lAge = new javax.swing.JLabel();
        lAgeValue = new javax.swing.JLabel();
        lHeight = new javax.swing.JLabel();
        lHeightValue = new javax.swing.JLabel();
        lWeight = new javax.swing.JLabel();
        lWeightValue = new javax.swing.JLabel();
        lEyes = new javax.swing.JLabel();
        lEyesValue = new javax.swing.JLabel();
        lSkin = new javax.swing.JLabel();
        lSkinValue = new javax.swing.JLabel();
        lHair = new javax.swing.JLabel();
        lHeirValue = new javax.swing.JLabel();
        sepChar2 = new javax.swing.JSeparator();
        spProficiencies = new javax.swing.JScrollPane();
        taProficiencies = new javax.swing.JTextArea();
        spLanguages = new javax.swing.JScrollPane();
        taLanguages = new javax.swing.JTextArea();
        spPersonalityTraits = new javax.swing.JScrollPane();
        taPersonalityTraits = new javax.swing.JTextArea();
        spIdeals = new javax.swing.JScrollPane();
        taIdeals = new javax.swing.JTextArea();
        spBonds = new javax.swing.JScrollPane();
        taBonds = new javax.swing.JTextArea();
        spFlaws = new javax.swing.JScrollPane();
        taFlaws = new javax.swing.JTextArea();
        spEquipment = new javax.swing.JScrollPane();
        taEquipment = new javax.swing.JTextArea();
        spTreasure = new javax.swing.JScrollPane();
        taTreasure = new javax.swing.JTextArea();
        spAlliesAndOrganisations = new javax.swing.JScrollPane();
        taAlliesAndOrganisations = new javax.swing.JTextArea();
        spBackstory = new javax.swing.JScrollPane();
        taBackstory = new javax.swing.JTextArea();
        sepFeatures = new javax.swing.JSeparator();
        lFeatures = new javax.swing.JLabel();
        spFeatures = new javax.swing.JScrollPane();
        taFeatures = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());

        spCharacterFields.setBorder(null);

        pCharacterFields.setLayout(new java.awt.GridBagLayout());

        lPlayerName.setText("Player Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lPlayerName, gridBagConstraints);

        lPlayerNameValue.setText(character.getPlayerName());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lPlayerNameValue, gridBagConstraints);

        lRace.setText("Race");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lRace, gridBagConstraints);

        lRaceValue.setText(character.getRace());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lRaceValue, gridBagConstraints);

        lBackground.setText("Background");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lBackground, gridBagConstraints);

        lBackgroundValue.setText(character.getBackground());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lBackgroundValue, gridBagConstraints);

        lAlignment.setText("Alignment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lAlignment, gridBagConstraints);

        lAlignmentValue.setText(character.getAlignment());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lAlignmentValue, gridBagConstraints);

        lExperiencePoints.setText("Experience Points");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lExperiencePoints, gridBagConstraints);

        lExperiencePointsValue.setText(Integer.toString(character.getExperiencePoints()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lExperiencePointsValue, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(sepChar1, gridBagConstraints);

        lAge.setText("Age");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lAge, gridBagConstraints);

        lAgeValue.setText(Integer.toString(character.getAge()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lAgeValue, gridBagConstraints);

        lHeight.setText("Height");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lHeight, gridBagConstraints);

        lHeightValue.setText(character.getHeight());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lHeightValue, gridBagConstraints);

        lWeight.setText("Weight");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lWeight, gridBagConstraints);

        lWeightValue.setText(character.getWeight());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lWeightValue, gridBagConstraints);

        lEyes.setText("Eyes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lEyes, gridBagConstraints);

        lEyesValue.setText(character.getEyes());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lEyesValue, gridBagConstraints);

        lSkin.setText("Skin");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lSkin, gridBagConstraints);

        lSkinValue.setText(character.getSkin());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lSkinValue, gridBagConstraints);

        lHair.setText("Hair");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(lHair, gridBagConstraints);

        lHeirValue.setText(character.getHair());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(lHeirValue, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(sepChar2, gridBagConstraints);

        spProficiencies.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Other proficiencies", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taProficiencies.setColumns(20);
        taProficiencies.setLineWrap(true);
        taProficiencies.setText(character.getProficiencies());
        taProficiencies.setWrapStyleWord(true);
        spProficiencies.setViewportView(taProficiencies);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(spProficiencies, gridBagConstraints);

        spLanguages.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Languages", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taLanguages.setColumns(20);
        taLanguages.setLineWrap(true);
        taLanguages.setText(character.getLanguages());
        taLanguages.setWrapStyleWord(true);
        spLanguages.setViewportView(taLanguages);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(spLanguages, gridBagConstraints);

        spPersonalityTraits.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Personality traits", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taPersonalityTraits.setColumns(20);
        taPersonalityTraits.setLineWrap(true);
        taPersonalityTraits.setText(character.getPersonalityTraits());
        taPersonalityTraits.setWrapStyleWord(true);
        spPersonalityTraits.setViewportView(taPersonalityTraits);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(spPersonalityTraits, gridBagConstraints);

        spIdeals.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Ideals", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taIdeals.setColumns(20);
        taIdeals.setLineWrap(true);
        taIdeals.setText(character.getIdeals());
        taIdeals.setWrapStyleWord(true);
        spIdeals.setViewportView(taIdeals);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(spIdeals, gridBagConstraints);

        spBonds.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Bonds", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taBonds.setColumns(20);
        taBonds.setLineWrap(true);
        taBonds.setText(character.getBonds());
        taBonds.setWrapStyleWord(true);
        spBonds.setViewportView(taBonds);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(spBonds, gridBagConstraints);

        spFlaws.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Flaws", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taFlaws.setColumns(20);
        taFlaws.setLineWrap(true);
        taFlaws.setText(character.getFlaws());
        taFlaws.setWrapStyleWord(true);
        spFlaws.setViewportView(taFlaws);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(spFlaws, gridBagConstraints);

        spEquipment.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Equipment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        spEquipment.setToolTipText("");

        taEquipment.setColumns(20);
        taEquipment.setLineWrap(true);
        taEquipment.setText(character.getEquipment());
        taEquipment.setWrapStyleWord(true);
        spEquipment.setViewportView(taEquipment);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        pCharacterFields.add(spEquipment, gridBagConstraints);

        spTreasure.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Treasure", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taTreasure.setColumns(20);
        taTreasure.setLineWrap(true);
        taTreasure.setText(character.getTreasure());
        taTreasure.setWrapStyleWord(true);
        spTreasure.setViewportView(taTreasure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        pCharacterFields.add(spTreasure, gridBagConstraints);

        spAlliesAndOrganisations.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Allies & organisations", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        taAlliesAndOrganisations.setColumns(20);
        taAlliesAndOrganisations.setLineWrap(true);
        taAlliesAndOrganisations.setText(character.getAliesAndOrganizations());
        taAlliesAndOrganisations.setWrapStyleWord(true);
        spAlliesAndOrganisations.setViewportView(taAlliesAndOrganisations);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        pCharacterFields.add(spAlliesAndOrganisations, gridBagConstraints);

        spBackstory.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Backstory", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        spBackstory.setToolTipText("");

        taBackstory.setColumns(20);
        taBackstory.setLineWrap(true);
        taBackstory.setText(character.getBackstory());
        taBackstory.setWrapStyleWord(true);
        spBackstory.setViewportView(taBackstory);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pCharacterFields.add(spBackstory, gridBagConstraints);

        spCharacterFields.setViewportView(pCharacterFields);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.75;
        gridBagConstraints.weighty = 1.0;
        add(spCharacterFields, gridBagConstraints);

        sepFeatures.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 326;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(sepFeatures, gridBagConstraints);

        lFeatures.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lFeatures.setText("Features & Traits");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(lFeatures, gridBagConstraints);

        taFeatures.setEditable(false);
        taFeatures.setColumns(20);
        taFeatures.setLineWrap(true);
        taFeatures.setRows(5);
        taFeatures.setText(character.getFeaturesAndTraits());
        taFeatures.setWrapStyleWord(true);
        spFeatures.setViewportView(taFeatures);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        add(spFeatures, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lAge;
    private javax.swing.JLabel lAgeValue;
    private javax.swing.JLabel lAlignment;
    private javax.swing.JLabel lAlignmentValue;
    private javax.swing.JLabel lBackground;
    private javax.swing.JLabel lBackgroundValue;
    private javax.swing.JLabel lExperiencePoints;
    private javax.swing.JLabel lExperiencePointsValue;
    private javax.swing.JLabel lEyes;
    private javax.swing.JLabel lEyesValue;
    private javax.swing.JLabel lFeatures;
    private javax.swing.JLabel lHair;
    private javax.swing.JLabel lHeight;
    private javax.swing.JLabel lHeightValue;
    private javax.swing.JLabel lHeirValue;
    private javax.swing.JLabel lPlayerName;
    private javax.swing.JLabel lPlayerNameValue;
    private javax.swing.JLabel lRace;
    private javax.swing.JLabel lRaceValue;
    private javax.swing.JLabel lSkin;
    private javax.swing.JLabel lSkinValue;
    private javax.swing.JLabel lWeight;
    private javax.swing.JLabel lWeightValue;
    private javax.swing.JPanel pCharacterFields;
    private javax.swing.JSeparator sepChar1;
    private javax.swing.JSeparator sepChar2;
    private javax.swing.JSeparator sepFeatures;
    private javax.swing.JScrollPane spAlliesAndOrganisations;
    private javax.swing.JScrollPane spBackstory;
    private javax.swing.JScrollPane spBonds;
    private javax.swing.JScrollPane spCharacterFields;
    private javax.swing.JScrollPane spEquipment;
    private javax.swing.JScrollPane spFeatures;
    private javax.swing.JScrollPane spFlaws;
    private javax.swing.JScrollPane spIdeals;
    private javax.swing.JScrollPane spLanguages;
    private javax.swing.JScrollPane spPersonalityTraits;
    private javax.swing.JScrollPane spProficiencies;
    private javax.swing.JScrollPane spTreasure;
    private javax.swing.JTextArea taAlliesAndOrganisations;
    private javax.swing.JTextArea taBackstory;
    private javax.swing.JTextArea taBonds;
    private javax.swing.JTextArea taEquipment;
    private javax.swing.JTextArea taFeatures;
    private javax.swing.JTextArea taFlaws;
    private javax.swing.JTextArea taIdeals;
    private javax.swing.JTextArea taLanguages;
    private javax.swing.JTextArea taPersonalityTraits;
    private javax.swing.JTextArea taProficiencies;
    private javax.swing.JTextArea taTreasure;
    // End of variables declaration//GEN-END:variables
}
