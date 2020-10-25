package exalt.trashout

object TrashPicker {
    private val labelToTrashRes = mapOf(
        "Cardboard" to R.drawable.ic_recycle_bin_blue,
        "Glass" to R.drawable.ic_recycle_bin_green,
        "Metal" to R.drawable.ic_recycle_bin_yellow,
        "Paper" to R.drawable.ic_recycle_bin_blue,
        "Plastic" to R.drawable.ic_recycle_bin_yellow,
        "Mixed trash" to R.drawable.ic_recycle_bin_black
    )

    fun getTrashForLabel(label: String): Int? = labelToTrashRes[label]
}