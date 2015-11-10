package com.rtve.core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by mdomonic on 10/8/2015.
 */
@Root
public class xmeml
{
    @Element
    private Project project;

    @Attribute
    private int version;

    public xmeml(Project project, int version)
    {
        this.project = project;
        this.version = version;
    }
}
