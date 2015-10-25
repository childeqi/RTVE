package com.rtve.core.storage;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by Matt on 10/24/2015.
 */
public class Configuration
{
   @ElementList(inline = true)
   private List<Camera> cameras;

   public Configuration(List<Camera> cameras)
   {
      this.cameras = cameras;
   }

   List<Camera> getCameras()
   {
      return cameras;
   }
}
