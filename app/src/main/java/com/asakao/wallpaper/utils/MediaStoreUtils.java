package com.asakao.wallpaper.utils;

import android.Manifest;
import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MediaStoreUtils {

    //读取
    public static void queryImages(@NotNull Activity activity) {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 10086);
                return;
            }
        }
        queryAllImages(activity);
    }

    private static ArrayList queryAllImages(Context context) {
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection;
        Uri.Builder appendId = new Uri.Builder();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.RELATIVE_PATH};
        } else {
            projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
        }
        Cursor cursor = context.getContentResolver().query(externalContentUri, projection, null, null, null);

        ArrayList<Uri> uris = new ArrayList<Uri>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    appendId = ContentUris.appendId(externalContentUri.buildUpon(), cursor.getLong(0));
                    if (appendId != null) {
                        uris.add(appendId.build());
                        Log.d("SMG", appendId.build().toString());
                    }
                    String string1 = cursor.getString(1);
                    Log.d("SMG", string1);
                    String string2 = cursor.getString(2);
                    Log.d("SMG", string2);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return uris;
    }

    //保存
    public static void saveImages(@NotNull Activity activity, @NotNull final Bitmap bitmap) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 10086);
                return;
            }
        }

        try {
            String fileName = "chinaums_" + System.currentTimeMillis() + ".jpg";
            saveMedia(activity, bitmap, Environment.DIRECTORY_PICTURES, "chinaums", fileName, "image/JPEG", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //type:Environment.DIRECTORY_PICTURES
    private static void saveMedia(Context context, Bitmap bitmap, String dirType, String relativeDir, String filename, String mimeType, String description) throws IOException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //首先保存
            File saveDir = Environment.getExternalStoragePublicDirectory(dirType);
            saveDir = new File(saveDir, relativeDir);
            if (!saveDir.exists() && !saveDir.mkdirs()) {
                try {
                    throw new Exception("create directory fail!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d("SMG", saveDir.getAbsolutePath());
            File outputFile = new File(saveDir, filename);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            //把文件插入到系统图库(直接插入到Picture文件夹下)
//        MediaStore.Images.Media.insertImage(
//            context.contentResolver, outputFile.absolutePath, outputFile.name, ""
//        )
            //最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outputFile)));
            Toast.makeText(context, "save succeed", Toast.LENGTH_LONG).show();
        } else {
            String path = relativeDir.isEmpty() ? Environment.DIRECTORY_PICTURES + File.separator + relativeDir : Environment.DIRECTORY_PICTURES;
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, description);
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, path);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
            //contentValues.put(MediaStore.Images.Media.IS_PENDING,1)

            Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri insertUri = context.getContentResolver().insert(external, contentValues);

            OutputStream fos = (OutputStream) null;
            if (insertUri != null) {
                try {
                    fos = context.getContentResolver().openOutputStream(insertUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(context, "save succeed", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Android Q以下版本，删除文件需要申请WRITE_EXTERNAL_STORAGE权限。通过MediaStore的DATA字段获得媒体文件的绝对路径，然后使用File相关API删除
     * <p>
     * Android Q以上版本，应用删除自己创建的媒体文件不需要用户授权。删除其他应用创建的媒体文件需要申请READ_EXTERNAL_STORAGE权限。
     * 删除其他应用创建的媒体文件，还会抛出RecoverableSecurityException异常，在操作或删除公共目录的文件时，需要Catch该异常，由MediaProvider弹出弹框给用户选择是否允许应用修改或删除图片/视频/音频文件
     */
    public static void deletePicture(@NotNull Activity activity, @NotNull Uri imageUri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            String[] projection = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(imageUri, projection,
                    null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex > -1) {
                    File file = new File(cursor.getString(columnIndex));
                    file.delete();
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } else {
            try {
                activity.getContentResolver().delete(imageUri, null, null);
            } catch (RecoverableSecurityException e1) {
                //捕获 RecoverableSecurityException异常，发起请求
                try {
                    ActivityCompat.startIntentSenderForResult(activity, e1.getUserAction().getActionIntent().getIntentSender(),
                            10086, null, 0, 0, 0, null);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
