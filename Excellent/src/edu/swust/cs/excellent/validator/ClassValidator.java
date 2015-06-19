package edu.swust.cs.excellent.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class ClassValidator extends  Validator{

	@Override
	protected void validate(Controller c) {
		if ((!c.getPara("className").contains("软") && !c.getPara("className").contains("计"))
		     ||(c.getPara("className").contains("软") && c.getPara("className").contains("计"))){
			handleError(c);
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara();
		c.setAttr("success", false);
		c.setAttr("error", "班级名不符合规范,请指明计科或是软件");
		c.renderJson();
	}

}
