package com.cropcare.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cropcare.core.data.local.entity.ClimateConfigEntity
import com.cropcare.core.data.local.entity.PlantEntity
import com.cropcare.core.data.local.entity.SpeciesEntity
import com.cropcare.core.data.local.entity.WateringRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants WHERE activa = 1 ORDER BY nombre ASC")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE id = :id")
    fun getPlantById(id: Long): Flow<PlantEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: PlantEntity): Long

    @Update
    suspend fun updatePlant(plant: PlantEntity)

    @Delete
    suspend fun deletePlant(plant: PlantEntity)
}

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM species ORDER BY nombreComun ASC")
    fun getAllSpecies(): Flow<List<SpeciesEntity>>

    @Query("SELECT * FROM species WHERE id = :id")
    fun getSpeciesById(id: Long): Flow<SpeciesEntity?>

    @Query(
        """
        SELECT * FROM species 
        WHERE nombreComun LIKE '%' || :query || '%' 
           OR nombreCientifico LIKE '%' || :query || '%'
        ORDER BY nombreComun ASC
        """
    )
    fun searchSpeciesByName(query: String): Flow<List<SpeciesEntity>>
}

@Dao
interface WateringDao {
    @Query("SELECT * FROM watering_records WHERE plantId = :plantId ORDER BY timestamp DESC")
    fun getRecordsByPlantId(plantId: Long): Flow<List<WateringRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: WateringRecordEntity): Long
}

@Dao
interface ClimateDao {
    @Query("SELECT * FROM climate_config WHERE id = 1")
    fun getClimateConfig(): Flow<ClimateConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveClimateConfig(config: ClimateConfigEntity)
}
