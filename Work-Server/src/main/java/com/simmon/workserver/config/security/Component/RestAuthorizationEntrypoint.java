package com.simmon.workserver.config.security.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simmon.workserver.domain.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Simmon
 */

@Component
public class RestAuthorizationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        RespBean bean=RespBean.error("未登录请登录");
        bean.setCode(401);
        writer.write(new ObjectMapper().writeValueAsString(bean));
        writer.flush();
        writer.close();
    }
}
