package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, Invalid Email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email.toLowerCase();
    }

    private String getFullName(){
        return this.firstName+" "+this.lastName;
    }

    private String getEmail(){
        return this.email;
    }

    @Override
    public String toString(){
        return "Customer - name: "+getFullName()+" email: "+getEmail();
    }
}
