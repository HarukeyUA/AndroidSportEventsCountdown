package com.nazar.assignment.features.sportsList.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nazar.assignment.R
import com.nazar.assignment.databinding.SportEventListItemBinding
import com.nazar.assignment.features.sportsList.list.SportsListAdapter.Companion.TICKER_PAYLOAD
import com.nazar.assignment.features.sportsList.model.EventViewItemModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SportEventListAdapter(private val onEventStarred: (event: EventViewItemModel) -> Unit) :
    ListAdapter<EventViewItemModel, SportEventListAdapter.EventViewHolder>(
        SportEventViewItemDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            SportEventListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onEventStarred
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            if (payloads.first() is String && payloads.first() == TICKER_PAYLOAD) {
                holder.bindTicker(getItem(position))
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class EventViewHolder(
        private val binding: SportEventListItemBinding,
        private val onEventStarred: (event: EventViewItemModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var bindItem: EventViewItemModel? = null

        init {
            binding.starIcon.setOnClickListener {
                bindItem?.also {
                    onEventStarred(it)
                }
            }
        }

        fun bind(event: EventViewItemModel) {
            bindItem = event
            with(binding) {
                firstTeam.text = event.firstTeamName
                secondTeam.text = event.secondTeamName
                setTimeText(event.startTime.time - System.currentTimeMillis())
                starIcon.setImageResource(if (event.isStarred) R.drawable.ic_star else R.drawable.ic_star_outline)
                starIcon.contentDescription =
                    binding.root.resources.getString(if (event.isStarred) R.string.starred_event_description else R.string.not_starred_event_description)
            }
        }

        fun bindTicker(event: EventViewItemModel) {
            setTimeText(event.startTime.time - System.currentTimeMillis())
        }

        private fun setTimeText(millis: Long) {
            val duration = millis.toDuration(DurationUnit.MILLISECONDS)
            duration.toComponents { days, hours, minutes, seconds, _ ->
                binding.countDownText.text = if (days > 0) {
                    binding.root.resources.getString(
                        R.string.event_days_countdown,
                        days,
                        hours,
                        minutes,
                        seconds
                    )
                } else {
                    binding.root.resources.getString(
                        R.string.event_hours_countdown,
                        hours,
                        minutes,
                        seconds
                    )
                }
            }
        }
    }

    class SportEventViewItemDiffUtil : DiffUtil.ItemCallback<EventViewItemModel>() {
        override fun areItemsTheSame(
            oldItem: EventViewItemModel,
            newItem: EventViewItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EventViewItemModel,
            newItem: EventViewItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}
