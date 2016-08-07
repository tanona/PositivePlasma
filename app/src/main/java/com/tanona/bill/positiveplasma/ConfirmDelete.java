package com.tanona.bill.positiveplasma;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by BillTinVT on 8/6/16.
 * Creates a dialog fragment to get confirmation from the user before deleting database entries.
 */
public class ConfirmDelete extends DialogFragment{

    /* The activity that creates an instance of this dialog fragment must implement this interface
    * in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it.
    */
    public interface NoticeDialogListener {
         void onDialogPositiveClick(DialogFragment dialog);
         void onDialogNegativeClick(DialogFragment dialog);
    }

    // use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    // override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try{
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
            + "must implement NoticeDialogListener");

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Delete")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ConfirmDelete.this);
                        // delete confirmed
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel delete
                        mListener.onDialogNegativeClick(ConfirmDelete.this);
                    }
                }
                );
        // create alert and return it
        return builder.create();
    }



}
