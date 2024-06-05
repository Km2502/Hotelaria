/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Customer;

public class CustomerTest {
    
    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("C1", "Lucas Lopes", "lucas@example.com", "123456789");
    }

    @Test
    public void testGetId() {
        assertEquals("C1", customer.getId());
    }

    @Test
    public void testSetId() {
        customer.setId("C2");
        assertEquals("C2", customer.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Lucas Lopes", customer.getName());
    }

    @Test
    public void testSetName() {
        customer.setName("João Pedro");
        assertEquals("João Pedro", customer.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("lucas@example.com", customer.getEmail());
    }

    @Test
    public void testSetEmail() {
        customer.setEmail("joao@example.com");
        assertEquals("joao@example.com", customer.getEmail());
    }

    @Test
    public void testGetPhone() {
        assertEquals("123456789", customer.getPhone());
    }

    @Test
    public void testSetPhone() {
        customer.setPhone("987654321");
        assertEquals("987654321", customer.getPhone());
    }
}



