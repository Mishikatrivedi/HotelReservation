package service;

import model.Customer;

import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CustomerService {
    private static CustomerService reference = new CustomerService();
    private CustomerService(){}
    public static CustomerService getReference(){
        return reference;
    }


    static Collection<Customer> customersList = new HashSet<>();
    public static Map<String , Customer> emailCustMap = new HashMap<>();


    public static void addCustomer(String email , String firstName , String lastName){
        Customer cust = new Customer(firstName , lastName , email);
        if(emailCustMap.containsKey(email)){
            System.out.println("You already have an Account !!");
        }else{
            customersList.add(cust);
            emailCustMap.put(email , cust);
            System.out.println("Account Created!!");
        }
        cust.toString();
    }
    public static Customer getCustomer(String customerEmail) throws InstanceNotFoundException{
        if(emailCustMap.containsKey(customerEmail))
        return emailCustMap.get(customerEmail);
        else{
            throw new InstanceNotFoundException("The customer doesnt exist , check entered email or create an account first!!");
        }
    }
    public static Collection<Customer> getAllCustomers(){
        return customersList;
    }
}
