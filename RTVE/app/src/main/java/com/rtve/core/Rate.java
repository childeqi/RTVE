package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Rate
{
    @Element
    public int timebase;

    @Element
    public boolean ntsc;

    public Rate(int timebase, boolean ntsc)
    {
        this.timebase = timebase;
        this.ntsc = ntsc;
    }
}
