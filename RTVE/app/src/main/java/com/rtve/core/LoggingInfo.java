package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class LoggingInfo
{
    @Element
    private String description;

    @Element
    private String scene;

    @Element
    private String shottake;

    @Element
    private String lognote;

    public LoggingInfo(String description, String scene, String shottake, String lognote)
    {
        this.description = description;
        this.scene = scene;
        this.shottake = shottake;
        this.lognote = lognote;
    }
}
