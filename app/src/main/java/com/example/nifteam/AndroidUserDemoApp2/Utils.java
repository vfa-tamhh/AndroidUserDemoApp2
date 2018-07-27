package com.example.nifteam.AndroidUserDemoApp2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Utils {

    public interface ClickListener {
        void onOK();
    }

    private static ProgressDialog progressDialog = null;

    public static void showLoading(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getText(R.string.loading_title));
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public static void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    public static void showDialog(Context context, String msg, final ClickListener clickListener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        TextView text = dialog.findViewById(R.id.tv_message);
        text.setText(msg);

        Button dialogButton = dialog.findViewById(R.id.btn_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clickListener.onOK();
            }
        });

        dialog.show();
    }

    public static void showDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        TextView text = dialog.findViewById(R.id.tv_message);
        text.setText(msg);

        Button dialogButton = dialog.findViewById(R.id.btn_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static boolean isBlankOrEmpty(EditText editText){
        return editText.getText().toString().trim().isEmpty();
    }

    public static void clearField(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof EditText) {
                ((EditText) linearLayout.getChildAt(i)).setText("");
            }
        }
    }


}
