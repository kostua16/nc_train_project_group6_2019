package com.edunetcracker.billingservice.ProxyValidator.cipher;

import java.io.*;
import java.util.Base64;

/**
 * Used for passing Classes between applications
 * Because
 * The String class is serialized everywhere (package is the same)
 * App(A) => MyClass => String => MyClass => App(B)
 */
public class Cipher {

    /**
     * Decoding an encrypted String to an Object
     * String "rO0ABXQAEnsiYSI6IkxvdmVBbmRXYXIifQ==" => MyClass
     *
     */
    public static Object deCode(String s) throws IOException, ClassNotFoundException{
        byte [] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        System.out.println("Decode string to object");
        ois.close();
        return o;
    }
    /**
     * Encrypting an object into a string
     * MyClass n => String "rO0ABXQAEnsiYSI6IkxvdmVBbmRXYXIifQ=="
     *
     */
    public static String enCode(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject(o);
        System.out.println("Encode object to strings");
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
