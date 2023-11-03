package com.sunny.kit.utils.impl.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sunny.kit.utils.api.glide.GlideApp
import com.sunny.kit.utils.api.glide.GlideRequest
import com.sunny.kit.utils.api.glide.ZyGlideUtil

@Suppress("MemberVisibilityCanBePrivate")
internal class ZyGlideUtilImpl : ZyGlideUtil() {


    private fun getPlaceholder(placeholder: Int): Int {
        return if (placeholder == -1) defaultPlaceholder else placeholder
    }

    private fun getLoadImageRequest(imgView: ImageView, imgPath: Any, placeholder: Int): GlideRequest<Drawable> {
        return GlideApp.with(imgView.context).load(imgPath).placeholder(getPlaceholder(placeholder)).dontAnimate()
    }

    private fun getLoadGifRequest(imgView: ImageView, imgPath: Any, placeholder: Int): GlideRequest<GifDrawable> {
        return GlideApp.with(imgView.context).asGif().load(imgPath).placeholder(getPlaceholder(placeholder)).dontAnimate()
    }

    private fun getLoadBitmapRequest(imgView: ImageView, imgPath: Any, placeholder: Int): GlideRequest<Bitmap> {
        return GlideApp.with(imgView.context).asBitmap().load(imgPath).placeholder(getPlaceholder(placeholder)).dontAnimate()
    }

    /**
     * 加载Image
     */
    override fun loadImage(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadImageRequest(imgView, imgPath, placeholder).into(imgView)
    }

    /**
     * 加载Image(取消内存缓存)
     */
    override fun loadImageNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadImageRequest(imgView, imgPath, placeholder).skipMemoryCache(true).into(imgView)
    }

    /**
     * 加载Image(取消硬盘缓存)
     */
    override fun loadImageNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadImageRequest(imgView, imgPath, placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }

    /**
     * 无缓存加载Image
     */
    override fun loadImageNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadImageRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }

    /**
     * 加载GIF文件
     */
    override fun loadGif(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadGifRequest(imgView, imgPath, placeholder).into(imgView)
    }

    /**
     * 加载GIF(取消内存缓存)
     */
    override fun loadGifNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadGifRequest(imgView, imgPath, placeholder).skipMemoryCache(true).into(imgView)
    }

    /**
     * 加载GIF(取消硬盘缓存)
     */
    override fun loadGifNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadGifRequest(imgView, imgPath, placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }

    /**
     * 无缓存加载GIF
     */
    override fun loadGifNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int) {
        getLoadGifRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgView)
    }


    /**
     * 加载Bitmap
     */
    override fun loadBitmap(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Bitmap) -> Unit) {
        getLoadBitmapRequest(imgView, imgPath, placeholder).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 加载Bitmap(取消内存缓存)
     */
    override fun loadBitmapNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Bitmap) -> Unit) {
        getLoadBitmapRequest(imgView, imgPath, placeholder).skipMemoryCache(true).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 加载Bitmap(取消硬盘缓存)
     */
    override fun loadBitmapNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Bitmap) -> Unit) {
        getLoadBitmapRequest(imgView, imgPath, placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 无缓存加载Bitmap
     */
    override fun loadBitmapNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Bitmap) -> Unit) {
        getLoadBitmapRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    /**
     * 加载Drawable
     */
    override fun loadDrawable(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (drawable: Drawable) -> Unit) {
        getLoadImageRequest(imgView, imgPath, placeholder).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 加载Drawable(取消内存缓存)
     */
    override fun loadDrawableNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Drawable) -> Unit) {
        getLoadImageRequest(imgView, imgPath, placeholder).skipMemoryCache(true).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 加载Drawable(取消硬盘缓存)
     */
    override fun loadDrawableNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Drawable) -> Unit) {
        getLoadImageRequest(imgView, imgPath, placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                callback.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /**
     * 无缓存加载Drawable
     */
    override fun loadDrawableNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int, callback: (bitmap: Drawable) -> Unit) {
        getLoadImageRequest(imgView, imgPath, placeholder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    callback.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}