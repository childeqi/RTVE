package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Format
{
    @Element
    private SampleCharacteristics sampleCharacteristics;

    public Format(SampleCharacteristics sampleCharacteristics)
    {
        this.sampleCharacteristics = sampleCharacteristics;
    }
}
