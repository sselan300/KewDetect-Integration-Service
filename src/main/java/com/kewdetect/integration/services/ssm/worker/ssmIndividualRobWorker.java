package com.kewdetect.integration.services.ssm.worker;


import com.cc.cielo.authgen.TokenGenerator;
import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kewdetect.integration.services.sftpService.utils.ISFTPService;
import com.kewdetect.integration.services.sftpService.utils.impl.SFTPServiceImpl;
import com.kwm.common.analytics.Analytics;
import com.kwm.common.analytics.AnalyticsHelper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ssmIndividualRobWorker {

    private Analytics analytics = AnalyticsHelper.geAnalytics(ssmIndividualRobWorker.class);


    @Autowired
    ISFTPService sftpclient = new SFTPServiceImpl();


    //SOAP using mockio example
    public void execute(TaskModelRequest model) throws UnirestException {

        TokenGenerator tg = new TokenGenerator();
        String token = tg.generateTokenForAuth("NFCC","34997409");


        HttpResponse<String> response = Unirest.post("http://integrasistg.ssm.com.my/PersonalInvolvement/1")
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("Authorization", "Bearer" + token)
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pi=\"http://pi.ssm.com.my\">\n    <soapenv:Header/>\n    <soapenv:Body>\n        <pi:getPersonalInvolvementRob>\n            <!--Optional:-->\n            <header>\n                <!--Optional:-->\n                <customerId>NFCC</customerId>\n                <!--Optional:-->\n                <customerReferenceNo>MSB Test</customerReferenceNo>\n                <!--Optional:-->\n                <customerRequestDate>2022-08-05 10:05:00T</customerRequestDate>\n            </header>\n            <!--Optional:-->\n            <request>\n                <!--Optional:-->\n                <personalInvolvementRobReq>\n                    <!--Optional:-->\n                    <idNo>"+model.getKeyword()+"</idNo>\n                    <!--Optional:-->\n                    <idType>MK</idType>\n                    <!--Optional:-->\n                    <tableId>ROBINFO</tableId>\n                </personalInvolvementRobReq>\n            </request>\n        </pi:getPersonalInvolvementRob>\n    </soapenv:Body>\n</soapenv:Envelope>")
                .asString();


        String xml = response.getBody();
        try {

            JSONObject json = XML.toJSONObject(xml);
            json
                    .getJSONObject("wsdl:definitions")
                    .remove("xmlns:xsd");
            json
                    .getJSONObject("wsdl:definitions")
                    .remove("xmlns:wsdl");
            json
                    .getJSONObject("wsdl:definitions")
                    .remove("xmlns:tns");
            json
                    .getJSONObject("wsdl:definitions")
                    .getJSONObject("wsdl:portType")
                    .getJSONObject("wsdl:operation")
                    .getJSONObject("wsdl:output")
                    .remove("getPersonalInvolvementRocResponse");
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRocResponse")
                    .remove("xmlns:xsd");
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRocResponse")
                    .remove("xmlns:pi");
            json
                    .getJSONObject("soap:Envelope")
                    .remove("xmlns:soap");


            String jsonString = json.toString(4);
            JSONObject metadataJson = new JSONObject();
            metadataJson.put("rfi_id",Integer.parseInt(model.getRfiID()));
            metadataJson.put("jops_id",Integer.parseInt(model.getJoID()));
            metadataJson.put("ip_id",Integer.parseInt(model.getIpID()));

            metadataJson.put("execution_id","2");
            metadataJson.put("data_source_id","2");


            sftpclient.export2ftp(jsonString,metadataJson);

        } catch (Exception e) {

            analytics.error(e.getMessage());

        }
    }

}
