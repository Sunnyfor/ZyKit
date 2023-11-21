package com.sunny.kit.utils.application.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.sunny.kit.utils.application.ZyKit
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule
class ZyAppGlideModule : AppGlideModule() {

    companion object {
        var okHttpClient = OkHttpClient()
    }

    private val factory: Factory by lazy {
        Factory(okHttpClient)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            factory
        )
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.DEBUG)
        val cacheSize = 100 * 1000L * 1000L
        builder.setDiskCache(
            DiskLruCacheFactory(ZyKit.file.getExternalFile("glide").path, cacheSize)
        )
    }

    class Factory(private val client: Call.Factory) : ModelLoaderFactory<GlideUrl, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return ZyOkHttpUrlLoader(client)
        }

        override fun teardown() {
        }
    }
}