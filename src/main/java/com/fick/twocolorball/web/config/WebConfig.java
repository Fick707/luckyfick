package com.fick.twocolorball.web.config;

import com.fick.twocolorball.web.constants.WebConstants;
import com.fick.twocolorball.web.filter.SSOFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @name: WebConfig
 * @program: twocolorball
 * @description: web配置
 * @author: figo.song
 * @created: 2019/12/11
 **/
@Configuration
public class WebConfig {

    @Autowired
    SSOFilter ssoFilter;

    @Bean(name = "ssoFilterRegistrationBean")
    public FilterRegistrationBean ssoFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ssoFilter);
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>(5);
        initParameters.put(WebConstants.CommonConstants.FILTER_INIT_PARAM_EXCLUSIONS,
                        "/api/user/login"
                        );
        registration.setInitParameters(initParameters);
        registration.setName("ssoFilter");
        registration.setOrder(1);

        return registration;
    }

}
