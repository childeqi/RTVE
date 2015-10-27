package com.rtve.core;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Track
{
    @ElementList(inline=true)
    private List<ClipItem> clipItem;

    public Track(List<ClipItem> clipItem)
    {
        this.clipItem = clipItem;
    }
}
