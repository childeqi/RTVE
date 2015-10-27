package com.rtve.core;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Children
{
    @ElementList(inline=true)
    private List<Clip> clip;

    @Element
    private Sequence sequence;

    public Children(Sequence sequence, List<Clip> clip)
    {
        this.clip = clip;
        this.sequence = sequence;
    }
}
