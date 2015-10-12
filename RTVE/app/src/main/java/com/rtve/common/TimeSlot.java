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

   public TimeSlot stopAt(CameraTime endTime)
   {
      this.end = endTime;
      this.duration = end.minus(start);
      return this;
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

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder(cam.getName()).append(": ");
      sb.append("start[").append(start.getMillis()).append(']');
      sb.append(" to ");
      sb.append("end[").append(end.getMillis()).append(']');
      sb.append(" - duration[").append(duration.getMillis()).append(']');
      return sb.toString();
   }
}
