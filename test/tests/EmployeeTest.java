/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import model.Employee;

public class EmployeeTest {
    
    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee("E1", "Lucas Lopes", "lucas@example.com");
    }

    @Test
    public void testGetId() {
        assertEquals("E1", employee.getId());
    }

    @Test
    public void testSetId() {
        employee.setId("E2");
        assertEquals("E2", employee.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Lucas Lopes", employee.getName());
    }

    @Test
    public void testSetName() {
        employee.setName("João Pedro");
        assertEquals("João Pedro", employee.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("lucas@example.com", employee.getEmail());
    }

    @Test
    public void testSetEmail() {
        employee.setEmail("joao@example.com");
        assertEquals("joao@example.com", employee.getEmail());
    }
}

