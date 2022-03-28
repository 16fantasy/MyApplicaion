package com.fantasy.config.feignconfig;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author jingc
 * @description  feign 配置类，进行请求之前进行请求头传递
 * @since 2022/3/14
 */

@Configuration
public class FeignConfiguration {

    /**
     * 在请求之前讲原有的请求头依次传递下去
     * @return
     */
    @Bean
    public RequestInterceptor interceptor() {
        return (requestTemplate) -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (!Objects.isNull(headerNames)) {
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    requestTemplate.header(headerName, headerValue);
                }
            }
        };
    }
}
