package android.app.camerasample;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;

public class ImageManager {
	 public static Uri addImageAsCamera(ContentResolver cr, Bitmap bitmap) {
	 long dateTaken = System.currentTimeMillis();
	 String name = createName(dateTaken) + ".jpg";
	String uriStr = MediaStore.Images.Media.insertImage(cr, bitmap, name,
	 null);
	 return Uri.parse(uriStr);
	 }

	private static String createName(long dateTaken) {
	 return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();
	 }
	 }
