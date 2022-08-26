package com.kewdetect.integration.services.sftpService.utils.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kewdetect.integration.services.sftpService.utils.ISFTPService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;


@Service
public class SFTPServiceImpl implements ISFTPService {

    @Value("${sftp.path}")
     private String path;

    @Value("${sftp.host}")
     private String host;

    @Value("${sftp.port}")
     private String port;

    @Value("${sftp.user}")
     private String user;

    @Value("${sftp.password}")
     private String password;

    @Override
    public void export2ftp(String data, JSONObject metadata) throws Exception{
        String path1 = this.path == null ?  "/opt/nfis/pending/" : this.path;

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
