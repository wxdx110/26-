package com.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.java.Roletb;
import com.java.Xxuser;
import com.opensymphony.xwork2.ActionSupport;
import com.service.intface.user.UserService;

public class RoledeleAction extends ActionSupport {
	private UserService userService;
	private List message;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest re = ServletActionContext.getRequest();
		String[] a =re.getParameterValues("Id");
		System.out.println(a[0]);
		Roletb pr=new Roletb();
		if(a[0]!=null){
			pr = userService.QueryRoletbById(new Long(a[0].trim()));
			userService.deleRoletb(pr);
			addActionMessage("数据删除成功！");
		}else{
			System.out.println("删除用户信息时出错！");
		}
		return SUCCESS;
	}
	public String edit() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest re = ServletActionContext.getRequest();
		String[] a =re.getParameterValues("Id");
		System.out.println(a[0]);
		Roletb pr=new Roletb();
		if(a[0]!=null){
			pr = userService.QueryRoletbById(new Long(a[0].trim()));
		} 
		List l=new ArrayList();
		l.add(pr);
		setMessage(l);
		return SUCCESS;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getMessage() {
		return message;
	}
	public void setMessage(List message) {
		this.message = message;
	}
	
}
