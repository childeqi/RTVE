package com.rtve.core;

import com.rtve.core.Children;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/8/2015.
 */
public class Project
{
    @Element
    private String name;

    @Element
    private Children children;

    public Project(Children children, String name)
    {
        this.name = name;
        this.children = children;
    }
}
