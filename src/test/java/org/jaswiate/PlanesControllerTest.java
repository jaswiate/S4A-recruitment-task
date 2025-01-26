package org.jaswiate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlanesControllerTest {
    private PlanesController planesController;

    @BeforeEach
    public void setUp() {
        ArrayList<Integer> planeRoutes = new ArrayList<>();
        planeRoutes.add(1);
        planeRoutes.add(2);
        planeRoutes.add(3);
        planeRoutes.add(2);
        planeRoutes.add(4);
        planesController = new PlanesController(planeRoutes);
    }

    @Test
    public void testP() {
        planesController.P(1, 25, 1);
        int p = planesController.actualPlaneRoutes.queryCapacity(1, 1);
        assertEquals(25, p);
        System.out.println("testP passed, provided: " + p + ", expected: 25");
    }

    @Test
    public void testC() {
        planesController.C(1, 1);
        int c = planesController.actualPlaneRoutes.queryCapacity(1, 1);
        assertEquals(0, c);
        System.out.println("testC passed, provided: " + c + ", expected: 0");
    }

    @Test
    public void testA() {
        planesController.C(1, 1);
        planesController.A(1, 35, 2);
        int a = planesController.actualPlaneRoutes.queryCapacity(1, 1);
        assertEquals(35, a);
        System.out.println("testA passed, provided: " + a + ", expected: 35");
    }

    @Test
    public void testQ() {
        planesController.P(0, 3, 1);
        planesController.P(1, 3, 2);
        int q = planesController.Q(0, 2, 3);
        assertEquals(23, q);
        System.out.println("testQ passed, provided: " + q + ", expected: 23");
    }

    @Test
    public void taskInstructionTest1() {
        int q1 = planesController.Q(0, 4, 2);
        int q2 = planesController.Q(1, 2, 2);
        planesController.C(1, 3);
        planesController.P(2, 5, 3);
        int q3 = planesController.Q(1, 3, 4);
        planesController.A(1, 5, 6);
        int q4 = planesController.Q(0, 4, 8);

        assertEquals(24, q1);
        assertEquals(10, q2);
        assertEquals(22, q3);
        assertEquals(100, q4);

        System.out.println("taskInstructionTest1 passed, provided: " + q1 + ", " + q2 + ", " + q3 + ", " + q4 + ", expected: 24, 10, 22, 100");
    }
}
