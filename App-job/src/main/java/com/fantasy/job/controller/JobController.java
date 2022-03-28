package com.fantasy.job.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingc
 * @description JobController
 * @since 2022/3/13
 */

@RestController()
@RequestMapping(value = "/api/job")
public class JobController {

    @PostMapping("/test")
    public String testRequest(@RequestParam String id) {
        return "success";
    }
}
