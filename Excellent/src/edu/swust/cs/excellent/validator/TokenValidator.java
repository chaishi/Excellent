package edu.swust.cs.excellent.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class TokenValidator extends Validator{

	@Override
	protected void validate(Controller c) {
	//	validateToken("Token", "msg", "alert('请不要重复提交')");
	}
	@Override
	protected void handleError(Controller c) {
	
	}

}
