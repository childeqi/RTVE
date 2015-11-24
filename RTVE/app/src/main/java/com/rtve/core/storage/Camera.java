package com.rtve.core.storage;

import com.rtve.common.LensType;

import org.simpleframework.xml.Attribute;

/**
 * Created by Matt on 10/24/2015.
 */
public class Camera
{
   @Attribute(name = "number")
   private final int number;

   @Attribute(name = "name")
   private final String name;

   @Attribute(name = "timeLimited")
   private final boolean timeLimited;

   @Attribute(name = "lens")
   private final LensType lens;

   public Camera(@Attribute(name = "number") int number,
                 @Attribute(name = "name") String name,
                 @Attribute(name = "timeLimited") boolean timeLimited,
                 @Attribute(name = "lens") LensType lens)
   {
      this.number = number;
      this.name = name;
      this.timeLimited = timeLimited;
      this.lens = lens;
   }

   int getNumber()
   {
      return number;
   }

   String getName()
   {
      return name;
   }

   boolean isTimeLimited()
   {
      return timeLimited;
   }

   LensType getLensType()
   {
      return lens;
   }
}
