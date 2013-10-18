package examples;

import bignum.UnsignedBigInt;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    
    public static void main(String[] args) {

        SecureRandom random = new SecureRandom();
        
        UnsignedBigInt p = new UnsignedBigInt(BigInteger.probablePrime(256, random).toString());
        UnsignedBigInt g = new UnsignedBigInt("2");
        
        UnsignedBigInt a = new UnsignedBigInt(BigInteger.probablePrime(128, random).toString());
        UnsignedBigInt b = new UnsignedBigInt(BigInteger.probablePrime(128, random).toString());
        
        System.out.println("Alice's secret is: " + a + ", Bob's secret is: " + b);
        
        UnsignedBigInt A = g.modPow(a, p);
        UnsignedBigInt B = g.modPow(b, p);
        
        System.out.println("A: " + A);
        System.out.println("B: " + B);
        System.out.println("---");

        UnsignedBigInt alice = B.modPow(a, p);
        UnsignedBigInt bob = A.modPow(b, p);

        System.out.println("Alice got: " + alice);
        System.out.println("Bob got: " + bob);
    }
}
