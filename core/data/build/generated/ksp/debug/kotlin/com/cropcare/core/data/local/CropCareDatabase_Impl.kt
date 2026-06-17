package com.cropcare.core.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.cropcare.core.`data`.local.dao.ClimateDao
import com.cropcare.core.`data`.local.dao.ClimateDao_Impl
import com.cropcare.core.`data`.local.dao.PlantDao
import com.cropcare.core.`data`.local.dao.PlantDao_Impl
import com.cropcare.core.`data`.local.dao.SpeciesDao
import com.cropcare.core.`data`.local.dao.SpeciesDao_Impl
import com.cropcare.core.`data`.local.dao.WateringDao
import com.cropcare.core.`data`.local.dao.WateringDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class CropCareDatabase_Impl : CropCareDatabase() {
  private val _plantDao: Lazy<PlantDao> = lazy {
    PlantDao_Impl(this)
  }

  private val _speciesDao: Lazy<SpeciesDao> = lazy {
    SpeciesDao_Impl(this)
  }

  private val _wateringDao: Lazy<WateringDao> = lazy {
    WateringDao_Impl(this)
  }

  private val _climateDao: Lazy<ClimateDao> = lazy {
    ClimateDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "7b8d1f40608d9bb7feb20c52ba156590", "bc938aafb8b540aeb4174500b599a74e") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `plants` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `especieId` INTEGER NOT NULL, `ubicacion` TEXT NOT NULL, `fotoPath` TEXT, `fechaCreacion` INTEGER NOT NULL, `frecuenciaRiegoDias` INTEGER NOT NULL, `cantidadAguaMl` INTEGER NOT NULL, `activa` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `species` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombreComun` TEXT NOT NULL, `nombreCientifico` TEXT NOT NULL, `frecuenciaBaseDias` INTEGER NOT NULL, `cantidadBaseAguaMl` INTEGER NOT NULL, `ajustePorTemperatura` REAL NOT NULL, `ajustePorHumedad` REAL NOT NULL, `consejosLuz` TEXT NOT NULL, `consejosHumedad` TEXT NOT NULL, `consejosAbono` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `watering_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `plantId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `cantidadAguaMl` INTEGER NOT NULL, `completadoPorNotificacion` INTEGER NOT NULL, `notas` TEXT, FOREIGN KEY(`plantId`) REFERENCES `plants`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_watering_records_plantId` ON `watering_records` (`plantId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `climate_config` (`id` INTEGER NOT NULL, `temperaturaPromedio` REAL NOT NULL, `humedadAmbiental` REAL NOT NULL, `estacionActual` TEXT NOT NULL, `fechaUltimaActualizacion` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7b8d1f40608d9bb7feb20c52ba156590')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `plants`")
        connection.execSQL("DROP TABLE IF EXISTS `species`")
        connection.execSQL("DROP TABLE IF EXISTS `watering_records`")
        connection.execSQL("DROP TABLE IF EXISTS `climate_config`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsPlants: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPlants.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("nombre", TableInfo.Column("nombre", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("especieId", TableInfo.Column("especieId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("ubicacion", TableInfo.Column("ubicacion", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("fotoPath", TableInfo.Column("fotoPath", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("fechaCreacion", TableInfo.Column("fechaCreacion", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("frecuenciaRiegoDias", TableInfo.Column("frecuenciaRiegoDias", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("cantidadAguaMl", TableInfo.Column("cantidadAguaMl", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlants.put("activa", TableInfo.Column("activa", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPlants: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPlants: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPlants: TableInfo = TableInfo("plants", _columnsPlants, _foreignKeysPlants,
            _indicesPlants)
        val _existingPlants: TableInfo = read(connection, "plants")
        if (!_infoPlants.equals(_existingPlants)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |plants(com.cropcare.core.data.local.entity.PlantEntity).
              | Expected:
              |""".trimMargin() + _infoPlants + """
              |
              | Found:
              |""".trimMargin() + _existingPlants)
        }
        val _columnsSpecies: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSpecies.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("nombreComun", TableInfo.Column("nombreComun", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("nombreCientifico", TableInfo.Column("nombreCientifico", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("frecuenciaBaseDias", TableInfo.Column("frecuenciaBaseDias", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("cantidadBaseAguaMl", TableInfo.Column("cantidadBaseAguaMl", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("ajustePorTemperatura", TableInfo.Column("ajustePorTemperatura", "REAL",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("ajustePorHumedad", TableInfo.Column("ajustePorHumedad", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("consejosLuz", TableInfo.Column("consejosLuz", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("consejosHumedad", TableInfo.Column("consejosHumedad", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSpecies.put("consejosAbono", TableInfo.Column("consejosAbono", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSpecies: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSpecies: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSpecies: TableInfo = TableInfo("species", _columnsSpecies, _foreignKeysSpecies,
            _indicesSpecies)
        val _existingSpecies: TableInfo = read(connection, "species")
        if (!_infoSpecies.equals(_existingSpecies)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |species(com.cropcare.core.data.local.entity.SpeciesEntity).
              | Expected:
              |""".trimMargin() + _infoSpecies + """
              |
              | Found:
              |""".trimMargin() + _existingSpecies)
        }
        val _columnsWateringRecords: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWateringRecords.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWateringRecords.put("plantId", TableInfo.Column("plantId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWateringRecords.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWateringRecords.put("cantidadAguaMl", TableInfo.Column("cantidadAguaMl", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWateringRecords.put("completadoPorNotificacion",
            TableInfo.Column("completadoPorNotificacion", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWateringRecords.put("notas", TableInfo.Column("notas", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWateringRecords: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysWateringRecords.add(TableInfo.ForeignKey("plants", "CASCADE", "NO ACTION",
            listOf("plantId"), listOf("id")))
        val _indicesWateringRecords: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesWateringRecords.add(TableInfo.Index("index_watering_records_plantId", false,
            listOf("plantId"), listOf("ASC")))
        val _infoWateringRecords: TableInfo = TableInfo("watering_records", _columnsWateringRecords,
            _foreignKeysWateringRecords, _indicesWateringRecords)
        val _existingWateringRecords: TableInfo = read(connection, "watering_records")
        if (!_infoWateringRecords.equals(_existingWateringRecords)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |watering_records(com.cropcare.core.data.local.entity.WateringRecordEntity).
              | Expected:
              |""".trimMargin() + _infoWateringRecords + """
              |
              | Found:
              |""".trimMargin() + _existingWateringRecords)
        }
        val _columnsClimateConfig: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsClimateConfig.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsClimateConfig.put("temperaturaPromedio", TableInfo.Column("temperaturaPromedio",
            "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClimateConfig.put("humedadAmbiental", TableInfo.Column("humedadAmbiental", "REAL",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClimateConfig.put("estacionActual", TableInfo.Column("estacionActual", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsClimateConfig.put("fechaUltimaActualizacion",
            TableInfo.Column("fechaUltimaActualizacion", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysClimateConfig: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesClimateConfig: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoClimateConfig: TableInfo = TableInfo("climate_config", _columnsClimateConfig,
            _foreignKeysClimateConfig, _indicesClimateConfig)
        val _existingClimateConfig: TableInfo = read(connection, "climate_config")
        if (!_infoClimateConfig.equals(_existingClimateConfig)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |climate_config(com.cropcare.core.data.local.entity.ClimateConfigEntity).
              | Expected:
              |""".trimMargin() + _infoClimateConfig + """
              |
              | Found:
              |""".trimMargin() + _existingClimateConfig)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "plants", "species",
        "watering_records", "climate_config")
  }

  public override fun clearAllTables() {
    super.performClear(true, "plants", "species", "watering_records", "climate_config")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PlantDao::class, PlantDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SpeciesDao::class, SpeciesDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WateringDao::class, WateringDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ClimateDao::class, ClimateDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun plantDao(): PlantDao = _plantDao.value

  public override fun speciesDao(): SpeciesDao = _speciesDao.value

  public override fun wateringDao(): WateringDao = _wateringDao.value

  public override fun climateDao(): ClimateDao = _climateDao.value
}
