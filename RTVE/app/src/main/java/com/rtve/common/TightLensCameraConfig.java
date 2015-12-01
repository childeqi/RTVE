package com.rtve.common;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Matt on 10/10/2015.
 */
@SuppressLint("ParcelCreator")
public class TightLensCameraConfig extends CameraConfig
{
   public TightLensCameraConfig(String name)
   {
      super(name);
   }

   public TightLensCameraConfig(String name, boolean timeLimited)
   {
      super(name, timeLimited);
   }

   public TightLensCameraConfig(String name, boolean timeLimited, int number)
   {
      super(name, timeLimited, number);
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
