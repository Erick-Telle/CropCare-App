package com.cropcare.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cropcare.core.data.local.dao.ClimateDao
import com.cropcare.core.data.local.dao.PlantDao
import com.cropcare.core.data.local.dao.SpeciesDao
import com.cropcare.core.data.local.dao.WateringDao
import com.cropcare.core.data.local.entity.ClimateConfigEntity
import com.cropcare.core.data.local.entity.PlantEntity
import com.cropcare.core.data.local.entity.SpeciesEntity
import com.cropcare.core.data.local.entity.WateringRecordEntity

@Database(
    entities = [
        PlantEntity::class,
        SpeciesEntity::class,
        WateringRecordEntity::class,
        ClimateConfigEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CropCareDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun wateringDao(): WateringDao
    abstract fun climateDao(): ClimateDao

    companion object {
        const val DATABASE_NAME = "cropcare.db"

        val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                SpeciesSeedData.species.forEach { species ->
                    db.execSQL(
                        """
                        INSERT INTO species (
                            nombreComun, nombreCientifico, frecuenciaBaseDias,
                            cantidadBaseAguaMl, ajustePorTemperatura, ajustePorHumedad,
                            consejosLuz, consejosHumedad, consejosAbono
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """.trimIndent(),
                        arrayOf(
                            species.nombreComun,
                            species.nombreCientifico,
                            species.frecuenciaBaseDias,
                            species.cantidadBaseAguaMl,
                            species.ajustePorTemperatura,
                            species.ajustePorHumedad,
                            species.consejosLuz,
                            species.consejosHumedad,
                            species.consejosAbono
                        )
                    )
                }
            }
        }
    }
}
