package com.ex.punkapi.network.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class BooleanDeserializer : JsonDeserializer<Boolean> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean? {
        try {
            return json.asInt != 0
        } catch (e: Exception) {
        }

        return json.asBoolean
    }
}