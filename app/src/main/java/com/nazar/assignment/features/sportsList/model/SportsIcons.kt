package com.nazar.assignment.features.sportsList.model

import androidx.annotation.DrawableRes
import com.nazar.assignment.R

enum class SportsIcons(val sportId: String, @DrawableRes val icon: Int) {
    FOOTBALL("FOOT", R.drawable.ic_sports_soccer),
    BASKETBALL("BASK", R.drawable.ic_sports_basketball),
    TENNIS("TENN", R.drawable.ic_sports_baseball),
    TABLE_TENNIS("TABL", R.drawable.ic_table),
    VOLLEYBALL("VOLL", R.drawable.ic_sports_volleyball),
    ESPORTS("ESPS", R.drawable.ic_sports_esports),
    HOCKEY("ICEH", R.drawable.ic_sports_hockey),
    BEACH_VOLLEYBALL("BCHV", R.drawable.ic_sports_volleyball),
    BADMINTON("BADM", R.drawable.ic_sports_tennis)
}
