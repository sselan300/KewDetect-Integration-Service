package com.test;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


//UNIREST directly example

public class UNIRestTesting {

//    public void getRequestExample() throws UnirestException {
//
//       HttpResponse<JsonNode> jsonResponse =
//               Unirest.get("https://dummy.restapiexample.com/api/v1/employees").asJson();
//
//       System.out.println("Status code " + jsonResponse.getStatus());
//        System.out.println("Status message " + jsonResponse.getStatusText());
//        System.out.println("Response body " + jsonResponse.getBody());
//
//    }

    //SOAP using mockio example
    public void getRequestExample2() throws UnirestException {

        HttpResponse<String> response = Unirest.post("http://demo9426900.mockable.io/")
                .header("Accept", "text/xml")
                .header("Content-Type", "text/xml")
                .header("Content-Encoding", "UTF-8")
                .header("SOAPAction", "getxml")
                .asString();

        String xml = response.getBody();
        try {

            JSONObject json = XML.toJSONObject(xml);
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRobResponse")
                    .remove("request");
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRobResponse")
                    .remove("xmlns:xsd");
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRobResponse")
                    .remove("xmlns:pi");
            json
                    .getJSONObject("soap:Envelope")
                    .remove("xmlns:soap");






            String jsonString = json.toString(4);
            System.out.println(jsonString);

        }catch (JSONException e) {

            System.out.println(e.toString());
        }

    }

    public static void main(String[] args) throws UnirestException {

        UNIRestTesting example = new UNIRestTesting();
        example.getRequestExample2();


    }
}