package com.sprintalert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sprintalert.db.SprintRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordAdapter(
    private val records: List<SprintRecord>,
    private val onDelete: (SprintRecord) -> Unit
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(view, onDelete)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount() = records.size

    class RecordViewHolder(
        itemView: View,
        private val onDelete: (SprintRecord) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val distanceText: TextView = itemView.findViewById(R.id.record_distance)
        private val timeText: TextView = itemView.findViewById(R.id.record_time)
        private val dateText: TextView = itemView.findViewById(R.id.record_date)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_button)

        fun bind(record: SprintRecord) {
            distanceText.text = String.format("%.2f m", record.distance)
            timeText.text = String.format("%.2f s", record.timeInSeconds)
            
            val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
            dateText.text = dateFormat.format(Date(record.date))

            deleteButton.setOnClickListener {
                onDelete(record)
            }
        }
    }
}
