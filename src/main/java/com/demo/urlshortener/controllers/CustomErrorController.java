package com.demo.urlshortener.controllers;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> error(HttpServletRequest request) {
        try {
            JSONObject response = new JSONObject()
                   .put("Message", "ERROR")
                   .put("uri", ERROR_PATH)
                   .put("Status", getStatus(request));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        } catch (JSONException e) {
            return ResponseEntity.status(this.getStatus(request)).build();
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            }
            catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
