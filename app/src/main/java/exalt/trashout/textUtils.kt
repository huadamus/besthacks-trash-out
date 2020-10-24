package exalt.trashout

import android.content.Context

fun cutResponse(response: String): Int {
    if(response.isEmpty() || response.length < 2) {
        return -1
    }
    return response.substring(response.length - 1).toInt()
}

fun getLabel(context: Context, id: Int): String {
    if(id == -1) {
        return context.getString(R.string.error)
    }
    return context.resources.getStringArray(R.array.labels)[id]
}
