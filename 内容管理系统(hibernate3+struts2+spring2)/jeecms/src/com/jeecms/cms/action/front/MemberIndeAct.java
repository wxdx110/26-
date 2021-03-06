package com.jeecms.cms.action.front;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeecms.cms.CmsMemberAction;
import com.jeecms.core.entity.User;
import com.ponyjava.common.hibernate3.Updater;
import com.ponyjava.common.hibernate3.Updater.UpdateMode;
import com.ponyjava.common.util.PwdEncoder;

/**
 * 会员独立模板类
 * 
 * @author liufang
 * 
 */
@Scope("prototype")
@Controller("cms.memberIndeAct")
public class MemberIndeAct extends CmsMemberAction {
	public static final String MEMBER_CENTER = "MemberCenter";

	/**
	 * 会员中心首页
	 * 
	 * @return
	 */
	public String memberCenter() {
		String result = checkLoginAndError();
		if (result != null) {
			return result;
		}
		return handleResult(MEMBER_CENTER);
	}

	public String memberInfo() {
		String result = checkLoginAndError();
		if (result != null) {
			return result;
		}
		return handleResult("MemberInfo");
	}

	public String memberInfoUpdate() {
		String result = checkLoginAndError();
		if (result != null) {
			return result;
		}
		if (userBean != null) {
			userBean.setId(getUserId());
			Updater userUd = Updater.create(userBean, UpdateMode.MIN);
			userUd.include(User.PROP_GENDER);
			userUd.include(User.PROP_REAL_NAME);
			userMng.updateByUpdater(userUd);
		}
		addActionMessage("个人信息修改成功");
		return showSuccess();
	}

	public String memberPwd() {
		String result = checkLoginAndError();
		if (result != null) {
			return result;
		}
		return handleResult("MemberPwd");
	}

	public boolean vldMemberPwdUpdate() {
		if (hasErrors()) {
			return true;
		}
		if (!pwdEncoder.isPasswordValid(getUser().getPassword(), password)) {
			addActionError("密码错误");
			return true;
		}
		// 邮箱是否已经被使用
		if (!StringUtils.equals(email, getUser().getEmail())
				&& !userMng.checkEmail(email)) {
			addActionError("email已经被使用");
			return true;
		}
		return false;
	}

	public String memberPwdUpdate() {
		String result = checkLoginAndError();
		if (result != null) {
			return result;
		}
		if (vldMemberPwdUpdate()) {
			return showError();
		}
		userMng.updatePwdEmail(getUser(), password, newPassword, email);
		addActionMessage("密码和邮箱修改成功");
		return showSuccess();
	}

	@Autowired
	private PwdEncoder pwdEncoder;
	private User userBean;
	private String password;
	private String newPassword;
	private String email;

	public User getUserBean() {
		return userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}