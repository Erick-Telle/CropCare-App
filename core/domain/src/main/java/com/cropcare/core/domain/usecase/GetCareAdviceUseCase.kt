package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.CareAdviceLevel
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class CareAdvice(
    val species: Species,
    val luzLevel: CareAdviceLevel,
    val humedadLevel: CareAdviceLevel
)

class GetCareAdviceUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository
) {
    suspend operator fun invoke(especieId: Long): CareAdvice? {
        val species = speciesRepository.getSpeciesById(especieId).first() ?: return null
        return CareAdvice(
            species = species,
            luzLevel = parseLevel(species.consejosLuz),
            humedadLevel = parseLevel(species.consejosHumedad)
        )
    }

    private fun parseLevel(text: String): CareAdviceLevel {
        val normalized = text.lowercase()
        return when {
            normalized.contains("alta") -> CareAdviceLevel.ALTA
            normalized.contains("baja") -> CareAdviceLevel.BAJA
            else -> CareAdviceLevel.MEDIA
        }
    }
}
