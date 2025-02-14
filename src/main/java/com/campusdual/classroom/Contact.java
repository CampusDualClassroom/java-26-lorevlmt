package com.campusdual.classroom;

import com.campusdual.util.Utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Contact implements ICallActions {

    //atributos
    public String name;
    public String surnames;
    public String phone;
    public String code;
    public List<String> otherNumbers;

    //constructor
    public Contact(String name, String surnames, String phone) {
        this.name = name;
        this.surnames = surnames;
        this.phone = phone;
        this.code = codeGenerate(name, surnames);
        this.otherNumbers = new ArrayList<>();
    }


    //getter y setter
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return this.surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        //validar números
        phone = phone.replaceAll("[^0-9]", "");

        if (phone.length() == 9) {
            this.phone = phone;
        } else {
            System.out.println("\nEl número de teléfono debe tener exactamente 9 cifras.");
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getOtherNumbers() {
        return this.otherNumbers;
    }

    //code
    public String codeGenerate(String name, String surnames) {

        //minúsculas sin acentos
        name = Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        surnames = Normalizer.normalize(surnames.toLowerCase().trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        //primera letra del nombre
        String code = "" + name.charAt(0);

        //apellidos
        String[] surnameParts = surnames.split("\\s+");

        if (surnameParts.length > 1) {  //más de un apellido
            String firstSurname = surnameParts[0];  //primera letra del primer apellido
            String secondSurname = surnames.substring(firstSurname.length()).replaceAll("\\s+", "");    //segundo apellido sin espacios si es compuesto
            code += firstSurname.charAt(0) + secondSurname;
        } else {    //un apellido
            code += surnameParts[0];
        }

        return code;
    }

    //métodos de la interfaz
    @Override
    public void callMyNumber() {
        System.out.println("\nLlamando al número principal:\n" + this.name + "\n" + this.surnames + "\n" + this.phone);
    }

    @Override
    public void callOtherNumber(String number) {
        System.out.println("\nLlamando al número secundario:\n" + this.name + "\n" + this.surnames + "\n" + number);
    }

    @Override
    public void showContactDetails() {
        System.out.println("\nDetalles del contacto:\nNombre: " + this.name + "\nApellidos: " + this.surnames + "\nCódigo: " + this.code + "\nNúmero de teléfono: " + this.phone + "\nOtros números: " + (this.otherNumbers.isEmpty() ? "No hay otros números" : this.otherNumbers));
    }

    //añadir otros números del contacto
    public void addOtherNumber() {
        String newNumber = Utils.string("\nIntroduzca un número adicional: \n");

        while (newNumber.replaceAll("[^0-9]", "").length() != 9) {
            System.out.println("\nEl número debe tener exactamente 9 cifras.");
            newNumber = Utils.string("\nIntroduzca un número adicional (9 cifras): \n");
        }

        if (!newNumber.isEmpty()) {
            this.otherNumbers.add(newNumber);
            System.out.println("\nNúmero añadido correctamente.\n");
        } else {
            System.out.println("\nNo se añadió ningún número.\n");

        }
    }

    //menú del contacto
    public void contactMenu(Contact contactToSelect,  Phonebook phonebook) {
        int choice;

        do {
            choice = Utils.integer("\nMenú de contacto:" +
                    "\n1. Llamar al número principal" +
                    "\n2. Llamar a otro número propio" +
                    "\n3. Mostrar detalles del contacto" +
                    "\n4. Volver al menú principal \n");

            switch (choice) {
                case 1:
                    callMyNumber();
                    break;
                case 2:
                    if (this.otherNumbers.isEmpty()) {
                        System.out.println("\nNo tiene otros números guardados.\n");
                        String response = Utils.string("\n¿Quiere añadir un número? (S/N): \n").toLowerCase();
                        if (response.equalsIgnoreCase("s")) {
                            addOtherNumber();
                        }
                    } else {
                        System.out.println("\nOtros números disponibles: \n");
                        for (String number : this.otherNumbers) {
                            System.out.println("- " + number);
                        }
                        String selectedNumber = Utils.string("\nIntroduce el número al que deseas llamar: \n");

                        if (this.otherNumbers.contains(selectedNumber)) {
                            callOtherNumber(selectedNumber);
                        } else {
                            System.out.println("\nEl número introducido no está en la lista.\n");
                        }
                    }
                    break;
                case 3:
                    showContactDetails();
                    break;
                case 4:
                    System.out.println("\nVolviendo al menú principal");
                    phonebook.menu();
                    break;
                default:
                    System.out.println("La opción introducida no es válida");
            }
        } while (choice != 4);

    }


}
