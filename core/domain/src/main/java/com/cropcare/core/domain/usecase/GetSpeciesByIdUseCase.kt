package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpeciesByIdUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository
) {
    operator fun invoke(id: Long): Flow<Species?> = speciesRepository.getSpeciesById(id)
}
