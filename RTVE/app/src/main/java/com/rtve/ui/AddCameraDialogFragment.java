package com.rtve.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rtve.R;
import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraFactory;
import com.rtve.common.LensType;

/**
 * Created by Matt on 10/11/2015.
 */
public class AddCameraDialogFragment extends DialogFragment
{
   private static final String CAMERA_CONFIG_LIST_KEY = "cameraConfigList";

   // used to callback with the newly created configuration
   private AddCameraDialogListener listener;
   private View                    rootView;

   /**
    * The activity that creates an instance of this dialog fragment must implement this interface in
    * order to receive the callback with the created camera configuration.
    */
   public interface AddCameraDialogListener
   {
      /**
       * Called when the "OK" button is pressed in the dialog when a valid camera configuration is
       * entered.
       *
       * @param dialog    the dialog fragment that is the source of this callback
       * @param newConfig the newly created camera configuration
       */
      void cameraAdded(AddCameraDialogFragment dialog, CameraConfig newConfig);
   }

   public static AddCameraDialogFragment createFragment(CameraConfigList cameraConfigList)
   {
      AddCameraDialogFragment dialog          = new AddCameraDialogFragment();
      Bundle                  argumentsBundle = new Bundle();
      argumentsBundle.putParcelable(CAMERA_CONFIG_LIST_KEY, cameraConfigList);
      dialog.setArguments(argumentsBundle);
      return dialog;
   }

   // Override the Fragment.onAttach() method to instantiate the AddCameraDialogListener
   @Override
   public void onAttach(Activity activity)
   {
      super.onAttach(activity);
      // Verify that the host activity implements the callback interface
      try
      {
         // Instantiate the AddCameraDialogListener so we can send events to the host
         listener = (AddCameraDialogListener) activity;
      }
      catch (ClassCastException e)
      {
         // The activity doesn't implement the interface, throw exception
         throw new ClassCastException(activity.toString()
                                              + " must implement AddCameraDialogListener");
      }
   }

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState)
   {
      // Use the Builder class for convenient dialog construction
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      // Get the layout inflater
      LayoutInflater inflater = getActivity().getLayoutInflater();

      // Inflate and set the layout for the dialog
      // Pass null as the parent view because its going in the dialog layout
      rootView = inflater.inflate(R.layout.dialog_addcamera, null);

      builder.setView(rootView)
              // Add action buttons
              .setPositiveButton(R.string.dialog_add_camera_ok,
                                 new DialogInterface.OnClickListener()
                                 {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                       //Do nothing here because we override this button later to
                                       // change the close behaviour.
                                       //However, we still need this because on older versions of
                                       // Android unless we
                                       //pass a handler the button doesn't get instantiated
                                    }
                                 })
              .setNegativeButton(R.string.dialog_add_camera_cancel,
                                 new DialogInterface.OnClickListener()
                                 {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                       AddCameraDialogFragment.this.getDialog().cancel();
                                    }
                                 });
      return builder.create();
   }

   @Override
   public void onStart()
   {
      super.onStart();    //super.onStart() is where dialog.show() is actually called on the
      // underlying dialog, so we have to do it after this point
      final AlertDialog d = (AlertDialog) getDialog();
      if (d != null)
      {
         Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
         positiveButton.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
               if (tryAddCamera())
               {
                  // successfully added camera, so close dialog
                  AddCameraDialogFragment.this.getDialog().dismiss();
               }
               // if we failed to add camera, don't do anything (so dialog doesn't close)
            }
         });
      }
   }

   private boolean tryAddCamera()
   {
      // get the string entered for the camera name
      EditText editText = (EditText) rootView.findViewById(R.id.dialog_add_camera_camera_name);
      String   name     = editText.getText().toString();
      // get the CameraConfigList given as an argument for this dialog
      CameraConfigList camConfigList = getCameraConfigList();
      // ensure that the name is valid
      if (!isNameValid(name))
      {
         displayErrorMessage(rootView.getContext(), R.string.dialog_add_camera_error_invalid);
         return false;
      }
      // ensure that the name is unique
      else if (!isNameUnique(name, camConfigList))
      {
         displayErrorMessage(rootView.getContext(), R.string.dialog_add_camera_cancel_not_unique);
         return false;
      }
      // valid camera name, so get lens type then add camera to list
      else
      {
         // get lens type radio button group
         RadioGroup lensRadioGroup = (RadioGroup) rootView.findViewById(R.id.dialog_add_camera_lens_radiogroup);
         // get the selected lens type
         LensType lens;
         switch (lensRadioGroup.getCheckedRadioButtonId())
         {
            case R.id.dialog_add_camera_radio_tight:
               lens = LensType.TIGHT;
               break;

            case R.id.dialog_add_camera_radio_wide:
               lens = LensType.WIDE;
               break;

            default:
               throw new IllegalStateException("Neither lens type radio button is selected");
         }

         // get whether this is time limited or not
         CheckBox cb = (CheckBox) rootView.findViewById(R.id.dialog_add_camera_time_limited);
         boolean timeLimited = cb.isChecked();

         // create CameraConfig and callback
         CameraConfig newConfig = CameraFactory.createCamera(name, timeLimited, lens);
         listener.cameraAdded(this, newConfig);
         return true;
      }
   }

   private void displayErrorMessage(Context context, int resourceID)
   {
      Toast toast = Toast.makeText(context, resourceID, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
   }

   private CameraConfigList getCameraConfigList()
   {
      Bundle bundle = getArguments();
      Object o      = bundle.getParcelable(CAMERA_CONFIG_LIST_KEY);
      if (o == null || !(o instanceof CameraConfigList))
      {
         throw new IllegalStateException("Could not get CameraConfigList");
      }
      else
      {
         return (CameraConfigList) o;
      }
   }

   private boolean isNameValid(String name)
   {
      return (name != null && !name.trim().isEmpty());
   }

   private boolean isNameUnique(String name, CameraConfigList camConfigList)
   {
      // make sure that the entered camera name is unique from the previously configured cameras
      for (CameraConfig config : camConfigList.getBackingList())
      {
         // case insensitive comparison
         if (name.equalsIgnoreCase(config.getName()))
         {
            return false;
         }
      }

      return true;
   }
}
