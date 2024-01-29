package com.samuelokello.data.mapper

import com.samuelokello.data.remote.model.WinterFellResponseDTO
import com.samuelokello.data.remote.model.WinterfellDetailsDTO
import com.samuelokello.domain.model.WinterFellResponse
import com.samuelokello.domain.model.WinterfellDetailsResponse

fun List<WinterFellResponseDTO>.toDomain(): List<WinterFellResponse> {
    return map {
        WinterFellResponse(
            family = it.family ?: "",
            firstName = it.firstName ?: "",
            fullName = it.fullName ?: "",
            id = it.id,
            image = it.image ?: "",
            imageUrl = it.imageUrl ?: "",
            lastName = it.lastName ?: "",
            title = it.title ?: "",
        )
    }
}

fun WinterfellDetailsDTO.toDomain(): WinterfellDetailsResponse {
    return WinterfellDetailsResponse(
        family = family ?: "",
        firstName = firstName ?: "",
        fullName = fullName ?: "",
        id = id,
        image = image ?: "",
        imageUrl = imageUrl ?: "",
        lastName = lastName ?: "",
        title = title ?: "",
    )
}
