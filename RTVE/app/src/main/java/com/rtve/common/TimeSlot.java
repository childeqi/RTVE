package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public class TimeSlot
{
   private CameraTime start, duration, end;
   private CameraConfig cam;

   public TimeSlot(CameraConfig cameraConfig, CameraTime startTime)
   {
      this.cam = cameraConfig;
      this.start = startTime;
   }

   public void stopNow()
   {
      this.end = new CameraTime();
      this.duration = end.minus(start);
   }

   public CameraTime getStartTime()
   {
      return start;
   }

   public CameraTime getEndTime()
   {
      return end;
   }

   public CameraTime getDuration()
   {
      return duration;
   }
}
