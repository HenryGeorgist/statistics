/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Will_and_Sara
 */
public class NormalTest {
    
    public NormalTest() {
        Normal N = new Normal(1,1);
        N.WriteToXML();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetInvCDF method, of class Normal.
     */
    @Test
    public void testGetInvCDF() {
        System.out.println("GetInvCDF");
        double probability = 0.0;
        Normal instance = null;
        double expResult = 0.0;
        double result = instance.GetInvCDF(probability);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetCDF method, of class Normal.
     */
    @Test
    public void testGetCDF() {
        System.out.println("GetCDF");
        double value = 0.0;
        Normal instance = null;
        double expResult = 0.0;
        double result = instance.GetCDF(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetPDF method, of class Normal.
     */
    @Test
    public void testGetPDF() {
        System.out.println("GetPDF");
        double value = 0.0;
        Normal instance = null;
        double expResult = 0.0;
        double result = instance.GetPDF(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
