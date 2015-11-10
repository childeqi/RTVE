package com.rtve.common;

import android.os.Parcel;

/**
 * Created by Matt on 10/10/2015.
 */
public class TightLensCameraConfig extends CameraConfig
{
   public TightLensCameraConfig(String name)
   {
      super(name);
   }

   public TightLensCameraConfig(String name, int number)
   {
      super(name, number);
   }

   protected TightLensCameraConfig(Parcel in)
   {
      super(in);
   }

   @Override
   public LensType getLensType()
   {
      return LensType.TIGHT;
   }
}
