/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.network.gui.dialogs.neuron;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.simbrain.network.core.Neuron;
import org.simbrain.network.gui.NetworkUtils;
import org.simbrain.util.DropDownTriangle;

/**
 * 
 * @author ztosi
 * @author jyoshimi
 * 
 */
public class BasicNeuronInfoPanel extends JPanel {

	/** Null string. */
	public static final String NULL_STRING = "...";

	/** Activation field. */
	private JTextField tfActivation = new JTextField();

	/** The neuron Id. */
	private JLabel idLabel = new JLabel();

	/**
	 * Displays More/Less depending on whether or not extra data panel is
	 * displayed.
	 */
	private JLabel detailLabel = new JLabel();

	/**
	 * The extra data panel. Includes: increment, upper bound, lower bound, and
	 * priority.
	 */
	private ExtendedNeuronInfoPanel extraDataPanel;

	/** The neurons being modified. */
	private ArrayList<Neuron> neuronList = new ArrayList<Neuron>();

	/**
	 * A triangle that switches between an up (left) and a down state Used for
	 * showing/hiding extra neuron data.
	 */
	private DropDownTriangle detailTriangle = new DropDownTriangle(
			DropDownTriangle.LEFT, false);

	/**
	 * @param selectedNeurons
	 *            the pnode_neurons being adjusted
	 */
	public BasicNeuronInfoPanel(final Collection<Neuron> neuronList) {
		this.neuronList = (ArrayList<Neuron>) neuronList;
		initializeLayout();
		fillFieldValues();
		addListeners();
	}

	/**
	 * Initialize the basic info panel (generic neuron parameters)
	 * 
	 * @return the basic info panel
	 */
	private void initializeLayout() {

		setLayout(new BorderLayout());

		JPanel basicsPanel = new JPanel(new GridBagLayout());
		basicsPanel
				.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.8;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		basicsPanel.add(new JLabel("Neuron Id:"), gbc);

		gbc.gridwidth = 2;
		gbc.gridx = 1;
		basicsPanel.add(idLabel, gbc);

		gbc.weightx = 0.8;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		basicsPanel.add(new JLabel("Activation:"), gbc);

		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 3, 0, 0);
		gbc.gridwidth = 2;
		gbc.weightx = 0.2;
		gbc.gridx = 1;
		basicsPanel.add(tfActivation, gbc);

		gbc.gridwidth = 1;
		int lgap = detailTriangle.isDown() ? 5 : 0;
		gbc.insets = new Insets(10, 5, lgap, 5);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.2;
		String details = detailTriangle.isDown() ? "Less" : "More";
		detailLabel.setText(details);
		basicsPanel.add(detailLabel, gbc);
		gbc.weightx = 0.0;
		gbc.gridx = 2;
		basicsPanel.add(detailTriangle, gbc);

		this.add(basicsPanel, BorderLayout.NORTH);

		extraDataPanel = new ExtendedNeuronInfoPanel(neuronList);

		extraDataPanel.setVisible(detailTriangle.isDown());

		this.add(extraDataPanel, BorderLayout.SOUTH);

		TitledBorder tb = BorderFactory.createTitledBorder("Basic Data");
		this.setBorder(tb);

	}

	/**
	 * Called Externally to repaint this panel based on whether or not extra
	 * data is displayed.
	 */
	public void repaintPanel() {
		extraDataPanel.setVisible(detailTriangle.isDown());
		String details = detailTriangle.isDown() ? "Less" : "More";
		detailLabel.setText(details);
		repaint();
	}

	/**
	 * A method for adding all internal listeners.
	 */
	private void addListeners() {

		// Add a listener to display/hide extra editable neuron data
		detailTriangle.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Repaint to show/hide extra data
				extraDataPanel.setVisible(detailTriangle.isDown());
				String details =
						detailTriangle.isDown() ? "Less" : "More";
				detailLabel.setText(details);
				// Alert the panel/dialog/frame this is embedded in to
				// resize itself accordingly
				firePropertyChange("Extra Data",
						!detailTriangle.isDown(), detailTriangle.isDown());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
	}

	/**
	 * Set the initial values of dialog components.
	 */
	public void fillFieldValues() {

		Neuron neuronRef = neuronList.get(0);
		if (neuronList.size() == 1) {
			idLabel.setText(neuronRef.getId());
		} else {
			idLabel.setText(NULL_STRING);
		}

		// (Below) Handle consistency of multiple selections

		// Handle Activation
		if (!NetworkUtils.isConsistent(neuronList, Neuron.class,
				"getActivation"))
			tfActivation.setText(NULL_STRING);
		else
			tfActivation.setText(Double.toString(neuronRef
					.getActivation()));

	}

	/**
     * 
     */
	public void commitChanges() {
		for (int i = 0; i < neuronList.size(); i++) {

			Neuron neuronRef = neuronList.get(i);

			// Activation
			if (!tfActivation.getText().equals(NULL_STRING))
				neuronRef.setActivation(Double.parseDouble(tfActivation
						.getText()));

		}

		extraDataPanel.commitChanges();

	}

	/**
	 * 
	 * @return
	 */
	public DropDownTriangle getDetailTriangle() {
		return detailTriangle;
	}

}