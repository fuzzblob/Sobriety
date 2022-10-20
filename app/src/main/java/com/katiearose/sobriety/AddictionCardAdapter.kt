package com.katiearose.sobriety

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katiearose.sobriety.activities.Main
import com.katiearose.sobriety.utils.convertSecondsToString
import com.katiearose.sobriety.utils.secondsFromNow

class AddictionCardAdapter(private val context: Context) :
    RecyclerView.Adapter<AddictionCardAdapter.AddictionCardViewHolder>() {

    private lateinit var onButtonDeleteClickListener: View.OnClickListener
    private lateinit var onButtonRelapseClickListener: View.OnClickListener

    fun setOnButtonDeleteClickListener(onButtonDeleteClickListener: View.OnClickListener) {
        this.onButtonDeleteClickListener = onButtonDeleteClickListener
    }

    fun setOnButtonRelapseClickListener(onButtonRelapseClickListener: View.OnClickListener) {
        this.onButtonRelapseClickListener = onButtonRelapseClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddictionCardViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_addiction, parent, false)
        return AddictionCardViewHolder(itemView, onButtonDeleteClickListener, onButtonRelapseClickListener)
    }

    override fun onBindViewHolder(
        holder: AddictionCardViewHolder,
        position: Int
    ) {
        val addiction = Main.addictions[position]

        holder.textViewName.text = addiction.name
        holder.textViewTime.text =
            context.convertSecondsToString(addiction.lastRelapse.secondsFromNow())
        holder.textViewAverage.visibility = if (addiction.averageRelapseDuration == -1L) View.GONE else View.VISIBLE
        holder.textViewAverage.text =
            context.getString(R.string.recent_avg, context.convertSecondsToString(addiction.averageRelapseDuration))
    }

    override fun getItemCount() = Main.addictions.size

    class AddictionCardViewHolder(itemView: View, onButtonDeleteClickListener: View.OnClickListener,
                                  onButtonRelapseClickListener: View.OnClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewAddictionName)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        val textViewAverage: TextView = itemView.findViewById(R.id.textViewAverage)
        init {
            itemView.findViewById<ImageView>(R.id.imageDelete).apply {
                tag = this@AddictionCardViewHolder
                setOnClickListener(onButtonDeleteClickListener)
            }
            itemView.findViewById<ImageView>(R.id.imageReset).apply {
                tag = this@AddictionCardViewHolder
                setOnClickListener(onButtonRelapseClickListener)
            }
        }
    }
}