package com.fantasy.controller;

import com.fantasy.entity.User;
import com.fantasy.feignclient.JobClient;
import com.fantasy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingc
 * @description SystemController
 * @since 2022/2/21
 */

@RestController
@RequestMapping("/system/")
public class SystemController {

    @Autowired
    JobClient jobClient;

    @Autowired
    private SystemService systemService;


    @PostMapping("login")
    public Map login(@RequestBody User user) {
        String token = systemService.login(user);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @GetMapping("test")
    public  String  testSecurity(){
        return  "success";
    }

    @PostMapping(value = "/call")
    public String testCall() {
        String result = jobClient.registerJob("123");
        return result;
    }
}
