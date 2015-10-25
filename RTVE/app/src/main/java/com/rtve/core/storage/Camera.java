package com.rtve.core.storage;

import com.rtve.common.LensType;

import org.simpleframework.xml.Attribute;

/**
 * Created by Matt on 10/24/2015.
 */
public class Camera
{
   @Attribute
   private int number;

   @Attribute
   private String name;

   @Attribute
   private LensType lens;

   public Camera(int number, String name, LensType lens)
   {
      this.number = number;
      this.name = name;
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

   LensType getLensType()
   {
      return lens;
   }
}
