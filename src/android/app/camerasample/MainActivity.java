package android.app.camerasample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private  String createName(long dateTaken) {
		return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();
	}


	private static final String TAG = "ImageManager";
	private static final String APPLICATION_NAME = "PATOM";
	private static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	 private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/" + APPLICATION_NAME;

	public Uri addImageAsApplication(ContentResolver cr, Bitmap bitmap) {
	 long dateTaken = System.currentTimeMillis();
	 String name = createName(dateTaken) + ".jpg";
	return addImageAsApplication(cr, name, dateTaken, PATH, name, bitmap, null);
	 }

	public Uri addImageAsApplication(ContentResolver cr, String name,
	 long dateTaken, String directory,
	 String filename, Bitmap source, byte[] jpegData) {

		Log.d("test",directory);

	OutputStream outputStream = null;
	 String filePath = directory + "/" + filename;
	 try {
	 File dir = new File(directory);
	 if (!dir.exists()) {
	 dir.mkdirs();
	 Log.d(TAG, dir.toString() + " create");
	 }
	File file = new File(directory, filename);
	 if (file.createNewFile()) {
	 outputStream = new FileOutputStream(file);
	 if (source != null) {
	 source.compress(CompressFormat.JPEG, 75, outputStream);
	 } else {
	 outputStream.write(jpegData);
	 }
	 }

	} catch (FileNotFoundException ex) {
	 Log.w(TAG, ex);
	 return null;
	 } catch (IOException ex) {
	 Log.w(TAG, ex);
	 return null;
	 } finally {
	 if (outputStream != null) {
	 try {
	 outputStream.close();
	 } catch (Throwable t) {
	 }
	 }
	 }

	ContentValues values = new ContentValues(7);
	 values.put(Images.Media.TITLE, name);
	 values.put(Images.Media.DISPLAY_NAME, filename);
	 values.put(Images.Media.DATE_TAKEN, dateTaken);
	 values.put(Images.Media.MIME_TYPE, "image/jpeg");
	values.put(Images.Media.DATA, filePath);
	 return cr.insert(IMAGE_URI, values);
	 }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

