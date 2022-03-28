package com.fantasy.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author jingc
 * @description JobClient
 * @since 2022/3/7
 */
@FeignClient(name ="job-application",path = "/api/job")
public interface JobClient {

    @PostMapping("/test")
    @ResponseBody
    String registerJob(@RequestParam String id);
}
