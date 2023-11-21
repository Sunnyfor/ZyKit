package com.sunny.kit.utils.application.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.sunny.kit.R

@Suppress("MemberVisibilityCanBePrivate")
abstract class ZyImageUtil {

    var defaultPlaceholder = R.drawable.zy_ic_placeholder

    /**
     * 加载Image
     */
    abstract fun loadImage(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 加载Image(取消内存缓存)
     */
    abstract fun loadImageNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 加载Image(取消硬盘缓存)
     */
    abstract fun loadImageNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 无缓存加载Image
     */
    abstract fun loadImageNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)


    /**
     * 加载GIF文件
     */
    abstract fun loadGif(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 加载GIF(取消内存缓存)
     */
    abstract fun loadGifNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 加载GIF(取消硬盘缓存)
     */
    abstract fun loadGifNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)

    /**
     * 无缓存加载GIF
     */
    abstract fun loadGifNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder)


    /**
     * 加载Bitmap
     */
    abstract fun loadBitmap(imgView: ImageView, imgPath: Any, placeholder: Int= defaultPlaceholder, callback: (bitmap: Bitmap) -> Unit)

    /**
     * 加载Bitmap(取消内存缓存)
     */
    abstract fun loadBitmapNoMemoryCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Bitmap) -> Unit)

    /**
     * 加载Bitmap(取消硬盘缓存)
     */
    abstract fun loadBitmapNoDiskCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Bitmap) -> Unit)

    /**
     * 无缓存加载Bitmap
     */
    abstract fun loadBitmapNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Bitmap) -> Unit)


    /**
     * 加载Drawable
     */
    abstract fun loadDrawable(imgView: ImageView, imgPath: Any, placeholder: Int= defaultPlaceholder, callback: (drawable: Drawable) -> Unit)

    /**
     * 加载Drawable(取消内存缓存)
     */
    abstract fun loadDrawableNoMemoryCache(
        imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Drawable) -> Unit
    )

    /**
     * 加载Drawable(取消硬盘缓存)
     */
    abstract fun loadDrawableNoDiskCache(
        imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Drawable) -> Unit
    )

    /**
     * 无缓存加载Drawable
     */
    abstract fun loadDrawableNoAllCache(imgView: ImageView, imgPath: Any, placeholder: Int = defaultPlaceholder, callback: (bitmap: Drawable) -> Unit)

}