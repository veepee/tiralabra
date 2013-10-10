package examples;

import bignum.UnsignedBigInt;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        
        UnsignedBigInt p = new UnsignedBigInt(BigInteger.probablePrime(256, random).toString());
        //UnsignedBigInt p = new UnsignedBigInt("2410312426921032588552076022197566074856950548502459942654116941958108831682612228890093858261341614673227141477904012196503648957050582631942730706805009223062734745341073406696246014589361659774041027169249453200378729434170325843778659198143763193776859869524088940195577346119843545301547043747207749969763750084308926339295559968882457872412993810129130294592999947926365264059284647209730384947211681434464714438488520940127459844288859336526896320919633919");
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
