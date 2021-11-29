package com.mycompany.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.vo.Orders;

public class OrderFormValidator implements Validator{
	private static final Logger logger = LoggerFactory.getLogger(OrderFormValidator.class);

	/* parameter의 clazz에 Orders로 검증 가능지 확인 */
	@Override
	public boolean supports(Class<?> clazz) {
		/* 전달받은 clazz 객체가 Orders DTO로 검증이 가능하면 true, 불가능하면 false를 반환한다. */
		boolean checkDto = Orders.class.isAssignableFrom(clazz);
		return checkDto;
	}

	/* 검증가능한 경우(supports가 true를 return하는 경우) Validation 수행
	   target에는 검증할 객체가 담겨져있고, errors에는 검증이후 에러값이 담긴다. */
	@Override
	public void validate(Object target, Errors errors) {
		//target을 Orders 객체로 강제 형변환하여 받는다.
		Orders order = (Orders) target;
		
		//phone Validation
		String phone = order.getOphone();
		logger.info(phone);
		if(phone == null || phone.trim().equals("")) {//공백 체크
			errors.rejectValue("tel", "errors.tel.required");//필드에 대한 에러코드 할당
		}else {//정규 표현식에 맞는지 체크
			String regex = "^01([0|1|6|7|8|9]?)([0-9]{7,8})$";
			Pattern pattern = Pattern.compile(regex);//정규표현식을 pattern에 할당한다.
			Matcher matcher = pattern.matcher(phone);//검증 값이 정규표현식과 매칭되는지 확인한다.
			if(!matcher.matches()) {//정규 표현식과 일치하지 않을 경우
				errors.rejectValue("tel", "errors.tel.invalid");//필드에 대한 에러코드 할당
			}
		}
		
		//zipcode Validation
		//비어있지 않고 5-6자리의 숫자 또는 숫자'-'숫자의 조합
		String zipcode = order.getOzipcode();
		logger.info(zipcode);
		if(zipcode == null || zipcode.trim().equals("")) {//공백 체크
			errors.rejectValue("zipcode", "errors.zipcode.required");
		}else {
			String regex = "\\d{5,6}|\\d{2,3}-\\d{2,3}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(zipcode);
			if(!matcher.matches()) {
				errors.rejectValue("zipcode", "errors.zipcode.invalid");
			}
		}
		//address Validation
		//비어있을 경우 체크
		String address = order.getOaddress();
		logger.info(address);
		if(address == null || address.trim().equals("")) {//공백 체크
			errors.rejectValue("address", "errors.address.required");
		}
		
		//receiver Validation
		//비어있지 않고 한글이름 또는 영어'공백'영어 이름 또는 영어이름
		String receiver = order.getOreceiver();
		logger.info(receiver);
		if(receiver == null || receiver.trim().equals("")) {//공백 체크
			errors.rejectValue("receiver", "errors.receiver.required");
		}else {
			String regex = "^[ㄱ-ㅎ가-힣]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}|[a-zA-Z]{2,20}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(receiver);
			if(!matcher.matches()) {
				errors.rejectValue("receiver", "errors.receiver.invalid");
			}
		}
	}


}
