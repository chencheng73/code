package com.zfpt.web.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IUserService;
 


public class SysUserFilter extends PathMatchingFilter {

	@Autowired
	private IUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
       // request.setAttribute("user", userService.findByNames(userFormMap));
        return true;
    }
}