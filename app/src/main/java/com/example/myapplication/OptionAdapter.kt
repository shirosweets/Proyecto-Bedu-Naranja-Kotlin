package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class OptionAdapter(
    private val click_listener: (String) -> Unit,
    private val options_list: List<Option>
): RecyclerView.Adapter<OptionAdapter.OptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OptionHolder(
            inflater.inflate(
                R.layout.fragment_profile_option_contact,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OptionHolder, position: Int) {
        val currentOption: Option = options_list[position]

        holder.render(currentOption)
        holder.itemView.setOnClickListener { click_listener(currentOption.name) }
    }

    override fun getItemCount(): Int = options_list.size

    inner class OptionHolder(view: View): RecyclerView.ViewHolder(view) {
        private val optionName: TextView = view.findViewById(R.id.option_name)
        private val optionImage: ImageView = view.findViewById(R.id.option_image)

        fun render(option: Option) {
            optionName.text = option.name
            optionImage.setImageResource(option.iconId)
        }
    }
}
