package com.zfpt;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.util.MD5Utils;
import com.zfpt.test.TestSupport;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IResourceService;
import com.zfpt.web.service.system.IUserService;

public class DbTest extends TestSupport {

	@Autowired
	private IUserService userService;
	@Autowired
	private IResourceService resourceService;
	
	//新增
	//@Test
	public void testAddUser(){
		User user=new User();
		user.setUserName("test");
		user.setUserPassword(MD5Utils.getMD5ofStr("test"));
		userService.insert(user);
		logger.info("新增成功!"+user.getId());
		
	}
	
	//@Test
	public void testUpdateUser(){
		User user=userService.findUserById(3);
		user.setUserName("charles");
		user.setEmail("123@qq.com");
		userService.updateUser(user);
	}
	
	//分页查询
	//@Test
	public void testQueryForPage(){
		Pager<List<User>> pager=new Pager<List<User>>();
        pager.setPageNo(1);
        pager.setPageSize(15);
		pager=userService.queryForPage(pager);
		List<User> list=pager.getResult();
		logger.info("查询到"+list.size()+"条记录");
		if(list!=null&&list.size()>0){
		   for (User user : list) {
			 logger.info(user.getUserName()+"\t"+user.getUserPassword());
		   }
		}
	}
	
	//资源新增
	//@Test
	public void testAddResource(){
		Resource resource=new Resource();
		resource.setParentId(3);
		resource.setResName("部门管理");
		resource.setResUrl("/system/deptment/listPage");
		resource.setLevel(3);
		resourceService.saveResource(resource);
		logger.info("新增成功!");
		
	}
	
	//资源修改
	@Test
	public void testUpdateResource(){
		Resource resource=resourceService.findResourceById(11);
		resource.setResUrl("/system/deptments/listPage");
		resource.setLevel(3);
		resourceService.updateResource(resource);
		logger.info("修改成功!");
		
	}
	
	@Test
	public void testDeleteResource(){
		resourceService.deleteResourceById(4);
		logger.info("删除成功!");
	}
	
	
	
}
