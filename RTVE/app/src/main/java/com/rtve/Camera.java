package com.rtve;

/**
 * Created by Matt on 10/10/2015.
 */
public abstract class Camera
{
   private static int nextNumber = 0;

   private final int number;
   private String name;

   public Camera(String name)
   {
      this.number = nextNumber++;
      this.name = name;
   }

   public int getNumber()
   {
      return number;
   }

   public String getName()
   {
      return name;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
