package bignum;

import java.math.BigInteger;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RandomizedTest {

    public static final int TESTS_ENABLED = 1;
    
    public static final int TESTS_MULTIPLIER = 128;
    public static final int TESTS_AMOUNT = 4;
    
    private Random r;
    
    @Before
    public void setUp() {
        r = new Random();
    }
    
    @Test
    public void randomizedAddTest() {
        for(int i = 1; i <= TESTS_AMOUNT * TESTS_ENABLED; i++) {
            BigInteger a = new BigInteger(TESTS_MULTIPLIER * i, r);
            BigInteger b = new BigInteger(TESTS_MULTIPLIER * i, r);
            UnsignedBigInt c = new UnsignedBigInt(a.toString());
            UnsignedBigInt d = new UnsignedBigInt(b.toString());
            
            assertTrue(a.add(b).toString().equals(c.add(d).toString()));
        }
    }
    
    @Test
    public void randomizedSubtractTest() {
        for(int i = 1; i <= TESTS_AMOUNT * TESTS_ENABLED; i++) {
            BigInteger a = new BigInteger(TESTS_MULTIPLIER * i, r);
            BigInteger b = new BigInteger(TESTS_MULTIPLIER * i, r);
            if(a.compareTo(b) < 0) {
                BigInteger swap = a;
                a = b;
                b = swap;
            }

            UnsignedBigInt c = new UnsignedBigInt(a.toString());
            UnsignedBigInt d = new UnsignedBigInt(b.toString());
            
            assertTrue(a.subtract(b).toString().equals(c.subtract(d).toString()));
        }
    }
    
    @Test
    public void randomizedMultiplyTest() {
        for(int i = 1; i <= TESTS_AMOUNT * TESTS_ENABLED; i++) {
            BigInteger a = new BigInteger(TESTS_MULTIPLIER * i, r);
            BigInteger b = new BigInteger(TESTS_MULTIPLIER * i, r);

            UnsignedBigInt c = new UnsignedBigInt(a.toString());
            UnsignedBigInt d = new UnsignedBigInt(b.toString());

            assertTrue(a.multiply(b).toString().equals(c.multiply(d).toString()));
        }
    }
    
    @Test
    public void randomizedDivideTest() {
        for(int i = 1; i <= TESTS_AMOUNT * TESTS_ENABLED; i++) {
            BigInteger a = new BigInteger(TESTS_MULTIPLIER * i, r);
            BigInteger b = new BigInteger(TESTS_MULTIPLIER * i, r);

            UnsignedBigInt c = new UnsignedBigInt(a.toString());
            UnsignedBigInt d = new UnsignedBigInt(b.toString());
            
            BigInteger biResult[] = a.divideAndRemainder(b);
            UnsignedBigInt ubiResult[] = c.divideAndRemainder(d);
            
            assertTrue("Quotient mismatch: was " + ubiResult[0].toString() + ", expected " + biResult[0].toString(),
                    biResult[0].toString().equals(ubiResult[0].toString()));
            assertTrue("Remainder mismatch: was " + ubiResult[1].toString() + ", expected " + biResult[1].toString(),
                    biResult[1].toString().equals(ubiResult[1].toString()));
        }
    }
    
}