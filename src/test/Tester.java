package test;

import menus.MainMenu;
import model.Customer;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("Prince", "Raj","email");
        System.out.println(customer);
    }
}
