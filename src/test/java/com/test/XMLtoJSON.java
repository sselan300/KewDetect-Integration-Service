package com.test;

import org.json.JSONObject;
import org.json.XML;

public class XMLtoJSON {


        public static void main(String[] args) {

            String xmlStr = "<student>\r\n" ;

            JSONObject json = XML.toJSONObject(xmlStr);

            System.out.println(json);

        }

    }

