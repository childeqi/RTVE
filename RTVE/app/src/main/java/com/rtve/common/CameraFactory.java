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
         case TIGHT:
            return new TightLensCameraConfig(name);

         case WIDE:
            return new WideLensCameraConfig(name);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }

   public static CameraConfig loadCamera(int number, String name, LensType lens)
   {
      switch (lens)
      {
         case TIGHT:
            return new TightLensCameraConfig(name, number);

         case WIDE:
            return new WideLensCameraConfig(name, number);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }
}
