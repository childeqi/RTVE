package com.rtve;

/**
 * Created by Matt on 10/10/2015.
 */
public class CameraFactory
{
   public static Camera createCamera(String name, LensType lens)
   {
      switch (lens)
      {
         case NARROW:
            return new NarrowLensCamera(name);

         case WIDE:
            return new WideLensCamera(name);

         default:
            throw new IllegalArgumentException("Bad value given for LensType");
      }
   }
}
