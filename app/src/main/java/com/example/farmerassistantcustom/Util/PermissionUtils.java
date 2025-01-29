package com.example.farmerassistantcustom.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class PermissionUtils {

    private static String logMessage = "";
    private static AlertDialog alertDialog;
    private static final String TAG = PermissionUtils.class.getName();
    private static String[] permissionsArray=new String[]{};

    public static boolean requestPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            boolean externalStorageManager = Environment.isExternalStorageManager();
            if (!externalStorageManager) {
                PermissionUtils.fullStorageAccessPermDialog(activity);
                return false;
            }
        } else {
            permissionsArray = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,

            };
        }


        ArrayList<String> permissionsNeeded = new ArrayList<>();
        boolean granted = true;
        for (String permission : permissionsArray) {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
            boolean hasPermission = (permissionCheck == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                permissionsNeeded.add(permission);
                granted = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (PermissionUtils.neverAskAgainSelected(activity)) {
                        if (PermissionUtils.getRationaleDisplayStatus(activity, permission)) {
                            return PermissionUtils.displayNeverAskAgainDialog(activity);
                        }
                    }
                }
            }
        }

        if (!granted) {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toArray(new String[0]),
                    requestCode);
        }
        return permissionsNeeded.size() <= 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean neverAskAgainSelected(final Activity activity) {
        for (String permission : permissionsArray) {
            final boolean prevShouldShowStatus = getRationaleDisplayStatus(activity, permission);
            final boolean currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission);
            return prevShouldShowStatus != currShouldShowStatus;
        }
        return false;
    }

    public static boolean getRationaleDisplayStatus(final Context context, final String permission) {
        SharedPreferences genPrefs = context.getSharedPreferences("per", Context.MODE_PRIVATE);
        return genPrefs.getBoolean(permission, false);
    }

    public static boolean displayNeverAskAgainDialog(Context context) {

        ArrayList<String> permissionsNeeded = new ArrayList<>();

        for (String permission : permissionsArray) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
            boolean hasPermission = (permissionCheck == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                Log.d(TAG, permission);
                permissionsNeeded.add(permission);
            }
        }

        if (permissionsNeeded.size() <= 0) {
            return true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Since required permissions has not been granted," +
                    "this app will not be able to work as required. " +
                    "Please go to Settings -> Applications/Apps -> " +
                    "Look for Burger App Admin -> " +
                    "Grant all the required permissions for the app.");
            builder.setCancelable(false);
            builder.setPositiveButton("Permit Manually", (dialog, which) -> {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void fullStorageAccessPermDialog(Context context) {
        closeDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permission Required");
        builder.setMessage("Need to access full storage to save data ");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            try {
                Uri uri = Uri.parse(context.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                context.startActivity(intent);
            } catch (Exception ex) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                context.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static String checkOverlayPermission(Context context) {
        try {
            boolean overlayPermission = tvOverlayPermission(context);
            if (!overlayPermission) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return logMessage = logMessage + "\nAndroid Version Less Than M Skipping Check Overlay Perms";
                }
                if (Settings.canDrawOverlays(context)) {
                    return logMessage = logMessage + "\nAndroid Version GRANTED";
                }
                logMessage = logMessage + "\nChecking MOBILE Perm Code TV Code return false";
                mobileOverlayPermission(context);
                try {
                    WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    if (mgr == null)
                        return logMessage = logMessage + "\nWindow Mgr Null"; //getSystemService might return null
                    View viewToAdd = new View(context);
                    WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0, Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
                    viewToAdd.setLayoutParams(params);
                    mgr.addView(viewToAdd, params);
                    mgr.removeView(viewToAdd);
                } catch (Exception ignore) {

                }
            } else return logMessage = logMessage + "\nOverlay Perms Found";
        } catch (Exception e) {
            e.printStackTrace();
            logMessage = logMessage + "\n" + e.getMessage();
        }
        return logMessage.trim().isEmpty() ? "NO LOG RECORD" : logMessage;
    }

    public static boolean tvOverlayPermission(Context context) {
        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (manager != null) {
                Class localClass = manager.getClass();
                Class[] arrayOfClass = new Class[3];
                arrayOfClass[0] = Integer.TYPE;
                arrayOfClass[1] = Integer.TYPE;
                arrayOfClass[2] = String.class;
                try {
                    Method method = localClass.getMethod("checkOp", arrayOfClass);
                    if (method == null) {
                        logMessage = logMessage + "\ncheckOp method NULL";
                        return false;
                    }
                    Object[] arrayOfObjects = new Object[3];
                    arrayOfObjects[0] = 24;
                    arrayOfObjects[1] = Binder.getCallingUid();
                    arrayOfObjects[2] = context.getPackageName();
                    int m = (Integer) method.invoke((Object) manager, arrayOfObjects);
                    logMessage = logMessage + "\n" + m;
                    return m == AppOpsManager.MODE_ALLOWED;
                } catch (Exception e) {
                    return false;
                }
            } else {
                logMessage = logMessage + "\nAPPSOPS MANAGER NULL";
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logMessage = logMessage + "\n" + e.getMessage();
            return false;
        }
    }

    public static void mobileOverlayPermission(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(context)) {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    myIntent.setData(Uri.parse("package:" + context.getPackageName()));
                    context.startActivity(myIntent);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
//            try {
//                overLayPermissionDialog(context);
//            } catch (Exception ignored) {
//            }
        }
    }

    private static void overLayPermissionDialog(Context context) {
        try {
            closeDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Overlay Permission Required");
            builder.setMessage("Instructions:\n\n" +
                    "-> Go to Settings\n" +
                    "-> Apps\n" +
                    "-> Special app Access\n" +
                    "-> Display over other apps\n" +
                    "-> Search for Local Signage App\n" +
                    "-> Then toggle switch on\n\n");

            builder.setPositiveButton("OK", (dialog, id) -> {
                dialog.cancel();
//                ((Activity) context).finish();
            });
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception ignored) {
        }
    }

    private static void closeDialog() {
        try {
            if (alertDialog != null) alertDialog.cancel();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
