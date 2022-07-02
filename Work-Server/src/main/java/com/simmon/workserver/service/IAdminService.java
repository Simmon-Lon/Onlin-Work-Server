package com.simmon.workserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simmon.workserver.domain.Admin;
import com.simmon.workserver.domain.Menu;
import com.simmon.workserver.domain.RespBean;
import com.simmon.workserver.domain.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Simmon
 * @since 2021-11-21
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password,String code, HttpServletRequest request);

    Admin getAdminByUserName(String username);

    List<Role> getRoles(Integer adminId);

    List<Admin> getAllAdmins(String keywords);

    RespBean updateAminRoles(Integer adminId, Integer[] rids);

    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);

    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);
}
