package com.sybinal.shop.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sybinal.shop.model.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmpty(errors, "userName", "username.empty", "");
		ValidationUtils.rejectIfEmpty(errors, "smsCode", "smscode.empty", "");
		ValidationUtils.rejectIfEmpty(errors, "pwd", "password.empty", "");
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword",
				"confirmPassword.empty", "");

		if (StringUtils.length(user.getUserName()) > 11) {
			errors.rejectValue("userName", "username.length");
		}
		if (StringUtils.length(user.getSmsCode()) > 6) {
			errors.rejectValue("smsCode", "smscode.length");
		}
		if (StringUtils.length(user.getPwd()) > 20) {
			errors.rejectValue("pwd", "password.length");
		}
		if (StringUtils.length(user.getConfirmPassword()) > 20) {
			errors.rejectValue("confirmPassword", "confirmPassword.length");
		}
		if (!user.getPwd().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword",
					"confirmPassword.inconformity");
		}
	}

}
