package com.kewdetect.integration.services.sftpService.utils;

import org.json.JSONObject;

public interface ISFTPService {

    public void export2ftp(String data, JSONObject metadata) throws Exception;
}
