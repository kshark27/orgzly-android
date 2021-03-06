package com.orgzly.android.ui.note

import android.os.Parcel
import android.os.Parcelable

data class NotePayload @JvmOverloads constructor(
        val title: String = "",
        val content: String? = null,
        val state: String? = null,
        val priority: String? = null,
        val scheduled: String? = null,
        val deadline: String? = null,
        val closed: String? = null,
        val tags: List<String> = emptyList(),
        val properties: Map<String, String> = LinkedHashMap()
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(title)
        out.writeString(content)

        out.writeString(state)
        out.writeString(priority)

        out.writeString(scheduled)
        out.writeString(deadline)
        out.writeString(closed)

        out.writeStringList(tags)

        out.writeInt(properties.size)
        for ((key, value) in properties) {
            out.writeString(key)
            out.writeString(value)
        }
    }

    companion object {
        fun getInstance(title: String, content: String? = null): NotePayload {
            return NotePayload(title, content)
        }

        @JvmStatic
        fun fromParcel(parcel: Parcel): NotePayload {
            val title = parcel.readString()
            val content = parcel.readString()

            val state = parcel.readString()
            val priority = parcel.readString()

            val scheduled = parcel.readString()
            val deadline = parcel.readString()
            val closed = parcel.readString()

            val tags = mutableListOf<String>()
            parcel.readStringList(tags)

            val properties = LinkedHashMap<String, String>()
            repeat(parcel.readInt()) {
                val name = parcel.readString()
                val value = parcel.readString()
                properties[name!!] = value!!
            }

            return NotePayload(
                    title!!,
                    content,
                    state,
                    priority,
                    scheduled,
                    deadline,
                    closed,
                    tags,
                    properties
            )
        }

        @JvmField
        val CREATOR: Parcelable.Creator<NotePayload> = object : Parcelable.Creator<NotePayload> {
            override fun createFromParcel(parcel: Parcel): NotePayload {
                return fromParcel(parcel)
            }

            override fun newArray(size: Int): Array<NotePayload?> {
                return arrayOfNulls(size)
            }
        }
    }
}
