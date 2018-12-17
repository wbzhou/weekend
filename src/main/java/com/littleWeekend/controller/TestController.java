package com.littleWeekend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.domain.City;
import com.littleWeekend.domain.common.CodeMsgResp;
import com.littleWeekend.service.UserService;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public String findByName(@RequestParam(value = "userName", required = true) String userName) {
        return userService.findByName(userName);
    }
    
    @RequestMapping(value = "/junting", method = RequestMethod.GET)
    public String junting(@RequestParam(value = "userName", required = true) String userName) {
    	City city=new City();
    	city.setCityName(userName);
        return JSONObject.toJSONString(city);
    }
    
    @RequestMapping(value="/testMe", method=RequestMethod.POST)
	  public @ResponseBody CodeMsgResp cartTagInfo(){
			CodeMsgResp resp = new CodeMsgResp();
			String result="";
		  try {
			  String url ="http://59.110.160.96:8080/int_ticket/user/notify/fromBus";
			  
		  } catch (Exception e){
		  }
		  resp.setData(result);
		  return resp;
	  }

}
