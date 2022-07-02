package com.simmon.workserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simmon.workserver.config.security.Component.JwtTokenUtil;
import com.simmon.workserver.domain.Admin;
import com.simmon.workserver.domain.AdminRole;
import com.simmon.workserver.domain.RespBean;
import com.simmon.workserver.domain.Role;
import com.simmon.workserver.mapper.AdminMapper;
import com.simmon.workserver.mapper.AdminRoleMapper;
import com.simmon.workserver.mapper.RoleMapper;
import com.simmon.workserver.service.IAdminService;
import com.simmon.workserver.util.AdminUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AdminRoleMapper adminRoleMapper;

    /**
     * 登录之后返回token
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @param request 请求
     * @return 登陆是否成功
     */
    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
         String captcha = (String)request.getSession().getAttribute("captcha");
         if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
             return RespBean.error("验证码不正确!");
         }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用请联系管理员");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
                userDetails,null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,Object> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);

        return RespBean.success("登陆成功",tokenMap);
    }

    /**
     * 更具用户名获取用户
     * @param username 用户名
     * @return Admin对象
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(),keywords);
    }

    @Override
    @Transactional
    public RespBean updateAminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.addAdminRoles(adminId, rids);
        if (rids.length==result){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if (encoder.matches(oldPass,admin.getPassword())) {
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if (1==result){
                return RespBean.success("密码修改成功");
            }
        }
        return RespBean.error("密码修改失败");
    }

    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if (1==result){
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功",url);
        }
        return RespBean.error("更新失败");
    }
}
