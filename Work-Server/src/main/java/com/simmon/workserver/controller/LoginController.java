package com.simmon.workserver.controller;

import com.simmon.workserver.domain.Admin;
import com.simmon.workserver.domain.AdminLoginParam;
import com.simmon.workserver.domain.RespBean;
import com.simmon.workserver.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author simmon
 */

@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService iadminservice;

    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return iadminservice.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }


    @ApiModelProperty(value = "获取当前登陆用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null == principal){
            return null;
        }
        String username=principal.getName();
        Admin admin=iadminservice.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(iadminservice.getRoles(admin.getId()));
        return admin;
    }

    @ApiModelProperty(value = "退出登陆")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("退出成功");
    }
}
