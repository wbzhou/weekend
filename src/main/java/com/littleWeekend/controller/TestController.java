package com.littleWeekend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littleWeekend.service.IActivityService;

@RestController
public class TestController {

    @Autowired
    private IActivityService IActivityService;

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public String findByActivityId(@RequestParam(value = "activityId", required = true) String activityId) {
        return IActivityService.checkActivity(activityId);
    }
    
//    @RequestMapping(value = "/junting", method = RequestMethod.GET)
//    public String junting(@RequestParam(value = "userName", required = true) String userName) {
//    	City city=new City();
//    	city.setCityName(userName);
//        return JSONObject.toJSONString(city);
//    }
    
//    @RequestMapping(value="/testMe", method=RequestMethod.POST)
//	  public @ResponseBody CodeMsgResp cartTagInfo(){
//			CodeMsgResp resp = new CodeMsgResp();
//			String result="";
//		  try {
//			  String url ="http://59.110.160.96:8080/int_ticket/user/notify/fromBus";
//
//		  } catch (Exception e){
//		  }
//		  resp.setData(result);
//		  return resp;
//	  }

}
