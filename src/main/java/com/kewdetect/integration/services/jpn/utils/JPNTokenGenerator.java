package com.kewdetect.integration.services.jpn.utils;

import com.cc.cielo.authgen.TokenGenerator;

public class JPNTokenGenerator {

    public static void main(String[] args){

        TokenGenerator tg1 = new TokenGenerator();
        System.out.println(tg1.generateTokenForAuth("NFCC","34997409"));

    }


    public static String generate(){
        TokenGenerator tg1 = new TokenGenerator();
        return tg1.generateTokenForAuth("NFCC","34997409");
    }


}
