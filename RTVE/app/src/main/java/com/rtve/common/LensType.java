package com.rtve.common;

/**
 * Created by Matt on 10/10/2015.
 */
public enum LensType
{
   TIGHT("Tight Lens"),
   WIDE("Wide Lens");

   private final String name;

   LensType(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
