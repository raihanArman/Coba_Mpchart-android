package com.simoku.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.simoku.R
import com.simoku.databinding.ItemTableBinding
import com.simoku.model.DataModel

class TableAdapter:RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private val listData = ArrayList<DataModel>()

    fun setList(list: List<DataModel>){
        this.listData.clear()
        this.listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTableBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemTableBinding = DataBindingUtil.inflate(inflater, R.layout.item_table, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.binding.tvNo.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvFixCo.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvNO2.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvFixSO2.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvFixO3.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvfixPM10.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvDate.setBackgroundResource(R.drawable.table_header_cell_bg)
            holder.binding.tvNo.setText("No")
            holder.binding.tvFixCo.setText("fixCO")
            holder.binding.tvNO2.setText("fixNO2")
            holder.binding.tvFixSO2.setText("fixSO2")
            holder.binding.tvFixO3.setText("fixO3")
            holder.binding.tvfixPM10.setText("fixPM10")
            holder.binding.tvDate.setText("Tanggal")
        } else {
            val dataModel = listData[position-1]
            var positionData = position
            holder.binding.tvFixCo.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvNO2.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvFixSO2.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvFixO3.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvfixPM10.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvDate.setBackgroundResource(R.drawable.table_content_cell_bg)
            holder.binding.tvNo.setText("${positionData++}")
            holder.binding.tvFixCo.setText(dataModel.fixCO)
            holder.binding.tvNO2.setText(dataModel.fixNO2)
            holder.binding.tvFixSO2.setText(dataModel.fixSO2)
            holder.binding.tvFixO3.setText(dataModel.fixO3)
            holder.binding.tvfixPM10.setText(dataModel.fixPM10)
            holder.binding.tvDate.setText(DateFormat.format("dd-MM-yyyy HH:mm", dataModel.date).toString())
        }
    }

    override fun getItemCount(): Int {
        return listData.size+1
    }

}