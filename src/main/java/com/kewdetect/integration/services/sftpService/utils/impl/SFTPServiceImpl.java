package com.kewdetect.integration.services.sftpService.utils.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kewdetect.integration.services.sftpService.utils.ISFTPService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;


@Service
public class SFTPServiceImpl implements ISFTPService {

//    @Autowired
//    @Value("sftp.path")
     private String path = "/opt/nfis/pending/";
//    @Autowired
//    @Value("sftp.host")
     private String host = "47.241.58.103";

//    @Autowired
//    @Value("sftp.port")
     private String port = "22";

//    @Autowired
//    @Value("sftp.user")
     private String user = "root";

//    @Autowired
//    @Value("sftp.password")
     private String password = "KmRoot@@";


    @Override
    public void export2ftp(String data, JSONObject metadata) throws Exception{



            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host,Integer.parseInt(port));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();


            String uuid = UUID.randomUUID().toString();

            sftpChannel.mkdir(path + uuid);

            InputStream stream = new ByteArrayInputStream(data.getBytes());
            sftpChannel.put(stream,   path + uuid + "/data.json");
            metadata.put("data_file_path",path + uuid + "/data.json");

            InputStream metadataStream = new ByteArrayInputStream(metadata.toString().getBytes());
            sftpChannel.put(metadataStream,   path + uuid + "/metadata.json");



            sftpChannel.disconnect();
            session.disconnect();


    }



    public static void main (String[] args){

        SFTPServiceImpl example = new SFTPServiceImpl();
//        example.getsftprequest();
    }


}
