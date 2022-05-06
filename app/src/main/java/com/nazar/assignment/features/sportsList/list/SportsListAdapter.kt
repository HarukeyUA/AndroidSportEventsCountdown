package com.nazar.assignment.features.sportsList.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nazar.assignment.R
import com.nazar.assignment.databinding.SportListItemBinding
import com.nazar.assignment.features.sportsList.model.EventViewItemModel
import com.nazar.assignment.features.sportsList.model.SportViewItemModel

class SportsListAdapter(
    private val onHeaderClicked: (item: SportViewItemModel) -> Unit,
    private val onEventStarred: (event: EventViewItemModel) -> Unit
) :
    ListAdapter<SportViewItemModel, SportsListAdapter.SportViewHolder>(SportViewItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        return SportViewHolder(
            SportListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onHeaderClicked,
            onEventStarred
        )
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: SportViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            if (payloads.first() is String && payloads.first() == TICKER_PAYLOAD) {
                holder.bindTicker()
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class SportViewHolder(
        private val binding: SportListItemBinding,
        private val onHeaderClicked: (item: SportViewItemModel) -> Unit,
        private val onEventStarred: (event: EventViewItemModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var bindItem: SportViewItemModel? = null
        private val eventsAdapter by lazy { SportEventListAdapter(onEventStarred) }

        init {
            binding.eventsList.itemAnimator =
                DefaultItemAnimator().apply { supportsChangeAnimations = false }
            binding.eventsList.addItemDecoration(SportEventItemDecoration())
            binding.sportContainer.setOnClickListener {
                bindItem?.also {
                    onHeaderClicked(it)
                }
            }
        }

        fun bind(sport: SportViewItemModel) {
            bindItem = sport
            with(binding) {
                if (eventsList.adapter == null) {
                    eventsList.adapter = eventsAdapter
                }
                eventsAdapter.submitList(sport.events)
                sportNameText.text = sport.name
                sportNameText.setCompoundDrawablesWithIntrinsicBounds(sport.icon, 0, 0, 0)

                eventsList.isVisible = sport.isExpanded

                expandIcon.setImageResource(if (sport.isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
            }
        }

        fun bindTicker() {
            eventsAdapter.notifyItemRangeChanged(0, eventsAdapter.itemCount, TICKER_PAYLOAD)
        }
    }

    class SportViewItemDiffUtil : DiffUtil.ItemCallback<SportViewItemModel>() {
        override fun areItemsTheSame(
            oldItem: SportViewItemModel,
            newItem: SportViewItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SportViewItemModel,
            newItem: SportViewItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    companion object {
        const val TICKER_PAYLOAD = "TICKER"
    }
}
