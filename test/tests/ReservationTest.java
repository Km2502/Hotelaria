/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;


import model.Customer;
import model.Employee;
import model.Reservation;

public class ReservationTest {
    
    private Reservation reservation;
    private Customer customer;
    private Employee employee;

    @Before
    public void setUp() {
        // Inicializa os objetos com valores de exemplo
        customer = new Customer("1", "Lucas Lopes", "lucas@example.com");
        employee = new Employee("E1", "Joao Pedro", "joao@example.com");
        reservation = new Reservation("101", customer, new Date(), 101, employee);
    }

    @Test
    public void testGetReservationId() {
        assertEquals("101", reservation.getReservationId());
    }

    @Test
    public void testSetReservationId() {
        reservation.setReservationId("102");
        assertEquals("102", reservation.getReservationId());
    }

    @Test
    public void testGetCustomer() {
        assertEquals(customer, reservation.getCustomer());
    }

    @Test
    public void testSetCustomer() {
        Customer newCustomer = new Customer("2", "Keven Macedo", "keven@example.com");
        reservation.setCustomer(newCustomer);
        assertEquals(newCustomer, reservation.getCustomer());
    }

    @Test
    public void testGetDate() {
        assertNotNull(reservation.getDate());
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();
        reservation.setDate(newDate);
        assertEquals(newDate, reservation.getDate());
    }

    @Test
    public void testGetCreatedBy() {
        assertEquals(employee, reservation.getCreatedBy());
    }

    @Test
    public void testSetCreatedBy() {
        Employee newEmployee = new Employee("E2", "Pedro Castelhano", "pedro@example.com");
        reservation.setCreatedBy(newEmployee);
        assertEquals(newEmployee, reservation.getCreatedBy());
    }
}




