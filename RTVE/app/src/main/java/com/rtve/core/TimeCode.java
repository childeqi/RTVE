package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class TimeCode
{
    @Element
    private Rate rate;

    @Element
    private String string;

    @Element
    private int frame;

    public TimeCode(Rate rate, String string, int frame)
    {
        this.rate = rate;
        this.string = string;
        this.frame = frame;
    }
}
