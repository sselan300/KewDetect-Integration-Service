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


//UNIREST directly example
@Service
public class ssmIndividualRocWorker {

    private Analytics analytics = AnalyticsHelper.geAnalytics(ssmIndividualRobWorker.class);


    @Autowired
    ISFTPService sftpclient = new SFTPServiceImpl();


    public void execute(TaskModelRequest model) throws UnirestException {

        TokenGenerator tg = new TokenGenerator();
        String token = tg.generateTokenForAuth("NFCC","34997409");

        HttpResponse<String> response = Unirest.post("http://integrasistg.ssm.com.my/PersonalInvolvement/1")
                .header("Content-Type", "text/xml")
                .header("Authorization", "Bearer" + token)
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pi=\"http://pi.ssm.com.my\">\n    <soapenv:Header/>\n    <soapenv:Body>\n        <pi:getPersonalInvolvementRoc>\n            <!--Optional:-->\n            <header>\n                <!--Optional:-->\n                <customerId>NFCC</customerId>\n                <!--Optional:-->\n                <customerReferenceNo>MSB test</customerReferenceNo>\n                <!--Optional:-->\n                <customerRequestDate>2022-08-09 12:20:00T</customerRequestDate>\n            </header>\n            <!--Optional:-->\n            <request>\n                <!--Optional:-->\n                <personalInvolvementRocReq>\n                    <!--Optional:-->\n                    <designation>V</designation>\n                    <!--Optional:-->\n                    <idNo>"+model.getKeyword()+"</idNo>\n                    <!--Optional:-->\n                    <idType>MK</idType>\n                    <!--Optional:-->\n                    <tableId>ROCINFO_AUDITOR</tableId>\n                </personalInvolvementRocReq>\n            </request>\n        </pi:getPersonalInvolvementRoc>\n    </soapenv:Body>\n</soapenv:Envelope>")
                .asString();

        String xml = response.getBody();
        try {

            JSONObject json = XML.toJSONObject(xml);
            json
                    .getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("pi:getPersonalInvolvementRocResponse")
                    .remove("request");
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

        }catch (Exception e) {

            analytics.error(e.getMessage());
        }
    }

}