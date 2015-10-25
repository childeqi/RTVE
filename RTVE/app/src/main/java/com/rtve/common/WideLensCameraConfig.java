package com.rtve.common;

import android.os.Parcel;

/**
 * Created by Matt on 10/10/2015.
 */
public class WideLensCameraConfig extends CameraConfig
{
   public WideLensCameraConfig(String name)
   {
      super(name);
   }

   public WideLensCameraConfig(String name, int number)
   {
      super(name, number);
   }

   protected WideLensCameraConfig(Parcel in)
   {
      super(in);
   }

   @Override
   public LensType getLensType()
   {
      return LensType.WIDE;
   }
}
