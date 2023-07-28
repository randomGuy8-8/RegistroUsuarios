package com.mycompany.loginformapp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginFormApp {
    private static List<User> users = new ArrayList<>();
    private static JFrame frame;
    private static JPanel loginPanel;
    private static JPanel registerPanel;
    private static JTextField nameField;
    private static JTextField lastNameField;
    private static JTextField newUserField;
    private static JPasswordField newPassField;
    private static JPasswordField confirmPassField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Inicio de Sesión");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 250);
            frame.setLocationRelativeTo(null);

            createLoginPanel();
            createRegisterPanel();

            frame.add(loginPanel);
            frame.setVisible(true);
        });
    }

    public static void showLoginFrame() {
        frame.remove(registerPanel);
        frame.add(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.gridx = 0;
        loginGbc.gridy = 0;
        loginGbc.anchor = GridBagConstraints.WEST;
        loginGbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Usuario:");
        loginPanel.add(userLabel, loginGbc);

        loginGbc.gridx = 1;
        JTextField userField = new JTextField(15);
        loginPanel.add(userField, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 1;
        JLabel passLabel = new JLabel("Contraseña:");
        loginPanel.add(passLabel, loginGbc);

        loginGbc.gridx = 1;
        JPasswordField passField = new JPasswordField(15);
        loginPanel.add(passField, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 2;
        JButton loginButton = new JButton("Iniciar sesión");
        loginPanel.add(loginButton, loginGbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (isLoginValid(username, password)) {
                JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso");

                // Abrir la nueva ventana para mostrar la lista de usuarios
                UserListView userListView = new UserListView(users);
                userListView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton registerButton = new JButton("Registrar");
        loginGbc.gridy = 3;
        loginPanel.add(registerButton, loginGbc);

        registerButton.addActionListener(e -> {
            frame.remove(loginPanel);
            frame.add(registerPanel);
            frame.revalidate();
            frame.repaint();
        });
    }

    private static void createRegisterPanel() {
        registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints registerGbc = new GridBagConstraints();
        registerGbc.gridx = 0;
        registerGbc.gridy = 0;
        registerGbc.anchor = GridBagConstraints.WEST;
        registerGbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Nombre:");
        registerPanel.add(nameLabel, registerGbc);

        registerGbc.gridx = 1;
        nameField = new JTextField(15);
        registerPanel.add(nameField, registerGbc);

        registerGbc.gridx = 0;
        registerGbc.gridy = 1;
        JLabel lastNameLabel = new JLabel("Apellido:");
        registerPanel.add(lastNameLabel, registerGbc);

        registerGbc.gridx = 1;
        lastNameField = new JTextField(15);
        registerPanel.add(lastNameField, registerGbc);

        registerGbc.gridx = 0;
        registerGbc.gridy = 2;
        JLabel newUserLabel = new JLabel("Nuevo Usuario:");
        registerPanel.add(newUserLabel, registerGbc);

        registerGbc.gridx = 1;
        newUserField = new JTextField(15);
        registerPanel.add(newUserField, registerGbc);

        registerGbc.gridx = 0;
        registerGbc.gridy = 3;
        JLabel newPassLabel = new JLabel("Nueva Contraseña:");
        registerPanel.add(newPassLabel, registerGbc);

        registerGbc.gridx = 1;
        newPassField = new JPasswordField(15);
        registerPanel.add(newPassField, registerGbc);

        registerGbc.gridx = 0;
        registerGbc.gridy = 4;
        JLabel confirmPassLabel = new JLabel("Confirmar Contraseña:");
        registerPanel.add(confirmPassLabel, registerGbc);

        registerGbc.gridx = 1;
        confirmPassField = new JPasswordField(15);
        registerPanel.add(confirmPassField, registerGbc);

        JButton registerButton = new JButton("Registrar");
        registerGbc.gridy = 5;
        registerPanel.add(registerButton, registerGbc);

        registerButton.addActionListener(e -> {
            String username = newUserField.getText();
            String password = new String(newPassField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());
            String name = nameField.getText();
            String lastName = lastNameField.getText();

            if (name.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (isUserRegistered(username)) {
                JOptionPane.showMessageDialog(frame, "El nombre de usuario ya está en uso", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User newUser = new User(name, lastName, username, password);
                users.add(newUser);
                Usuarios.guardarUsuariosEnArchivo(users, "Users.txt");
                JOptionPane.showMessageDialog(frame, "Registro exitoso");

                clearRegisterFields();
                frame.remove(registerPanel);
                frame.add(loginPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    private static boolean isUserRegistered(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLoginValid(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static void clearRegisterFields() {
        nameField.setText("");
        lastNameField.setText("");
        newUserField.setText("");
        newPassField.setText("");
        confirmPassField.setText("");
    }
}


















   



