package com.soufianekre.redpass.ui.views




import android.content.Context
import android.util.AttributeSet
import android.widget.ListView


class NonScrollableListView : ListView {

    constructor(context: Context) : super(context) {}


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }


    fun justifyListViewHeightBasedOnChildren() {

        val adapter = adapter ?: return

        val vg = this
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, vg)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        val par = layoutParams
        par.height = totalHeight + dividerHeight * (adapter.count - 1)
        layoutParams = par
        requestLayout()
    }


}
