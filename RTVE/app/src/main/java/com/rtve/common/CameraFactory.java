package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public class CameraFactory
{
   public static CameraConfig createCamera(String name, boolean timeLimited, LensType lens)
   {
      switch (lens)
      {
         case TIGHT:
            return new TightLensCameraConfig(name, timeLimited);

         case WIDE:
            return new WideLensCameraConfig(name, timeLimited);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }

   public static CameraConfig loadCamera(int number, String name, boolean timeLimited, LensType lens)
   {
      switch (lens)
      {
         case TIGHT:
            return new TightLensCameraConfig(name, timeLimited, number);

         case WIDE:
            return new WideLensCameraConfig(name, timeLimited, number);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }
}
