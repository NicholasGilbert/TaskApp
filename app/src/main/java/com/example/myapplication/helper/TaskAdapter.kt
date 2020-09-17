package com.example.myapplication.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.RoomTask
import com.example.myapplication.data.TaskCard

class TaskAdapter(val data: ArrayList<RoomTask>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        holder.txtTime.text = data.get(position).time
        holder.txtName.text = data.get(position).name
        holder.txtNotes.text = data.get(position).note
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTime = itemView.findViewById(R.id.tvTime) as TextView
        val txtName = itemView.findViewById(R.id.tvName) as TextView
        val txtNotes = itemView.findViewById(R.id.tvNotes) as TextView
    }

    fun deleteItem(pos: Int){
        data.removeAt(pos)
    }

    fun getDataArray() : ArrayList<RoomTask>{
        return data
    }
}