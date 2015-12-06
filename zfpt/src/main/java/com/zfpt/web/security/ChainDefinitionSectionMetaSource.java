package com.zfpt.web.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.zfpt.framework.util.StringUtils;
import com.zfpt.web.common.AppCons;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.service.system.IResourceService;
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
		 Set<String> menuSet=new HashSet<String>();
		 List<MenuTreeObject> resources=resourceService.findResourcesByParentId(0);
    	 if(resources!=null&&resources.size()>0){
    	   for(MenuTreeObject menuTreeObject : resources){
    		   /**遍历资源信息 **/
 			   if(StringUtils.isNotEmpty(menuTreeObject.getHref())) { 
 					//String permission = "perms[" + menuTreeObject.getHref() + "]";
 					//section.put(menuTreeObject.getId()+"", permission);
 					menuSet.add(menuTreeObject.getHref());
 				}
		   }
    	   AppCons.menuSet=menuSet;
    	}
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
