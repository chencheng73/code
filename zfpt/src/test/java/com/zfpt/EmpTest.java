package com.zfpt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zfpt.framework.filter.Pager;
import com.zfpt.web.model.system.Employee;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IEmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml"})
public class EmpTest {
	@Autowired
	public IEmployeeService employeeService;
	
	@Test
	public void testSaveEmployee(){
		Employee emp = new Employee();
		emp.setAddress("北京市海淀区");
		emp.setBirthday(1449021264122L);
		emp.setHireDate(1449021264155L);
		emp.setLeavingDate(1449021269999L);
		emp.setDeptId(101L);
		emp.setName("李斯");
		emp.setEmail("sw@qq.com");
		emp.setTele("188168888");
		emp.setGender(1);
		int result = employeeService.saveEmployee(emp);
		System.out.println(result);
	}
	
	@Test
	public void testFindEmployeeByEmpId(){
		 Employee employeeEmp = employeeService.findEmployeeByEmpId(1L);
		 System.out.println(employeeEmp);
	}
	
	@Test
	public void testqueryForPage(){
		Pager<List<Employee>> pager=new Pager<List<Employee>>();
        pager.setPageNo(2);
        pager.setPageSize(1);
		Pager<List<Employee>> queryForPage = employeeService.queryForPage(pager);
		List<Employee> empList = queryForPage.getResult();
		System.out.println("empList.size:"+empList.size());
		System.out.println("TotalPages="+queryForPage.getTotalPages());
		System.out.println("queryForPage.getFirst()="+queryForPage.getFirst());
		System.out.println(empList);
	}
	
	@Test
	public void testUpdateEmployee(){
		Employee emp = new Employee();
		emp.setEmpployeeId(1L);
		emp.setAddress("福建省三明市沙县");
		emp.setDeptId(102L);
		emp.setName("张三新");
		emp.setHireDate(1444653920104L);
		emp.setLeavingDate(1455653920104L);
		emp.setBirthday(1444653920104L);
		emp.setGender(1);
		emp.setEmail("admin@syswin.com");
		emp.setTele("16816816888");
		emp.setIsDelete(1);
		Employee oldEmployee = employeeService.findEmployeeByEmpId(1L);
		System.out.println("修改前："+oldEmployee);
		int result = employeeService.updateEmployee(emp);
		Employee newEmployee = employeeService.findEmployeeByEmpId(1L);
		System.out.println(result);
		System.out.println(newEmployee);
	}
	
	@Test
	public void testDeleteEmployeeByEmpId(){
		Employee oldEmployee = employeeService.findEmployeeByEmpId(1L);
		System.out.println("删除前："+oldEmployee);
		int result = employeeService.deleteEmployeeByEmpId(1L);
		System.out.println(result);
		Employee newEmployee = employeeService.findEmployeeByEmpId(1L);
		System.out.println("删除后："+newEmployee);
	}
	
	@Test
	public void fun1(){
		System.out.println(System.currentTimeMillis());
		System.out.println();
	}

}
