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
            case TIGHT:
               return new TightLensCameraConfig(in);

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
   private final int     number;
   private       String  name;
   private       boolean timeLimited;

   protected CameraConfig(Parcel in)
   {
      // the order of these is important
      number = in.readInt();
      name = in.readString();
      timeLimited = (in.readInt() != 0);
   }

   protected CameraConfig(String name)
   {
      this.number = nextNumber++;
      this.name = name;
      this.timeLimited = false;
   }

   protected CameraConfig(String name, boolean timeLimited)
   {
      this.number = nextNumber++;
      this.name = name;
      this.timeLimited = timeLimited;
   }

   protected CameraConfig(String name, boolean timeLimited, int number)
   {
      this.number = number;
      this.name = name;
      this.timeLimited = timeLimited;
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
      out.writeInt(timeLimited ? 1 : 0);
   }

   public int getNumber()
   {
      return number;
   }

   public String getName()
   {
      return name;
   }

   public boolean isTimeLimited()
   {
      return timeLimited;
   }

   @Override
   public int hashCode()
   {
      int result = number;
      result = 31 * result + name.hashCode();
      result = 31 * result + (timeLimited ? 1 : 0);
      result = 31 * result + getLensType().hashCode();
      return result;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof CameraConfig)) return false;

      CameraConfig that = (CameraConfig) o;

      if (number != that.number) return false;
      if (timeLimited != that.timeLimited) return false;
      if (getLensType() != that.getLensType()) return false;
      return name.equals(that.name);
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder(name);
      sb.append(" {#[")
        .append(number)
        .append("], Lens Type[")
        .append(getLensType().toString())
        .append("], Time Limited[")
        .append(Boolean.toString(timeLimited))
        .append("]}");
      return sb.toString();
   }
}
