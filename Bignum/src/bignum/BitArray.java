package bignum;

public class BitArray {

    private long[] a;
    
    public BitArray() {
        a = new long[1];
    }
    
    public BitArray(long l) {
        a = new long[1];
        a[0] = l;
    }

    public BitArray(BitArray ba) {
        a = new long[ba.a.length];
        System.arraycopy(ba.a, 0, a, 0, ba.a.length);
    }
    
    private void expand(int w) {
        long[] newArray = new long[Math.max(a.length * 2, w + 1)];
        System.arraycopy(a, 0, newArray, 0, a.length);
        this.a = newArray;
    }
    
    public int length() {
        for(int i = a.length - 1; i >= 0; i--) {
            if(a[i] != 0) {
                for(int j = 63; j >= 0; j--) {
                    if((a[i] & (1L << (j % 64))) != 0) {
                        return (i * 64) + j + 1;
                    }
                }
            }
        }
        return 0;
    }
    
    public void clear() {
        for(int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
    }
    
    public void setBit(int i, int b) {
        int w = (int)Math.floor(i / 64.0f);
        if(w >= a.length) {
            expand(w);
        }
        if(b == 1) {
            a[w] |= (1L << (i % 64));
        } else {
            a[w] &= ~(1L << (i % 64));
        }
    }
    
    public int getBit(int i) {
        int w = (int)Math.floor(i / 64.0f);
        if(w >= a.length) {
            return 0;
        }
        return (a[w] & (1L << (i % 64))) == 0 ? 0 : 1;
    }
    
    public void shiftLeft() {
        int lowBit = 0;
        int highBit = 0;
        for(int i = 0; i < a.length; i++) {
            lowBit = highBit;
            highBit = getBit((i * 64) + 63);
            a[i] <<= 1L;
            setBit(i * 64, lowBit);
            
            if(highBit > 0 && i == a.length - 1) {
                expand(0);
            }
        }
    }
    
    public void shiftRight() {
        // TODO CARRY
        for(int i = 0; i < a.length; i++) {
            a[i] >>= 1L;
        }
    }
    
    /**
     * Returns little-endian string representation of this bit array
     * @return A little-endian string representation of this bit array
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = a.length - 1; i >= 0; i--) {
            sb.append(" ");
            sb.append(String.format("%64s", Long.toBinaryString(a[i])).replace(' ', '0'));
        }
        return sb.toString().trim();
    }
    
    public long toLong() {
        return a[0];
    }
}