package it.niedermann.nextcloud.sso.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * A simple model loader for fetching media over http/https using OkHttp.
 */
public class SingleSignOnUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    @NonNull
    private final Context context;

    public SingleSignOnUrlLoader(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public boolean handles(@NonNull GlideUrl url) {
        return true;
    }

    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl url, int width, int height, @NonNull Options options) {
        return new LoadData<>(url, new SingleSignOnStreamFetcher(context, url));
    }

    /**
     * The default factory for {@link SingleSignOnUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private final SingleSignOnUrlLoader loader;

        /**
         * Constructor for a new Factory that runs requests using given client.
         */
        public Factory(@NonNull Context context) {
            loader = new SingleSignOnUrlLoader(context);
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return loader;
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
