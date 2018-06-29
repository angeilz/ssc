package com.angeilz.ssc.controller;

import com.angeilz.ssc.service.FiveFourService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FiveFourController {


    @Autowired
    FiveFourService service;

    @RequestMapping("chance")
    public String chance(@RequestParam("kjNumber") String kjNumber, @RequestParam("kjStage") String kjStage, @RequestParam("nextStage") String nextStage,
                         HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";
        try {
            jsonResult = mapper.writeValueAsString(service.isHaveChance(kjNumber, kjStage, nextStage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping("newId")
    public String getNewId(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return service.getNewId();
    }

}
