// WallpaperUtils.kt - Check for null InputStream and log errors/permissions
import android.util.Log

object WallpaperUtils {
    fun wallpaperStreamHandler(inputStream: InputStream?) {
        if (inputStream == null) {
            Log.e("WallpaperUtils", "InputStream is null")
            // Handle null InputStream
        }
        // Further processing...
    }
}