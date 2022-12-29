package com.passerby.cfttestproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.passerby.cfttestproject.R
import com.passerby.cfttestproject.bussiness.room.RequestsEntity

class RequestsRVAdapter(
    private val requestClickListener: RequestClickListener,
) : RecyclerView.Adapter<RequestsRVAdapter.ViewHolder>() {

    private val requestsList = ArrayList<RequestsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li =
            LayoutInflater.from(parent.context).inflate(R.layout.request_rv_item, parent, false)
        return ViewHolder(li)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binTV.text = requestsList[position].request
        holder.insertBTN.setOnClickListener { requestClickListener.onRequestClickListener(requestsList[position]) }
    }

    override fun getItemCount() = requestsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binTV: TextView = itemView.findViewById(R.id.bin_tv)
        val insertBTN: AppCompatImageButton = itemView.findViewById(R.id.bin_insert_btn)
    }

    fun updateList(newList: List<RequestsEntity>){
        requestsList.clear()
        requestsList.addAll(newList)
        notifyDataSetChanged()
    }

    interface RequestClickListener {
        fun onRequestClickListener(item: RequestsEntity)
    }
}
