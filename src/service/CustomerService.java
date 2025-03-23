package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    // store all the customer using map
    private static final Map<String, Customer> customers = new HashMap<>();

    // add new customer
    public static Customer addCustomer(String firstName, String lastName, String email){
        Customer newCustomer = new Customer(firstName,lastName,email);
        customers.put(email, newCustomer);
        return newCustomer;
    }

    // get customer
    public static Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }

    // validate customer email
    public static boolean customerExists(String email) {
        return customers.containsKey(email);
    }

    // get all customers
    public static Collection<Customer> getAllCustomers(){
        List<Customer> allCustomers = new ArrayList<>();
        for(Map.Entry<String, Customer> customer : customers.entrySet()){
            allCustomers.add(customer.getValue());
        }
        return allCustomers;
    }

}
