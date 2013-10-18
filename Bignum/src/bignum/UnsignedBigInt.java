package bignum;

/**
 * The UnsignedBigInt class for storing arbitrary-precision unsigned (non-negative) integers.
 * 
 */
public class UnsignedBigInt {

    public static UnsignedBigInt ZERO = new UnsignedBigInt(0);
    public static UnsignedBigInt ONE = new UnsignedBigInt(1);
    public static UnsignedBigInt TWO = new UnsignedBigInt(2);
    public static UnsignedBigInt TEN = new UnsignedBigInt(10);
    
    /**
     * BitArray for storing the bits of this UnsignedBigInt in little-endian byte order
     */
    private BitArray bits;
    
    /**
     * Creates a new UnsignedBigInt whose value is equal to the long integer l
     * @param l non-negative long integer l
     */
    public UnsignedBigInt(long l) {
        this.bits = new BitArray(l);
    }
    
    /**
     * Creates a new UnsignedBigInt whose value is equal to the base10 string representation s given
     * @param s String containing base10 string representation of an non-negative integer
     */
    public UnsignedBigInt(String s) {
        this();
        
        UnsignedBigInt newBigInt = new UnsignedBigInt();
        
        for(int i = 0; i < s.length(); i++) {
            newBigInt = newBigInt.multiply(UnsignedBigInt.TEN);
            newBigInt = newBigInt.add(new UnsignedBigInt(Long.parseLong(s.charAt(i) + "")));
        }
    
        this.bits = newBigInt.bits;
    }
    
    /**
     * Creates a new UnsignedBigInt whose value is equal to zero
     */
    public UnsignedBigInt() {
        this.bits = new BitArray();
    }
    
    /**
     * Creates a new UnsignedBigInt whose value is equal to the UnsignedBigInt b
     * @param b UnsignedBigInt to be copied
     */
    public UnsignedBigInt(UnsignedBigInt b) {
        this.bits = new BitArray(b.bits);
    }
    
    /**
     * Creates a new UnsignedBigInt whose value is equal to the value of bits given
     * @param bits BitArray to be copied
     */
    public UnsignedBigInt(BitArray bits) {
        this.bits = bits;
    }

    /**
     * Adds this UnsignedBigInt with the UnsignedBigInt b and returns the sum
     * @param b UnsignedBigInt containing addend b
     * @return UnsignedBigInt whose value is (this + b)
     */
    public UnsignedBigInt add(UnsignedBigInt b) {
        UnsignedBigInt result = new UnsignedBigInt();
        
        int carry = 0;
        int max = Math.max(this.bits.length(), b.bits.length());
        for(int i = 0; i <= max; i++) {
            int s = this.bits.getBit(i) + b.bits.getBit(i) + carry;
            carry = 0;
            if(s >= 2) {
                carry = 1;
                s -= 2;
            }
            result.bits.setBit(i, s);
        }
        
        return result;
    }
    
    /**
     * Compares the two UnsignedBigInts and returns true if this UnsignedBigInt is bigger
     * than the parameter b given, false otherwise
     * @param b UnsignedBigInt this UnsignedBigInt will be compared to
     * @return true, if this UnsignedBigInt is bigger than the UnsignedBigInt b, false otherwise
     */
    public boolean biggerThan(UnsignedBigInt b) {
        for(int i = Math.max(this.bits.length(), b.bits.length()); i >= 0; i--) {
            if(this.bits.getBit(i) > b.bits.getBit(i)) {
                return true;
            }
            if(this.bits.getBit(i) < b.bits.getBit(i)) {
                return false;
            }
        }
        return false;
    }
    
