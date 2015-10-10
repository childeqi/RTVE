package com.rtve;

/**
 * Created by Matt on 10/10/2015.
 */
public class WideLensCamera extends Camera
{
   public WideLensCamera(String name)
   {
      super(name);
   }

   @Override
   public String toString()
   {
      return super.toString() + "\n(Wide Lens)";
   }
}
