/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import model.Customer;  

public class CustomerTest {
    
    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("1", "Lucas Lopes", "lucas@example.com");
    }

    @Test
    public void testGetName() {
        assertEquals("Lucas Lopes", customer.getName());
    }

    @Test
    public void testSetName() {
        customer.setName("Keven Macedo");
        assertEquals("Keven Macedo", customer.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("lucas@example.com", customer.getEmail());
    }

    @Test
    public void testSetEmail() {
        customer.setEmail("keven@example.com");
        assertEquals("keven@example.com", customer.getEmail());
    }
}


