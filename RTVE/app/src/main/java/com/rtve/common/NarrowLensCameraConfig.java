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

   protected NarrowLensCameraConfig(Parcel in)
   {
      super(in);
   }

   @Override
   protected LensType getLensType()
   {
      return LensType.NARROW;
   }
}
