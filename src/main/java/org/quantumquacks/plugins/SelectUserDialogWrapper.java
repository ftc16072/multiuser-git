package org.quantumquacks.plugins;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Pair;
import com.sun.istack.Nullable;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectUserDialogWrapper extends DialogWrapper {
    List<Pair<String, String>> users;
    JComboBox<String> comboBox;

    public SelectUserDialogWrapper(){
        super(true); // use current window as parent
        users = new ArrayList<>();
        comboBox = new JComboBox<>();
    }
    public void addUser(String name, String email){
        users.add(new Pair<>(name, email));
    }
    @Override
    public void init(){
        super.init();
        setTitle("Select Git User");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select or enter like 'first last <e-mail>'");

        comboBox.setEditable(true);
        for(Pair<String, String> user : users){
            comboBox.addItem(user.first + "  <" + user.second + '>');
        }
        dialogPanel.add(label, BorderLayout.NORTH);
        dialogPanel.add(comboBox, BorderLayout.CENTER);

        return dialogPanel;
    }
    public String getSelectedUser(){
        return (String)comboBox.getSelectedItem();
    }
}
