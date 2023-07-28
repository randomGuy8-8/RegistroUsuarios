package com.mycompany.loginformapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserListView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<User> users;

    public UserListView(List<User> users) {
        this.users = users;

        setTitle("Usuarios Registrados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Usuario");

        for (User user : users) {
            tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getUsername()});
        }

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");
        JButton logoutButton = new JButton("Cerrar sesión");

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                User user = users.get(selectedRow);
                String newFirstName = JOptionPane.showInputDialog(this, "Nuevo Nombre:", user.getFirstName());
                String newLastName = JOptionPane.showInputDialog(this, "Nuevo Apellido:", user.getLastName());
                String newUsername = JOptionPane.showInputDialog(this, "Nuevo Usuario:", user.getUsername());

                user.setFirstName(newFirstName);
                user.setLastName(newLastName);
                user.setUsername(newUsername);

                tableModel.setValueAt(newFirstName, selectedRow, 0);
                tableModel.setValueAt(newLastName, selectedRow, 1);
                tableModel.setValueAt(newUsername, selectedRow, 2);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    users.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        logoutButton.addActionListener(e -> {
            this.setVisible(false); // Oculta la ventana actual (UserListView)
            LoginFormApp.showLoginFrame(); // Muestra la ventana de inicio de sesión
        });
    }
}







