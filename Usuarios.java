package com.mycompany.loginformapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    public static void guardarUsuariosEnArchivo(List<User> users, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                writer.write(user.getFirstName() + "," + user.getLastName() + "," + user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> cargarUsuariosDesdeArchivo(String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    String nombre = fields[0];
                    String apellido = fields[1];
                    String usuario = fields[2];
                    String contraseña = fields[3];
                    users.add(new User(nombre, apellido, usuario, contraseña));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}



