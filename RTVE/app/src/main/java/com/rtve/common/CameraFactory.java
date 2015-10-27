package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public class CameraFactory
{
   public static CameraConfig createCamera(String name, LensType lens)
   {
      switch (lens)
      {
         case NARROW:
            return new NarrowLensCameraConfig(name);

         case WIDE:
            return new WideLensCameraConfig(name);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }
}
