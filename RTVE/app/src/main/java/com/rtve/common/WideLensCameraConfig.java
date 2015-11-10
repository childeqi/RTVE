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

   protected WideLensCameraConfig(Parcel in)
   {
      super(in);
   }

   @Override
   protected LensType getLensType()
   {
      return LensType.WIDE;
   }
}
