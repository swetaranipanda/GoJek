package golife.com.gojektest.api.config;

import android.os.Environment;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by Swetarani Panda on 26/04/2017.
 */
public class OKHTTPClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient;

    private OKHTTPClient() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OKHTTPClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                    okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
                    okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
                    // createCacheForOkHTTP();
                }
            }
        }
        return okHttpClient;
    }

    private static void createCacheForOkHTTP() {
        Cache cache = null;
        cache = new Cache(getDirectory(), 1024 * 1024 * 10);
        okHttpClient.setCache(cache);
    }

    public static File getDirectory() {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "UCC" + File.separator);
        root.mkdirs();
        final String fname = "go-jek.cache";
        final File sdImageMainDirectory = new File(root, fname);
        return sdImageMainDirectory;
    }

    public static Call get(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }



}
