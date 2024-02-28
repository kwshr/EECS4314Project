package com.eauction.itemcatalogueservice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseStatus {
    public static ResponseEntity<Map<String,Object>> setResponse(Map<String,Object> response){
        HttpStatus status = HttpStatus.PROCESSING;
        if(response.get("queryStatus")== ItemCatalogueQueryResultStatus.SUCCESS)
        status = HttpStatus.OK;
        else if(response.get("queryStatus")== ItemCatalogueQueryResultStatus.NOT_FOUND)
        status = HttpStatus.NOT_FOUND;
        else if(response.get("queryStatus")== ItemCatalogueQueryResultStatus.ERROR)
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        else
        status = HttpStatus.NOT_IMPLEMENTED;
        response.put("status", status);
        return ResponseEntity.status(status).body(response);
    }
}
