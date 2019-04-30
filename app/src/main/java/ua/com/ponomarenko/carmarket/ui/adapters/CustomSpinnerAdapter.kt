package ua.com.ponomarenko.carmarket.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ua.com.ponomarenko.carmarket.R


/**
 * Created by Ponomarenko Oleh on 4/29/2019.
 */
class CustomSpinnerAdapter(private val context: Context, private val list: Map<String, String>?)
    : BaseAdapter() {

    private var keys: Array<String>? = list?.keys?.toTypedArray()

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        var currentConvertView = convertView
        if (currentConvertView == null) {
            currentConvertView = layoutInflater.inflate(R.layout.spinner_item_white, parent, false)
            viewHolder = ViewHolder(currentConvertView)
            currentConvertView.tag = viewHolder

        } else {
            viewHolder = currentConvertView.tag as ViewHolder
        }

        viewHolder.title.text = list?.get(keys?.get(position))
        return currentConvertView!!
    }

    override fun getItem(position: Int): String? {
        return keys?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    class ViewHolder(view: View) {
        var title: TextView = view.findViewById(R.id.spinner_title)
    }


}