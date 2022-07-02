package com.simmon.workserver.util;

import com.simmon.workserver.domain.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {

    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
