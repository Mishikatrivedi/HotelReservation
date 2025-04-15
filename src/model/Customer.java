package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String first ,String last ,String custEmail){
        this.firstName = first;
        this.lastName = last;
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(custEmail).matches()){
            throw new IllegalArgumentException("Error : Invalid Email.");
        }else{
            this.email = custEmail;
        }
    }
    @Override
    public final String toString(){
        return "First Name : " + firstName + ", Last Name : " + lastName + ", Email : " + email;
    }
}
