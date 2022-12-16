package edu.stanford.protege.sbuciu.ui;

import org.protege.editor.core.ui.util.JOptionPaneEx;
import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.OWLEditorKit;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class ExportToCypherDialog extends JPanel {
    private File file;

    private ExportToCypherDialog() {
        setLayout(new GridBagLayout());
        final JLabel fileLocationLbl = new JLabel("Export to file:");
        final JTextField fileLocationTxtField = new JTextField();
        final JButton browseBtn = new JButton("Browse");
        browseBtn.addActionListener(l -> {
            file = UIUtil.saveFile(this, "Choose Cypher file location", "TXT file", Collections.singleton("txt"), "export.txt");
            if (file != null) {
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                if (file.exists()) {
                    fileLocationTxtField.setText(file.getAbsolutePath());
                } else {
                    try {
                        if (file.createNewFile()) {
                            fileLocationTxtField.setText(file.getAbsolutePath());
                        }
                    } catch (IOException ignored) {
                        file = null;
                    }
                }
            }
        });

        final Insets insets = new Insets(2, 2, 2, 2);
        add(fileLocationLbl, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.NONE, new Insets(8, 2, 2, 2), 0, 0));
        add(fileLocationTxtField, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        add(browseBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.NONE, insets, 0, 0));
    }

    public static File showDialog(OWLEditorKit kit) {
        final ExportToCypherDialog panel = new ExportToCypherDialog();
        final int response = JOptionPaneEx.showConfirmDialog(kit.getOWLWorkspace(), "Export to Cypher", panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null);
        if (response == JOptionPane.OK_OPTION) {
            return panel.file;
        }

        return null;
    }
}
