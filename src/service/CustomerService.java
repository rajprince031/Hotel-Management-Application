package service;

import exception.CustomerIsNullException;
import exception.NoItemsInListException;
import model.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService instance = new CustomerService();// Singleton Instance
    private CustomerService() {} //private constructor
    //create a static method to return instance of class
    public static CustomerService getInstance() {
        return instance;
    }

    // store all the customer using map by email address
    private static final Map<String, Customer> customers = new HashMap<>();

    // add new customer
    public void addCustomer(String firstName, String lastName, String email){
        Customer newCustomer = new Customer(firstName,lastName,email);
        customers.put(email, newCustomer);
    }

    // get customer
    public Customer getCustomer(String email){
            if(customerExists(email)) return customers.get(email);
            return null;
    }

    // validate customer email
    public boolean customerExists(String email) {
        return customers.containsKey(email);

    }

    // get all customers
    public Collection<Customer> getAllCustomers(){
        try {
            if (customers.isEmpty()) System.out.println("\nCustomers List is Empty");
            else System.out.println("\nAll Customers");
            return customers.values();
        }catch (Exception exception){
            System.out.println("Error: "+exception.getMessage());
            return null;
        }
    }

}
