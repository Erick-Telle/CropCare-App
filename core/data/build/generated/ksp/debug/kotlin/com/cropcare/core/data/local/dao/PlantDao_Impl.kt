package com.cropcare.core.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.cropcare.core.`data`.local.entity.PlantEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PlantDao_Impl(
  __db: RoomDatabase,
) : PlantDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPlantEntity: EntityInsertAdapter<PlantEntity>

  private val __deleteAdapterOfPlantEntity: EntityDeleteOrUpdateAdapter<PlantEntity>

  private val __updateAdapterOfPlantEntity: EntityDeleteOrUpdateAdapter<PlantEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfPlantEntity = object : EntityInsertAdapter<PlantEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `plants` (`id`,`nombre`,`especieId`,`ubicacion`,`fotoPath`,`fechaCreacion`,`frecuenciaRiegoDias`,`cantidadAguaMl`,`activa`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nombre)
        statement.bindLong(3, entity.especieId)
        statement.bindText(4, entity.ubicacion)
        val _tmpFotoPath: String? = entity.fotoPath
        if (_tmpFotoPath == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpFotoPath)
        }
        statement.bindLong(6, entity.fechaCreacion)
        statement.bindLong(7, entity.frecuenciaRiegoDias.toLong())
        statement.bindLong(8, entity.cantidadAguaMl.toLong())
        val _tmp: Int = if (entity.activa) 1 else 0
        statement.bindLong(9, _tmp.toLong())
      }
    }
    this.__deleteAdapterOfPlantEntity = object : EntityDeleteOrUpdateAdapter<PlantEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `plants` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: PlantEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfPlantEntity = object : EntityDeleteOrUpdateAdapter<PlantEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `plants` SET `id` = ?,`nombre` = ?,`especieId` = ?,`ubicacion` = ?,`fotoPath` = ?,`fechaCreacion` = ?,`frecuenciaRiegoDias` = ?,`cantidadAguaMl` = ?,`activa` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: PlantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nombre)
        statement.bindLong(3, entity.especieId)
        statement.bindText(4, entity.ubicacion)
        val _tmpFotoPath: String? = entity.fotoPath
        if (_tmpFotoPath == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpFotoPath)
        }
        statement.bindLong(6, entity.fechaCreacion)
        statement.bindLong(7, entity.frecuenciaRiegoDias.toLong())
        statement.bindLong(8, entity.cantidadAguaMl.toLong())
        val _tmp: Int = if (entity.activa) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun insertPlant(plant: PlantEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfPlantEntity.insertAndReturnId(_connection, plant)
    _result
  }

  public override suspend fun deletePlant(plant: PlantEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfPlantEntity.handle(_connection, plant)
  }

  public override suspend fun updatePlant(plant: PlantEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfPlantEntity.handle(_connection, plant)
  }

  public override fun getAllPlants(): Flow<List<PlantEntity>> {
    val _sql: String = "SELECT * FROM plants WHERE activa = 1 ORDER BY nombre ASC"
    return createFlow(__db, false, arrayOf("plants")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombre: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfEspecieId: Int = getColumnIndexOrThrow(_stmt, "especieId")
        val _columnIndexOfUbicacion: Int = getColumnIndexOrThrow(_stmt, "ubicacion")
        val _columnIndexOfFotoPath: Int = getColumnIndexOrThrow(_stmt, "fotoPath")
        val _columnIndexOfFechaCreacion: Int = getColumnIndexOrThrow(_stmt, "fechaCreacion")
        val _columnIndexOfFrecuenciaRiegoDias: Int = getColumnIndexOrThrow(_stmt,
            "frecuenciaRiegoDias")
        val _columnIndexOfCantidadAguaMl: Int = getColumnIndexOrThrow(_stmt, "cantidadAguaMl")
        val _columnIndexOfActiva: Int = getColumnIndexOrThrow(_stmt, "activa")
        val _result: MutableList<PlantEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlantEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombre: String
          _tmpNombre = _stmt.getText(_columnIndexOfNombre)
          val _tmpEspecieId: Long
          _tmpEspecieId = _stmt.getLong(_columnIndexOfEspecieId)
          val _tmpUbicacion: String
          _tmpUbicacion = _stmt.getText(_columnIndexOfUbicacion)
          val _tmpFotoPath: String?
          if (_stmt.isNull(_columnIndexOfFotoPath)) {
            _tmpFotoPath = null
          } else {
            _tmpFotoPath = _stmt.getText(_columnIndexOfFotoPath)
          }
          val _tmpFechaCreacion: Long
          _tmpFechaCreacion = _stmt.getLong(_columnIndexOfFechaCreacion)
          val _tmpFrecuenciaRiegoDias: Int
          _tmpFrecuenciaRiegoDias = _stmt.getLong(_columnIndexOfFrecuenciaRiegoDias).toInt()
          val _tmpCantidadAguaMl: Int
          _tmpCantidadAguaMl = _stmt.getLong(_columnIndexOfCantidadAguaMl).toInt()
          val _tmpActiva: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfActiva).toInt()
          _tmpActiva = _tmp != 0
          _item =
              PlantEntity(_tmpId,_tmpNombre,_tmpEspecieId,_tmpUbicacion,_tmpFotoPath,_tmpFechaCreacion,_tmpFrecuenciaRiegoDias,_tmpCantidadAguaMl,_tmpActiva)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getPlantById(id: Long): Flow<PlantEntity?> {
    val _sql: String = "SELECT * FROM plants WHERE id = ?"
    return createFlow(__db, false, arrayOf("plants")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombre: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfEspecieId: Int = getColumnIndexOrThrow(_stmt, "especieId")
        val _columnIndexOfUbicacion: Int = getColumnIndexOrThrow(_stmt, "ubicacion")
        val _columnIndexOfFotoPath: Int = getColumnIndexOrThrow(_stmt, "fotoPath")
        val _columnIndexOfFechaCreacion: Int = getColumnIndexOrThrow(_stmt, "fechaCreacion")
        val _columnIndexOfFrecuenciaRiegoDias: Int = getColumnIndexOrThrow(_stmt,
            "frecuenciaRiegoDias")
        val _columnIndexOfCantidadAguaMl: Int = getColumnIndexOrThrow(_stmt, "cantidadAguaMl")
        val _columnIndexOfActiva: Int = getColumnIndexOrThrow(_stmt, "activa")
        val _result: PlantEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombre: String
          _tmpNombre = _stmt.getText(_columnIndexOfNombre)
          val _tmpEspecieId: Long
          _tmpEspecieId = _stmt.getLong(_columnIndexOfEspecieId)
          val _tmpUbicacion: String
          _tmpUbicacion = _stmt.getText(_columnIndexOfUbicacion)
          val _tmpFotoPath: String?
          if (_stmt.isNull(_columnIndexOfFotoPath)) {
            _tmpFotoPath = null
          } else {
            _tmpFotoPath = _stmt.getText(_columnIndexOfFotoPath)
          }
          val _tmpFechaCreacion: Long
          _tmpFechaCreacion = _stmt.getLong(_columnIndexOfFechaCreacion)
          val _tmpFrecuenciaRiegoDias: Int
          _tmpFrecuenciaRiegoDias = _stmt.getLong(_columnIndexOfFrecuenciaRiegoDias).toInt()
          val _tmpCantidadAguaMl: Int
          _tmpCantidadAguaMl = _stmt.getLong(_columnIndexOfCantidadAguaMl).toInt()
          val _tmpActiva: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfActiva).toInt()
          _tmpActiva = _tmp != 0
          _result =
              PlantEntity(_tmpId,_tmpNombre,_tmpEspecieId,_tmpUbicacion,_tmpFotoPath,_tmpFechaCreacion,_tmpFrecuenciaRiegoDias,_tmpCantidadAguaMl,_tmpActiva)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
