package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Media
{
    @Element
    private Video video;

    public Media(Video video)
    {
        this.video = video;
    }
}
