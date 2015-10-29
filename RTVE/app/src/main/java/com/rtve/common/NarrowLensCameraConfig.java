package com.rtve.common;

import android.os.Parcel;

/**
 * Created by Matt on 10/10/2015.
 */
public class NarrowLensCameraConfig extends CameraConfig
{
   public NarrowLensCameraConfig(String name)
   {
      super(name);
   }

   public NarrowLensCameraConfig(String name, int number)
   {
      super(name, number);
   }

   protected NarrowLensCameraConfig(Parcel in)
   {
      super(in);
   }

   @Override
   public LensType getLensType()
   {
      return LensType.NARROW;
   }
}
