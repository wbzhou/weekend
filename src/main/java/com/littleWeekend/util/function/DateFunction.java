package com.littleWeekend.util.function;


import com.littleWeekend.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFunction  implements Function{

	@Override
	public String execute(String[] args) {
		if (args.length == 0 || StringUtil.isEmpty(args[0])) {
			return String.format("%s", new Date().getTime());
		} else {
			return getCurrentDate("yyyy-MM-dd");
		}
	}

	private String getCurrentDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(new Date());
		return str;
	}
	
	@Override
	public String getReferenceKey() {
		// TODO Auto-generated method stub
		return "date";
	}

}
