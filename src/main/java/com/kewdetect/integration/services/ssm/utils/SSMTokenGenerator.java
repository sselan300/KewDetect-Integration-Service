package com.kewdetect.integration.services.ssm.utils;

import com.cc.cielo.authgen.TokenGenerator;

public class SSMTokenGenerator {

    public static void main(String[] args){

        TokenGenerator tg = new TokenGenerator();
        System.out.println(tg.generateTokenForAuth("NFCC","34997409"));

    }


    public static String generate(){
        TokenGenerator tg = new TokenGenerator();
        return tg.generateTokenForAuth("NFCC","34997409");
    }


}
