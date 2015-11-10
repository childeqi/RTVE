package com.rtve.core.storage;

import android.content.Context;

import com.rtve.common.CameraConfigList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/24/2015.
 */
public class CameraConfigLoader
{

   private CameraConfigLoader()
   {
      // a private constructor makes this class uninstantiable
   }

   public static List<String> getAvailableConfigNames(Context c)
   {
      if (c == null)
      {
         throw new NullPointerException("Context is null");
      }

      return convertConfigListToConfigNameList(getConfigFiles(c));
   }

   private static List<String> convertConfigListToConfigNameList(List<java.io.File> configFileList)
   {
      List<String> toReturn = new ArrayList<>();

      if (configFileList != null && configFileList.size() > 0)
      {
         for (java.io.File file : configFileList)
         {
            toReturn.add(file.getName()
                             .substring(CameraConfigSaver.CONFIG_STORAGE_PREFIX.length()));
         }
      }

      return toReturn;
   }

   private static List<java.io.File> convertFileListToConfigList(java.io.File[] fileList)
   {
      List<java.io.File> toReturn = new ArrayList<>();

      if (fileList != null && fileList.length > 0)
      {
         for (java.io.File file : fileList)
         {
            if (file.isFile() && file.getName().startsWith(CameraConfigSaver.CONFIG_STORAGE_PREFIX))
            {
               toReturn.add(file);
            }
         }
      }

      return toReturn;
   }

   private static List<java.io.File> getConfigFiles(Context c)
   {
      java.io.File storageDir = c.getFilesDir();
      if (storageDir != null)
      {
         return convertFileListToConfigList(storageDir.listFiles());
      }
      else
      {
         // return an empty array
         return new ArrayList<java.io.File>(0);
      }
   }

   public static CameraConfigList load(Context c, String configName) throws Exception
   {
      if (c == null || configName == null)
      {
         throw new NullPointerException();
      }

      java.io.File configFile = new java.io.File(c.getFilesDir(),
                                                 CameraConfigSaver.CONFIG_STORAGE_PREFIX +
                                                         configName);

      if (!configFile.isFile() || !configFile.canRead())
      {
         throw new IllegalArgumentException("Cannot load configuration from given filename");
      }

      return parseConfigListFromFile(configFile);
   }

   private static CameraConfigList parseConfigListFromFile(java.io.File inputFile) throws Exception
   {
      Serializer serializer = new Persister();

      Configuration config = serializer.read(Configuration.class, inputFile);
      return XmlConfigListFactory.getConfigListFromConfiguration(config);
   }
}
