package com.xjk.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.xjk.android.constant.Constants;

import java.io.File;
import java.io.IOException;

public class SelectPicUtils {

	public static void getImageFromAlbum(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		activity.startActivityForResult(intent, Constants.REQUEST_CODE_PICK);
	}

	public static void getImageFromCamera(Activity activity, String mFilePath) {
		Uri imageUri = FileProvider.getUriForFile(activity, "com.chiporadar.zhiboapp.fileprovider", new File(mFilePath));//通过FileProvider创建一个content类型的Uri
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
		activity.startActivityForResult(intent,Constants.REQUEST_CODE_TAKE);
	}
	
	public static void cropImageUri(Activity activity, String mCropPath, String photo, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		Uri imageUri = FileProvider.getUriForFile(activity, "com.chiporadar.zhiboapp.fileprovider", new File(photo));
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra("output", Uri.fromFile(new File(mCropPath)));
//		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, Constants.REQUEST_CODE_CROP);
	}
	
	/**
	 * 获取随机文件名
	 */
	public static String getRandomFilePath(){
		return Constants.DEFAULT_IMAGE_PATH + File.separator + SystemClock.uptimeMillis()+".png";
	}

	/**
	 * 创建文件夹
	 */
	public static File createDir(String dirName) throws IOException {
		File dir = new File(dirName);
		if (isSDMounted()) {
			if (!dir.exists()) dir.mkdirs();
			return dir;
		}
		return null;
	}

	/**
	 * SD卡是否可用
     */
	public static boolean isSDMounted(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
