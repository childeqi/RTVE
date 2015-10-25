package com.rtve.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matt on 10/10/2015.
 */
public abstract class CameraConfig implements Parcelable
{
   public static final Parcelable.Creator<CameraConfig> CREATOR
           = new Parcelable.Creator<CameraConfig>()
   {
      public CameraConfig createFromParcel(Parcel in)
      {
         LensType lens = (LensType) in.readSerializable();
         switch (lens)
         {
            case NARROW:
               return new NarrowLensCameraConfig(in);

            case WIDE:
               return new WideLensCameraConfig(in);

            default:
               throw new IllegalArgumentException("Invalid value for LensType");
         }

      }

      public CameraConfig[] newArray(int size)
      {
         return new CameraConfig[size];
      }
   };

   private static int nextNumber = 0;
   private final int    number;
   private       String name;

   protected CameraConfig(Parcel in)
   {
      // the order of these is important
      number = in.readInt();
      name = in.readString();
   }

   protected CameraConfig(String name)
   {
      this.number = nextNumber++;
      this.name = name;
   }

   protected CameraConfig(String name, int number)
   {
      this.number = number;
      this.name = name;
      // ensure that a camera number never repeats
      if (number >= nextNumber)
      {
         nextNumber = number + 1;
      }
   }

   public abstract LensType getLensType();

   public int describeContents()
   {
      return 0;
   }

   public void writeToParcel(Parcel out, int flags)
   {
      // the order of these is important
      out.writeSerializable(getLensType());
      out.writeInt(number);
      out.writeString(name);
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
      return (name + "\n(" + getLensType().toString() + ")");
   }
}
