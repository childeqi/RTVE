package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public class CameraTime
{
   private final long millis;

   public CameraTime()
   {
      this.millis = System.currentTimeMillis();
   }

   private CameraTime(long millis)
   {
      this.millis = millis;
   }

   public CameraTime minus(CameraTime before)
   {
      return new CameraTime(millis - before.getMillis());
   }

   public long getMillis()
   {
      return millis;
   }

   @Override
   public boolean equals(Object o)
   {
      return (o != null && o instanceof CameraTime && ((CameraTime) o).getMillis() == millis);
   }
}
