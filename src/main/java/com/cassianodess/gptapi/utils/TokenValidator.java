package com.cassianodess.gptapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


public class TokenValidator {


    public static String validateToken(String token, String secret) {
       try {
         Algorithm algorithm = Algorithm.HMAC256(secret);
         return JWT
         .require(algorithm)
         .withIssuer("auth-gpt")
         .build()
         .verify(token)
         .getSubject();
       } catch (JWTVerificationException e) {
        System.err.println(e);
        throw new RuntimeException("", e);
       }

    }
}
