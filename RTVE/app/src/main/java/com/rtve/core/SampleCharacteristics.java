package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class SampleCharacteristics
{
    @Element
    private Rate rate;

    @Element
    private int width;

    @Element
    private int height;

    public SampleCharacteristics(Rate rate, int width, int height)
    {
        this.rate = rate;
        this.width = width;
        this.height = height;
    }
}
