package com.littleWeekend.domain;

/**
 * @author: create by junting
 * @version: v1.0
 * @description:
 * @date:2019/3/31
 */
public class TestEntry extends  BaseBean{
    private boolean run;
    private String param;

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
