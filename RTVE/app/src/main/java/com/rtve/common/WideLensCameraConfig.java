package com.rtve.common;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Matt on 10/10/2015.
 */
@SuppressLint("ParcelCreator")
public class WideLensCameraConfig extends CameraConfig
{
   public WideLensCameraConfig(String name)
   {
      super(name);
   }

   public WideLensCameraConfig(String name, boolean timeLimited)
   {
      super(name, timeLimited);
   }

   public WideLensCameraConfig(String name, boolean timeLimited, int number)
   {
      super(name, timeLimited, number);
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
