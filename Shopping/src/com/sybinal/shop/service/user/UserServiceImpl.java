package com.sybinal.shop.service.user;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.AccountMapper;
import com.sybinal.shop.mapper.UserMapper;
import com.sybinal.shop.mapper.UserRolesMapper;
import com.sybinal.shop.model.Account;
import com.sybinal.shop.model.User;
import com.sybinal.shop.model.UserExample;
import com.sybinal.shop.model.UserRoles;
import com.sybinal.shop.utils.Constants;
import com.sybinal.shop.utils.PagingTool;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserRolesMapper userRolesMapper;

	@Autowired
	PagingTool pagingTool;
	
	@Autowired
	AccountMapper accountMapper;

	@Override
	@Transactional
	public int saveUser(User user) throws IllegalAccessException, InvocationTargetException {
		int cnt = 0;
		if (user != null) {
			Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
			user.setEnabled(1);
			user.setRegisterTime(new Date());
			user.setLastLoginTime(new Date());
			String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
			user.setPwd(encodePassword);
			cnt += userMapper.insert(user);
			
			Account account = new Account();
			account.setUsername(user.getUserName());
			account.setSource("注册赠送");
			account.setUpdTime(new Date());
			System.out.println("shopping--userServiceImpl--amount："+user.getAmount());
			account.setAmount(user.getAmount());
			accountMapper.saveAccount(account);
			
			UserRoles userRoles = new UserRoles();
			userRoles.setUsername(user.getUserName());
			userRoles.setRole(Constants.ROLE_USER);
			cnt += userRolesMapper.insert(userRoles);

		}
		return cnt;
	}


	@Override
	@Transactional
	public int modUser(User user) {
		int cnt = 0;
		if (user != null) {
			if (StringUtils.isEmpty(user.getPwd()) == false) {
				Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
				String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
				user.setPwd(encodePassword);
			}
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(user.getId());
			cnt = userMapper.updateByExampleSelective(user, example);
		}
		return cnt;
	}


	@Override
	@Transactional
	public int modUserPassword(Map<String, Object> reqMap) {
		int cnt = 0;
		User user = new User();
		if (reqMap != null) {
			if (!StringUtils.isEmpty((String) reqMap.get("rpassword"))) {
				Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
				String encodePassword = md5PasswordEncode.encodePassword((String) reqMap.get("rpassword"), null);
				user.setPwd(encodePassword);
			}
			String oldpassword = null;
			if (!StringUtils.isEmpty((String) reqMap.get("oldpassword"))) {
				Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
				oldpassword = md5PasswordEncode.encodePassword((String) reqMap.get("oldpassword"), null);
			}
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo((int) reqMap.get("userId"));
			criteria.andPwdEqualTo(oldpassword);
			cnt = userMapper.updateByExampleSelective(user, example);
		}
		return cnt;
	}

	@Override
	@Transactional
	public int recoveryUserPassword(User user) {
		int cnt = 0;
		if (user != null) {
			if (!StringUtils.isEmpty(user.getPwd()) && user.getPwd().equals(user.getConfirmPassword())) {
				Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
				String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
				user.setPwd(encodePassword);
			}
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.andUserNameEqualTo(user.getUserName());
			cnt = userMapper.updateByExampleSelective(user, example);
		}
		return cnt;
	}
	
	@Override
	@Transactional
	public Map<String, Object> delUser(Map<String, Object> data) {

		return null;
	}
//用户登录 获取信息
	@Override
	public User getUser(User user) {
		//System.err.println("=================================================");
		//System.err.println("user:"+user.getUserName()+"  pwd:"+user.getPwd());
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(user.getUserName());
		if (StringUtils.isEmpty(user.getPwd()) == false) {
			Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
			String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
			criteria.andPwdEqualTo(encodePassword);
			User updateUser = new User();
			//更新用户登陆事件
			updateUser.setLastLoginTime(new Date());
			userMapper.updateByExampleSelective(updateUser, example);
		}
		List<User> userlist = userMapper.selectByExample(example);
		//System.err.println("=================================================");
		//System.err.println("登陆者信息:"+userlist.get(0).getUserName());
		if (userlist != null && userlist.size() > 0) {
			return userlist.get(0);
		}
		return null;
	}

	@Override
	public User getUser(Map<String, Object> reqMap) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo((int) reqMap.get("userId"));
		List<User> userlist = userMapper.selectByExample(example);
		if (userlist != null && userlist.size() > 0) {
			User user = new User();
			user.setId(userlist.get(0).getId());
			user.setNickname(userlist.get(0).getNickname());
			user.setEmail(userlist.get(0).getEmail());
			return user;
		}
		return null;
	}

	@Override
	public Map<String, Object> getUserInfoByCondition(PageInformation pageInfo, User user) {
		
		int pageCount = userMapper.selectCountByCondition(user);
//		System.err.println("=================================================");
//		System.err.println("user:"+pageCount);
		List<User> listUser = pagingTool.PagingData("com.sybinal.shop.mapper.UserMapper.selectUserByCondition", user,
				(Integer.parseInt(pageInfo.getiDisplayStart())/Integer.parseInt(pageInfo.getiDisplayLength()) +1),Integer.parseInt(pageInfo.getiDisplayLength()));

		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listUser);

		return datas;
	}
	@Override
	public User getUserById(Integer userId) {
		return userMapper.selectById(userId);
	}

	@Override
	@Transactional
	public int saveUserForManage(User user) throws IllegalAccessException, InvocationTargetException {
		int cnt = 0;
		if (user != null) {
			Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
			user.setEnabled(1);
			user.setRegisterTime(new Date());
			user.setLastLoginTime(new Date());
			String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
			user.setPwd(encodePassword);
			cnt += userMapper.insert(user);
			
			UserRoles userRoles=new UserRoles();
			userRoles.setUsername(user.getUserName());
			//userRoles.setRole(userRoles.getRole());
			userRoles.setRole(user.getRole());
			cnt += userRolesMapper.insert(userRoles);

		}
		return cnt;
	}

	@Override
	@Transactional
	public int updateUserForManage(User user) {
		int cnt = 0;
		if (user != null) {
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(user.getId());
			Md5PasswordEncoder md5PasswordEncode = new Md5PasswordEncoder();
			String encodePassword = md5PasswordEncode.encodePassword(user.getPwd(), null);
			user.setPwd(encodePassword);
			cnt = userMapper.updateByExampleSelective(user, example);
		}
		return cnt;
	}
	
	
	
	/**
	 * 添加时验证用户名是否重复
	 */
	@Override
	public int checkAddUserName(String userName) {
		return userMapper.selectCountUserNameByCondition(userName);
	}
	
	
	/**
	 * 修改时验证用户名是否重复
	 */
	@Override
	public int checkUpdateUserName(User record) {
		return userMapper.selectCountUserNameByUpdate(record);
	}
	
	
	
	
}
