/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Customer;
import model.Employee;
import model.Reservation;
import model.Room;

public class ReservationTest {

    private Reservation reservation;
    private Customer customer;
    private Room room;
    private Employee employee;

    @Before
    public void setUp() throws ParseException {
        customer = new Customer("C1", "Lucas Lopes", "lucas@example.com", "123456789");
        room = new Room(101, true);
        employee = new Employee("E1", "João Silva", "joao@example.com");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate = sdf.parse("2023-05-23");

        reservation = new Reservation("R1", customer, reservationDate, room, employee);
    }

    @Test
    public void testGetId() {
        assertEquals("R1", reservation.getId());
    }

    @Test
    public void testSetId() {
        reservation.setId("R2");
        assertEquals("R2", reservation.getId());
    }

    @Test
    public void testGetCustomer() {
        assertEquals(customer, reservation.getCustomer());
    }

    @Test
    public void testSetCustomer() {
        Customer newCustomer = new Customer("C2", "Pedro Silva", "pedro@example.com", "987654321");
        reservation.setCustomer(newCustomer);
        assertEquals(newCustomer, reservation.getCustomer());
    }

    @Test
    public void testGetReservationDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = sdf.parse("2023-05-23");
        assertEquals(expectedDate, reservation.getReservationDate());
    }

    @Test
    public void testSetReservationDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = sdf.parse("2023-06-01");
        reservation.setReservationDate(newDate);
        assertEquals(newDate, reservation.getReservationDate());
    }

    @Test
    public void testGetRoom() {
        assertEquals(room, reservation.getRoom());
    }

    @Test
    public void testSetRoom() {
        Room newRoom = new Room(102, true);
        reservation.setRoom(newRoom);
        assertEquals(newRoom, reservation.getRoom());
    }

    @Test
    public void testGetEmployee() {
        assertEquals(employee, reservation.getEmployee());
    }

    @Test
    public void testSetEmployee() {
        Employee newEmployee = new Employee("E2", "Maria Santos", "maria@example.com");
        reservation.setEmployee(newEmployee);
        assertEquals(newEmployee, reservation.getEmployee());
    }
}











