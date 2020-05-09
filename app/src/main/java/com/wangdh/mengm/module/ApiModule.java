
package com.wangdh.mengm.module;

import android.support.annotation.NonNull;
import com.orhanobut.logger.Logger;
import com.wangdh.mengm.BuildConfig;
import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.utils.AppUtils;
import com.wangdh.mengm.utils.NetworkUtil;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

@Module
public class ApiModule {
    private static final int READ_TIME_OUT = 6;
    private static final int CONNECT_TIME_OUT = 6;

    @Provides
    public OkHttpClient provideOkHttpClient() {
        //非持久化cookie
//        CookieManager cookieManager = new CookieManager();
//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        //持久化cookie
//        CookieManager cookieManager = new CookieManager(new InDiskCookieStore(MyApplication.getContext()),CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        //指定缓存路径,缓存大小100Mb
        //getExternalCacheDir()  外部存储
        //getCacheDir()  内置存储
        Cache cache = new Cache(new File(AppUtils.getAppContext().getExternalCacheDir(), "myappcache"),
                1024 * 1024 * 100);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置超时
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)

                //服务端需要保持请求是同一个cookie
                //   .cookieJar(new JavaNetCookieJar(cookieManager))
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(cacheInterceptor);
        if (BuildConfig.DEBUG) {
            //DEBUG Log信息拦截器
            builder.addInterceptor(sLoggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    protected RetrofitManager provideApiService(OkHttpClient okHttpClient) {
        return RetrofitManager.getInstance(okHttpClient);
    }

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);

            } else {
                Logger.d("toast", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isAvailable(AppUtils.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtil.isAvailable(AppUtils.getAppContext())) {
                /* int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时*/
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时，设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        }
    };
}