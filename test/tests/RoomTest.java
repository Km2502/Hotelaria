/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import model.Room;

public class RoomTest {
    
    private Room room;

    @Before
    public void setUp() {
        room = new Room(101, true);
    }

    @Test
    public void testGetRoomNumber() {
        assertEquals(101, room.getRoomNumber());
    }

    @Test
    public void testSetRoomNumber() {
        room.setRoomNumber(102);
        assertEquals(102, room.getRoomNumber());
    }

    @Test
    public void testIsAvailable() {
        assertTrue(room.isAvailable());
    }

    @Test
    public void testSetAvailable() {
        room.setAvailable(false);
        assertFalse(room.isAvailable());
    }
}

