package com.campusdual.classroom;

import com.campusdual.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class Phonebook {

    public Map<String, Contact> contacts = new HashMap<>();

    public void menu() {
        int choice;

        choice = Utils.integer("\nMenú principal:" +
                "\n1. Añadir un contacto al listín telefónico" +
                "\n2. Mostar los contactos del listín telefónico" +
                "\n3. Seleccionar un contacto y mostrar su menú de acciones" +
                "\n4. Eliminar un contacto" +
                "\n5. Salir \n");

        switch (choice) {
            case 1:
                addContact(null);
                break;
            case 2:
                showPhonebook();
                break;
            case 3:
                selectContact();
                break;
            case 4:
                deleteContact(null);
                break;
            case 5:
                System.out.println("¡Hasta pronto!");
                System.exit(0);
                break;
            default:
                System.out.println("La opción introducida no es válida");
        }

    }

    public Map<String, Contact> getData() {
        return this.contacts;
    }

    //Añadir un contacto al listín telefónico
    public void addContact(Contact contact) {
        String name = Utils.string("\nNombre del contacto: \n");
        String surname = Utils.string("\nApellido/s del contacto: \n");
        String phone = Utils.string("\nNúmero principal del contacto: \n");

        //validar número
        while (phone.replaceAll("[^0-9]", "").length() != 9) {
            System.out.println("\nEl número debe tener 9 cifras.");
            phone = Utils.string("\nNúmero principal del contacto: \n");
        }

        Contact newContact = new Contact(name, surname, phone);

        contacts.put(newContact.getCode(), newContact);

        System.out.println("\nContacto añadido correctamente.");

        menu();
    }

    //Mostar los contactos del listín telefónico
    public void showPhonebook() {
        if (contacts.isEmpty()) {
            System.out.println("\nNo hay contactos en el listín telefónico.\n");
        } else {
            contacts.forEach((code, contact) -> {
                System.out.println("\n- " + contact.getCode() + ": " + contact.getName() + " " + contact.getSurnames());
            });
        }

        menu();
    }

    //Seleccionar un contacto y mostrar su menú de acciones
    public void selectContact() {
        if (contacts.isEmpty()) {
            System.out.println("\nNo hay contactos en el listín telefónico.\n");
        } else {
            contacts.forEach((code, contact) -> {
                System.out.println("\n- " + contact.getCode());
            });

            Contact contactToSelect = null;

            //buscar contacto
            do {
                String code = Utils.string("\nIntroduzca el código del usuario que desea seleccionar: \n");

                // Buscar contacto
                for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
                    if (entry.getKey().equals(code)) {
                        contactToSelect = entry.getValue();
                        break;
                    }
                }

                if (contactToSelect == null) {
                    System.out.println("\nNo se ha encontrado un contacto con ese código. Intente de nuevo.");
                }
            } while (contactToSelect == null); // Repite el bucle hasta que el código sea válido

            // Si encontramos el contacto, mostramos el menú de ese contacto
            System.out.println("\nContacto seleccionado: " + contactToSelect.getCode());

            // Acceder al menú del contacto
            contactToSelect.contactMenu(contactToSelect, this);

        }
    }

    //Eliminar un contacto
    public void deleteContact(String code) {
        if (contacts.isEmpty()) {
            System.out.println("\nNo hay contactos en el listín telefónico.\n");
        } else {

            String codeContact = Utils.string("\nIntroduzca el código del usuario que desea eliminar: \n");

            if (contacts.remove(code) != null) {
                System.out.println("\nContacto eliminado.");
            } else {
                System.out.println("\nNo se encontró un contacto con ese código.\n");
            }
        }

        menu();
    }

}
