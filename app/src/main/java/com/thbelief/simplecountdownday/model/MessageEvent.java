package com.thbelief.simplecountdownday.model;

/**
 * Author:thbelief
 * Date:2022/1/11 8:38 上午
 * Description:
 *
 * @author thbelief
 */
public class MessageEvent {
    private int what;
    private Object extra;

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public MessageEvent(int what, Object extra) {
        this.what = what;
        this.extra = extra;
    }

}
