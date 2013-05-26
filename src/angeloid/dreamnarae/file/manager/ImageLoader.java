package angeloid.dreamnarae.file.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import angeloid.dreamnarae.R;

public class ImageLoader 
{
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    public Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService; 
 
    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }
 
    final int stub_id = R.drawable.android;
    public void DisplayImage(String url, Bitmap b, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap != null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, b, imageView);
            imageView.setImageResource(stub_id);
        }
    }
 
    public void queuePhoto(String url, Bitmap b, ImageView imageView)
    {
        PhotoToLoad p = new PhotoToLoad(url, b, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    public Bitmap getBitmap(String url)
    {
        File f = fileCache.getFile(url);
 
        //from SD cache
        Bitmap b = decodeFile(f);
        if(b != null)
            return b;
        return null;
    }
 
    //decodes image and scales it to reduce memory consumption
    public Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
 
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while(true){
                if(width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
 
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
 
    // Task for the queue
    public class PhotoToLoad
    {
        public String url;
        public Bitmap b;
        public ImageView imageView;
        public PhotoToLoad(String u, Bitmap bmp, ImageView i) {
            url = u;
            b = bmp;
            imageView = i;
        }
    }
 
    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad = photoToLoad;
        }
 
        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            Bitmap bmp = photoToLoad.b;
            memoryCache.put(photoToLoad.url, bmp);
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }
 
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if(tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }
 
    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){ bitmap = b; photoToLoad = p; }
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }
 
    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
    
    public class MemoryCache {
        public Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
     
        public Bitmap get(String id){
        	boolean flag = !cache.containsKey(id);
            if(flag) return null;
            SoftReference<Bitmap> ref = cache.get(id);
            return ref.get();
        }
     
        public void put(String id, Bitmap bitmap){
            cache.put(id, new SoftReference<Bitmap>(bitmap));
        }
     
        public void clear() {
            cache.clear();
        }
    }
    
    public class FileCache {
        public File cacheDir;
     
        public FileCache(Context context){
            //Find the dir to save cached images
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
            else
                cacheDir = context.getCacheDir();
            if(!cacheDir.exists())
                cacheDir.mkdirs();
        }
     
        public File getFile(String url){
            //I identify images by hashcode. Not a perfect solution, good for the demo.
            String filename = String.valueOf(url.hashCode());
            //Another possible solution (thanks to grantland)
            //String filename = URLEncoder.encode(url);
            File f = new File(cacheDir, filename);
            return f;
     
        }
     
        public void clear(){
            File[] files = cacheDir.listFiles();
            if(files == null)
                return;
            for(File f:files)
                f.delete();
        }
    }
}