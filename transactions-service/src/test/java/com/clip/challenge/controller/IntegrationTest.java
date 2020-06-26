package com.clip.challenge.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface IntegrationTest {

    default String readJsonFile(String fileName) throws JSONException, IOException {
        try {
            File fileObject = ResourceUtils.getFile("classpath:" + fileName);
            InputStream inputStream = new FileInputStream(fileObject);
            String jsonString = IOUtils.toString(inputStream, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.toString();
        } catch (IOException e) {
            System.out.format("NotFoundException, confirm %s is located at resources/.", fileName);
            throw e;
        }
    }
}
