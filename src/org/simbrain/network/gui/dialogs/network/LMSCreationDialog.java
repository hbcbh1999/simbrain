/*
 * Part of Simbrain--a java-based neural network kit Copyright (C) 2005,2007 The
 * Authors. See http://www.simbrain.net/credits This program is free software;
 * you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place
 * - Suite 330, Boston, MA 02111-1307, USA.
 */
package org.simbrain.network.gui.dialogs.network;

import java.awt.geom.Point2D;

import javax.swing.JTextField;

import org.simbrain.network.subnetworks.LMSNetwork;
import org.simbrain.network.gui.NetworkPanel;
import org.simbrain.util.LabelledItemPanel;
import org.simbrain.util.StandardDialog;

/**
 * <b>LMSDialog</b> is a dialog box for creating an LMS network
 */
public class LMSCreationDialog extends StandardDialog {

    private JTextField numInputs = new JTextField();
    private JTextField numOutputs = new JTextField();

    /** Network panel. */
    private NetworkPanel networkPanel;

    /**
     * This method is the default constructor.
     * 
     * @param np Network panel
     */
    public LMSCreationDialog(final NetworkPanel np) {
        networkPanel = np;
        init();
    }

    /**
     * This method initialises the components on the panel.
     */
    private void init() {
        // Initialize Dialog
        setTitle("New LMS Network");

        fillFieldValues();

        LabelledItemPanel panel = new LabelledItemPanel();
        panel.addItem("Number of inputs: ", numInputs);
        panel.addItem("Number of outputs: ", numOutputs);
        setContentPane(panel);
    }

    /**
     * Populate fields with current data.
     */
    private void fillFieldValues() {
        numInputs.setText("5");
        numOutputs.setText("5");
    }

    /**
     * Called when dialog closes.
     */
    protected void closeDialogOk() {

        // Get last clicked position in the panel
        Point2D lastClicked = networkPanel.getLastClickedPosition();

        // Create the layered network
        networkPanel.getRootNetwork().addGroup(
                new LMSNetwork(networkPanel.getRootNetwork(), Integer
                        .parseInt(numInputs.getText()), Integer
                        .parseInt(numOutputs.getText()), lastClicked));

        networkPanel.repaint();
        super.closeDialogOk();
    }
}