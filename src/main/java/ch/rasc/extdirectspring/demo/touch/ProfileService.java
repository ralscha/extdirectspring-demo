package ch.rasc.extdirectspring.demo.touch;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.extdirectspring.demo.form.BasicInfo;

@Service
public class ProfileService {

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_LOAD, group = "touchdirect")
	public BasicInfo getBasicInfo(@RequestParam(value = "uid", required = false) String uid) {
		System.out.println("UID: " + uid);
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setFoo(uid);
		basicInfo.setName("Aaron Conran");
		basicInfo.setCompany("Ext JS, LLC");
		basicInfo.setEmail("aaron@extjs.com");
		return basicInfo;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "touchdirect")
	public ExtDirectFormPostResult updateBasicInfo(@Valid BasicInfo basicInfo, BindingResult result) {

		System.out.println(basicInfo);

		if (!result.hasErrors()) {
			if (basicInfo.getEmail().equals("aaron@extjs.com")) {
				result.rejectValue("email", null, "email already taken");
			}
		}

		return new ExtDirectFormPostResult(result);
	}
}
