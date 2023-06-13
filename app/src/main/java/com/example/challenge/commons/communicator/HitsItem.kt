package com.example.challenge.commons.communicator

import com.example.challenge.domain.entities.HitsList

interface HitsItem {
    fun onHitsItemClickListener(hitsList: HitsList?)
}
