package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public class CameraTime
{
   private final long time;

   public CameraTime()
   {
      this.time= System.currentTimeMillis();
   }

   private CameraTime(long millis)
   {
      this.time = millis;
   }

   public CameraTime minus(CameraTime before)
   {
      return new CameraTime(time - before.getTime());
   }

   private long getTime()
   {
      return time;
   }

   @Override
   public boolean equals(Object o)
   {
      return (o != null && o instanceof CameraTime && ((CameraTime) o).getTime() == time);
   }
}
