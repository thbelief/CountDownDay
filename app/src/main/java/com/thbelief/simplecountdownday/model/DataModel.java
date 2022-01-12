package com.thbelief.simplecountdownday.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Author:thbelief
 * Date:2022/1/13 4:43 上午
 * Description:
 *
 * @author thbelief
 */
@Entity
public class DataModel {
    @Id(autoincrement = true)
    @Unique
    private Long id;
    private String title;
    private String date;
    private int unit;
    private boolean isClock;
    private int colorId;
    private String content;
    private boolean isDisplay;

    @Generated(hash = 1707356029)
    public DataModel(Long id, String title, String date, int unit, boolean isClock,
            int colorId, String content, boolean isDisplay) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.unit = unit;
        this.isClock = isClock;
        this.colorId = colorId;
        this.content = content;
        this.isDisplay = isDisplay;
    }

    @Generated(hash = 1794172823)
    public DataModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public boolean isClock() {
        return isClock;
    }

    public void setClock(boolean clock) {
        isClock = clock;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public boolean getIsClock() {
        return this.isClock;
    }

    public void setIsClock(boolean isClock) {
        this.isClock = isClock;
    }

    public boolean getIsDisplay() {
        return this.isDisplay;
    }

    public void setIsDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }
}
