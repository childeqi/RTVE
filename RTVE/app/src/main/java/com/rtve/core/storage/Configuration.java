package com.rtve.core.storage;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Matt on 10/24/2015.
 */
@Root
public class Configuration
{
   @ElementList(name = "cameras")
   private final List<Camera> cameras;

   public Configuration(@ElementList(name = "cameras") List<Camera> cameras)
   {
      this.cameras = cameras;
   }

   List<Camera> getCameras()
   {
      return cameras;
   }
}
