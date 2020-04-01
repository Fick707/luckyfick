package com.fick.twocolorball.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.fick.common.utils.good.RegexUtil;
import com.fick.twocolorball.service.UserService;
import com.fick.twocolorball.web.constants.WebConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * @Description: 登录验证过滤器
 * @Author: figo.song
 * @CreatedAt: 2019/12/19
 */
@Component
@Slf4j
public class SSOFilter implements Filter {

    @Resource
    UserService userService;

    private String[]            exclusions;
    private Pattern[]           exclusionPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("sso Filter init...");
        String exclusionStr = filterConfig.getInitParameter(WebConstants.CommonConstants.FILTER_INIT_PARAM_EXCLUSIONS);
        if (StringUtils.isNotBlank(exclusionStr)) {
            exclusions = exclusionStr.split(WebConstants.CommonConstants.COMMA);
            exclusionPatterns = RegexUtil.compilePatterns(exclusions);
        }
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) _req;

        String url = request.getRequestURI().replace(request.getContextPath(),"");

        // 如果请求在例外列表里，则直接放过
        boolean matched = matchExclusionUrl(url);
        if (matched) {
            chain.doFilter(_req, _res);
            return;
        }

        String token = request.getHeader(WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN);
        if(StringUtils.isBlank(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0) {
                for(Cookie cookie : cookies){
                    if(StringUtils.equalsIgnoreCase(cookie.getName(),WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN)){
                        token = cookie.getValue();
                    }
                }
            }
        }
        if(StringUtils.isBlank(token)){
            unAuthorized(_res);
            return;
        }
        if(! userService.checkToken(token)){
            unAuthorized(_res);
            return;
        }
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
        log.info("destroy SSO Filter...");
    }

    private boolean matchExclusionUrl(String url) {
        return RegexUtil.isMatched(exclusionPatterns, url);
    }

    protected void unAuthorized(ServletResponse response) throws IOException {
        ((HttpServletResponse) response).setStatus(401);
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter pw = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("code", 401);
        result.put("msg", "login first please.");
        result.put("isSuccess",false);
        pw.write(result.toJSONString());
    }
}
