package edu.swust.cs.excellent.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

import edu.swust.cs.excellent.util.WordValidatorUtil;

public class WordValidator extends Validator{

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		WordValidatorUtil wordValidatorUtil=new WordValidatorUtil();
		String txt=c.getPara("content");
		System.out.println(txt);
		if(txt!=null){
			Boolean bool =wordValidatorUtil.isContaintSensitiveWord(txt, 1);
			if(bool){
				handleError(c);
			}
		}
	}
	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepPara();
		c.setAttr("success", false);
		c.setAttr("error", "内容包含敏感词,请修正");
	}

}
