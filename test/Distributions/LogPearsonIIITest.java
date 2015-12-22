/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions;

import Distributions.MethodOfMoments.LogPearsonIII;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Q0HECWPL
 */
public class LogPearsonIIITest {
    private LogPearsonIII _LPII;
    public LogPearsonIIITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        _LPII = new LogPearsonIII(5,1,1,0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetInvCDF method, of class LogPearsonIII.
     */
    @Test
    public void testGetInvCDF() {
        System.out.println("GetInvCDF");
        double probability = 0.5;
        double expResult = 68852.5605818443;
        double result = _LPII.GetInvCDF(probability);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetCDF method, of class LogPearsonIII.
     */
    @Test
    public void testGetCDF() {
        System.out.println("GetCDF");
        double value = 0.0;
        double expResult = 0.0;
        double result = _LPII.GetCDF(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetPDF method, of class LogPearsonIII.
     */
    @Test
    public void testGetPDF() {
        System.out.println("GetPDF");
        double value = 0.0;
        double expResult = 0.0;
        double result = _LPII.GetPDF(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
