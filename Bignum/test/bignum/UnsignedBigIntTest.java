package bignum;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnsignedBigIntTest {
    
    private UnsignedBigInt a;
    private UnsignedBigInt b;
    
    @Before
    public void setUp() {
        a = new UnsignedBigInt(12341234L);
        b = new UnsignedBigInt("12341234123412341234123412341234123412341234");
    }

    @Test
    public void testLongConstructor_positiveInt() {
        assertEquals(a.longValue(), 12341234L);
    }

    @Test
    public void testStringConstructor() {
        assertEquals(b.toString(), "12341234123412341234123412341234123412341234");
    }
    
    @Test
    public void testAdd() {
        assertEquals(a.add(b), new UnsignedBigInt("12341234123412341234123412341234123424682468"));
        assertEquals(b.add(a), new UnsignedBigInt("12341234123412341234123412341234123424682468"));
    }
    
    @Test
    public void testBiggerThan() {
        assertTrue(b.biggerThan(a));
        assertTrue(!a.biggerThan(b));
        assertTrue(!b.biggerThan(b));
    }
    
    @Test
    public void testEquals() {
        assertTrue(a.equals(a));
        assertTrue(b.equals(b));
        assertTrue(!a.equals(b));
        assertTrue(!b.equals(a));
        assertTrue(a.equals(new UnsignedBigInt(a.toString())));
    }
    
    @Test
    public void testDivide() {
        assertEquals(a.divide(a), new UnsignedBigInt("1"));
        assertEquals(b.divide(a), new UnsignedBigInt("1000000010000000100000001000000010000"));
    }
    
    @Test
    public void testLongValue() {
        assertEquals(a.longValue(), 12341234L);
    }
    
    @Test
    public void testMod() {
        assertEquals(a.mod(a), new UnsignedBigInt("0"));
        assertEquals(b.mod(a), new UnsignedBigInt("1234"));
    }
    
    @Test
    public void testModPow() {
        assertEquals(b.modPow(new UnsignedBigInt("4"), a), new UnsignedBigInt("3720510"));
        assertEquals(b.modPow(a, a), new UnsignedBigInt("1522756"));
    }
    
    @Test
    public void testMultiply_basecase() {
        assertEquals(a.multiply(a), new UnsignedBigInt("152306056642756"));
    }
    
    @Test
    public void testMultiply_karatsuba() {
        UnsignedBigInt c = new UnsignedBigInt("1552518092300708935148979488462502555256886017116696611139052038026050952686376886330878408828646477950487730697131073206171580044114814391444287275041181139204454976020849905550265285631598444825262999193716468750892846853816057855");
        assertEquals(c.multiply(c), new UnsignedBigInt("2410312426921032588580116606028314112912093247945688951359675039065257391591803200669085024107346049663448766280888004787862416978794958324969612987890774651455213339381625224770782077917681499676845543137387820057597345857904599106356350937498090094699856664417295567115701321048224206133690087915683964997615196447464774285761276110587297055818433175507740905485263500588072081099152785395963423140560695146462266635509081361999844936968295485071311052707201025"));
    }
    
    @Test
    public void testPow() {
       assertEquals(a.pow(new UnsignedBigInt("0")), new UnsignedBigInt("1"));
       assertEquals(a.pow(new UnsignedBigInt("1")), a);
       assertEquals(a.pow(new UnsignedBigInt("2")), new UnsignedBigInt("152306056642756"));
       assertEquals(a.pow(new UnsignedBigInt("3")), new UnsignedBigInt("1879644684645506200904"));
       assertEquals(a.pow(new UnsignedBigInt("4")), new UnsignedBigInt("23197134890066399073807275536"));
    }
    
    @Test
    public void testSmallerThan() {
        assertTrue(a.smallerThan(b));
        assertTrue(!b.smallerThan(a));
        assertTrue(!a.smallerThan(a));
    }
    
    @Test
    public void testSubtract() {
        assertEquals(b.subtract(a), new UnsignedBigInt("12341234123412341234123412341234123400000000"));
        assertEquals(a.subtract(a), new UnsignedBigInt("0"));
    }
    
    @Test
    public void testToString() {
        assertEquals(a.toString(), "12341234");
        assertEquals(b.toString(), "12341234123412341234123412341234123412341234");
    }
    
}