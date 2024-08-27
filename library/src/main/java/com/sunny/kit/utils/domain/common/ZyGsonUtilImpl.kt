package com.sunny.kit.utils.domain.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type

internal class ZyGsonUtilImpl {

    /**
     * 获取GsonBuilder
     */
    private fun getGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .registerTypeAdapterFactory(NullToDefaultAdapterFactory())
            .registerTypeAdapter(Int::class.javaPrimitiveType, object : JsonDeserializer<Int> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
                    return try {
                        json?.asInt ?: 0
                    } catch (e: Exception) {
                        return 0
                    }
                }
            })
            .registerTypeAdapter(Float::class.javaPrimitiveType, object : JsonDeserializer<Float> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Float {
                    return try {
                        json?.asFloat ?: 0f
                    } catch (e: Exception) {
                        return 0f
                    }

                }
            })
            .registerTypeAdapter(Double::class.javaPrimitiveType, object : JsonDeserializer<Double> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
                    return try {
                        json?.asDouble ?: 0.0
                    } catch (e: Exception) {
                        return 0.0
                    }
                }
            })
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, object : JsonDeserializer<Boolean?> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Boolean {
                    return try {
                        json?.asBoolean ?: false
                    } catch (e: Exception) {
                        return false
                    }
                }
            })
            .registerTypeAdapter(Long::class.javaPrimitiveType, object : JsonDeserializer<Long> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
                    return try {
                        json?.asLong ?: 0L
                    } catch (e: Exception) {
                        return 0L
                    }
                }
            })
    }

    /**
     * 获取Gson
     */
    fun getGson(): Gson {
        return getGsonBuilder().create()
    }

    /**
     * 空值转默认值
     */
    private class NullToDefaultAdapterFactory : TypeAdapterFactory {

        override fun <T> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
            val delegate = gson?.getDelegateAdapter(this, type)
            val elementAdapter = gson?.getAdapter(JsonElement::class.java)
            return object : TypeAdapter<T>() {
                override fun write(out: JsonWriter, value: T?) {
                    delegate?.write(out, value)
                }

                override fun read(reader: JsonReader): T? {
                    val jsonElement = elementAdapter?.read(reader)
                    if (jsonElement is JsonObject) {
                        areAllFieldsDefined(jsonElement, type?.rawType)
                    }
                    return delegate?.fromJsonTree(jsonElement)
                }
            }
        }


        /**
         * 判断json是否包含所有String字段
         */
        private fun <T> areAllFieldsDefined(jsonObject: JsonObject, clazz: Class<T>?) {

            val fields = clazz?.declaredFields
            fields?.forEach { field ->
                field.isAccessible = true
                val fieldName = field.getAnnotation(SerializedName::class.java)?.value ?: field.name
                when (field.type.name) {
                    String::class.java.name -> {
                        if (!jsonObject.has(fieldName) || jsonObject.get(fieldName).isJsonNull) {
                            jsonObject.addProperty(fieldName, "")
                        }
                    }

                    List::class.java.name, ArrayList::class.java.name -> {
                        if (!jsonObject.has(fieldName) || jsonObject.get(fieldName).isJsonNull) {
                            jsonObject.add(fieldName, JsonArray())
                        }
                    }

                    else -> {
                        if (field.type.isArray) {
                            if (!jsonObject.has(fieldName) || jsonObject.get(fieldName).isJsonNull) {
                                jsonObject.add(fieldName, JsonArray())
                            }
                        }
                    }
                }
            }
        }
    }
}