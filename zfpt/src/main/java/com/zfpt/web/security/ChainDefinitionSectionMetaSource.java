package com.zfpt.web.security;

import java.util.List;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.zfpt.framework.context.LoginManger;
import com.zfpt.framework.util.StringUtils;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.service.system.IResourceService;
import com.zfpt.web.service.system.IRoleService;
import com.zfpt.web.service.system.IUserService;

/**
 * 产生责任链，确定每个url的访问权限
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

	@Autowired
	private IResourceService resourceService;
	
	// 静态资源访问权限
	private String filterChainDefinitions = null;

	public Ini.Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		List<MenuTreeObject> resources=resourceService.findResourcesByParentId(0);
    	if(resources!=null&&resources.size()>0){
    	   for(MenuTreeObject menuTreeObject : resources){
    		   /**遍历资源信息 **/
 			   if(StringUtils.isNotEmpty(menuTreeObject.getHref())) { 
 					String permission = "perms[" + menuTreeObject.getHref() + "]";
 					System.out.println(permission);
 					section.put(menuTreeObject.getHref(), permission);
 				}
		   }	
    	}
		// 所有资源的访问权限，必须放在最后
		/*section.put("/**", "authc");*/
		section.put("/**", "authc,kickout,user");
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
