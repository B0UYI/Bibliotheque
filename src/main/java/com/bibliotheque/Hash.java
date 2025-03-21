package com.bibliotheque;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {
    public static void main(String[] args) {
        String password = "sara123";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Nouveau hash pour sara123 : " + hashed);
    }
}
