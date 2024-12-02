package org.util;

import java.util.ArrayList;

public class ValidateRegistrationInput {
    public ArrayList<Boolean> validateInputs(String login,String password,String firstName,String lastName, String email, String age, String country, String city){
        ArrayList<Boolean> list = new ArrayList<>();
        if (login.length()<=3|| !login.matches("^[a-zA-Z0-9._-]+$")) {
            list.add(false);
        } else {
            list.add(true);
        }

        if (password.length()<=3|| !password.matches("^[a-zA-Z0-9._-]+$")) {
            list.add(false);
        } else {
            list.add(true);
        }

        if (firstName.isEmpty() || !firstName.matches("^[a-zA-Zа-яА-ЯёЁ\\s-]+$")) {
            list.add(false);
        } else {
            list.add(true);
        }


        if (lastName.isEmpty() || !lastName.matches("^[a-zA-Zа-яА-ЯёЁ\\s-]+$")) {
            list.add(false);
        } else {
            list.add(true);
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email.isEmpty() || !email.matches(emailRegex)) {
            list.add(false);
        }
        else{
            list.add(true);
        }


        try {
            int ageNum = Integer.parseInt(age);
            list.add(ageNum >= 0 && ageNum <= 100);
        } catch (NumberFormatException e) {
            list.add(false);
        }

        if (!country.matches("^[a-zA-Zа-яА-ЯёЁ\\s-]+$") || country.isEmpty()){
            list.add(false);
        }
        else{
            list.add(true);
        }


        if (!city.matches("^[a-zA-Zа-яА-ЯёЁ\\s-]+$") || city.isEmpty()){
            list.add(false);
        }
        else{
            list.add(true);
        }

        return list;
    }
}