    /**
     * Compares the two UnsignedBigInts and returns true if the two UnsignedBigInts are equal,
     * false otherwise
     * @param b UnsignedBigInt this UnsignedBigInt will be compared to
     * @return true, if this UnsignedBigInt is equal to the UnsignedBigInt b, false otherwise
     */
    @Override
    public boolean equals(Object b) {
        if(b == null || !(b instanceof UnsignedBigInt)) {
            return false;
        }

        for(int i = Math.max(this.bits.length(), ((UnsignedBigInt)b).bits.length()); i >= 0; i--) {
            if(this.bits.getBit(i) > ((UnsignedBigInt)b).bits.getBit(i)) {
                return false;
            }
            if(this.bits.getBit(i) < ((UnsignedBigInt)b).bits.getBit(i)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Divides this UnsignedBigInt with the UnsignedBigInt b and returns the quotient
     * @param b UnsignedBigInt containing divisor b
     * @return UnsignedBigInt whose value is floor(this / b)
     */
    public UnsignedBigInt divide(UnsignedBigInt b) {
        UnsignedBigInt result[] = this.divideAndRemainder(b);
        return result[0];
    }
    
    /**
     * Divides this UnsignedBigInt with the UnsignedBigInt b returning the quotient and the remainder
     * @param b UnsignedBigInt containing divisor b
     * @return An UnsignedBigInt array containing the quotient (index 0) and the remainder (index 1)
     */
    public UnsignedBigInt[] divideAndRemainder(UnsignedBigInt b) {
        // quotient is in result[0]
        // remainder is in result[1]
        UnsignedBigInt result[] = new UnsignedBigInt[2];
        result[0] = new UnsignedBigInt();
        result[1] = new UnsignedBigInt();
        
        for(int i = this.bits.length() - 1; i >= 0; i--) {
            result[1].bits.shiftLeft();
            result[1].bits.setBit(0, this.bits.getBit(i));
            if(result[1].biggerThan(b) || result[1].equals(b)) {
                result[1] = result[1].subtract(b);
                result[0].bits.setBit(i, 1);
            }
        }
        
        return result;
    }
    
    /**
     * Returns the long integer representing this UnsignedBigInt, if possible
     * @return long integer containing the value of this UnsignedBigInt
     */
    public long longValue() {
        return this.bits.toLong();
    }
    
    /**
     * Divides this UnsignedBigInt with the UnsignedBigInt m and returns the remainder
     * @param b UnsignedBigInt containing divisor m
     * @return UnsignedBigInt whose value is (this mod m)
     */
    public UnsignedBigInt mod(UnsignedBigInt m) {
        UnsignedBigInt result[] = this.divideAndRemainder(m);
        return result[1];
    }
    
    /**
     * Calculates the remainder when this UnsignedBigInt is raised to the power represented
     * by the UnsignedBigInt e and divided by the UnsignedBigInt m
     * @param e UnsignedBigInt containing exponent e
     * @param m UnsignedBigInt containing modulus m
     * @return UnsignedBigInt whose value is (this^e mod m)
     */
    public UnsignedBigInt modPow(UnsignedBigInt e, UnsignedBigInt m) {
        UnsignedBigInt b = new UnsignedBigInt(this);
        UnsignedBigInt exp = new UnsignedBigInt(e);
        UnsignedBigInt result = new UnsignedBigInt(UnsignedBigInt.ONE);

        while(exp.biggerThan(UnsignedBigInt.ZERO)) {
            UnsignedBigInt mod = exp.mod(UnsignedBigInt.TWO);
            if(mod.equals(UnsignedBigInt.ONE)) {
                result = result.multiply(b).mod(m);
            }
            exp.bits.shiftRight();
            b = b.multiply(b).mod(m);
        }
        return result;
    }

    public UnsignedBigInt karatsuba(UnsignedBigInt b) {
        int m = (int)Math.ceil(Math.min(this.bits.length(), b.bits.length()) / 2.0);
        
        if(m < 512) {
            return this.multiply(b);
        }
        
        // TODO: calculate B^m with left shifts instead of multiplications
        UnsignedBigInt base = UnsignedBigInt.TWO.pow(new UnsignedBigInt(m));
        UnsignedBigInt base2 = base.pow(UnsignedBigInt.TWO);

        UnsignedBigInt[] x = this.divideAndRemainder(base);
        UnsignedBigInt[] y = b.divideAndRemainder(base);

        UnsignedBigInt z0 = x[1].karatsuba(y[1]);
        UnsignedBigInt z2 = x[0].karatsuba(y[0]);
        UnsignedBigInt z1 = ((x[0].add(x[1])).karatsuba(y[0].add(y[1]))).subtract(z2).subtract(z0);        

        z2 = z2.multiply(base2);
        z1 = z1.multiply(base);
        
        return z0.add(z1.add(z2));
    }
    
    /**
     * Multiplies this UnsignedBigInt with the UnsignedBigInt b and returns the product
     * @param b UnsignedBigInt containing multiplier b
     * @return UnsignedBigInt whose value is (this * b)
     */
    public UnsignedBigInt multiply(UnsignedBigInt b) {
        UnsignedBigInt result = new UnsignedBigInt();

        UnsignedBigInt temp;
        for(int i = 0; i < b.bits.length(); i++) {
            if(b.bits.getBit(i) > 0) {
                temp = new UnsignedBigInt(this);
                for(int j = 0; j < i; j++) {
                    if(i - j > 64) {
                        temp.bits.shiftLeft64();
                        j += 63;
                    } else {
                        temp.bits.shiftLeft();
                    }
                }
                result = result.add(temp);
            }
        }
        
        return result;
    }
    
    /**
     * Raises this UnsignedBigInt to the power represented by the UnsignedBigInt e
     * @param e UnsignedBigInt containing the exponent e
     * @return UnsignedBigInt whose value is (this^e)
     */
    public UnsignedBigInt pow(UnsignedBigInt e) {
        UnsignedBigInt b = new UnsignedBigInt(this);
        UnsignedBigInt result = new UnsignedBigInt(UnsignedBigInt.ONE);

        while(e.biggerThan(UnsignedBigInt.ZERO)) {
            if(e.bits.getBit(0) > 0) {
                result = result.multiply(b);
                e.bits.setBit(0, 0);
            }
            b = b.multiply(b);
            e.bits.shiftRight();
        }

        return result;
    }
    
    /**
     * Compares the two UnsignedBigInts and returns true if this UnsignedBigInt is smaller
     * than the parameter b given, false otherwise
     * @param b UnsignedBigInt this UnsignedBigInt will be compared to
     * @return true, if this UnsignedBigInt is smaller than the UnsignedBigInt b, false otherwise
     */
    public boolean smallerThan(UnsignedBigInt b) {
        for(int i = Math.max(this.bits.length(), b.bits.length()); i >= 0; i--) {
            if(this.bits.getBit(i) > b.bits.getBit(i)) {
                return false;
            }
            if(this.bits.getBit(i) < b.bits.getBit(i)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Subtracts the UnsignedBigInt b from this UnsignedBigInt and returns the difference.
     * Difference must be non-negative i.e. this >= b.
     * @param b UnsignedBigInt containing subtrahend b
     * @return UnsignedBigInt whose value is (this - b)
     */
    public UnsignedBigInt subtract(UnsignedBigInt b) {
        UnsignedBigInt result = new UnsignedBigInt();

        int borrow = 0;
        int max = Math.max(this.bits.length(), b.bits.length());
        for(int i = 0; i <= max; i++) {
            int s = this.bits.getBit(i) - b.bits.getBit(i) + borrow;
            borrow = 0;
            if(s < 0) {
                borrow = -1;
                s += 2;
            }
            result.bits.setBit(i, s);
        }
        
        return result;
    }
    
    /**
     * Returns the base10 string representation of this UnsignedBigInt
     * @return base10 string representation of this UnsignedBigInt
     */
    @Override
    public String toString() {              
        StringBuilder sb = new StringBuilder();
        
        UnsignedBigInt result[] = new UnsignedBigInt[2];
        result[0] = new UnsignedBigInt(this);
        
        do {
            result = result[0].divideAndRemainder(UnsignedBigInt.TEN);
            int n = 0;
            for(int i = 0; i < 4; i++) {
                if(result[1].bits.getBit(i) > 0) {
                    n += Math.pow(2, i);
                }
            }
            sb.append(n);
        } while(result[0].biggerThan(UnsignedBigInt.ZERO));

        return sb.reverse().toString();
    }
    
}