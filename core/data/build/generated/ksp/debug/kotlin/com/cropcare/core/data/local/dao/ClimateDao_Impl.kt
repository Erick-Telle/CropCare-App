package com.cropcare.core.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.cropcare.core.`data`.local.entity.ClimateConfigEntity
import javax.`annotation`.processing.Generated
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ClimateDao_Impl(
  __db: RoomDatabase,
) : ClimateDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfClimateConfigEntity: EntityInsertAdapter<ClimateConfigEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfClimateConfigEntity = object : EntityInsertAdapter<ClimateConfigEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `climate_config` (`id`,`temperaturaPromedio`,`humedadAmbiental`,`estacionActual`,`fechaUltimaActualizacion`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ClimateConfigEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.temperaturaPromedio.toDouble())
        statement.bindDouble(3, entity.humedadAmbiental.toDouble())
        statement.bindText(4, entity.estacionActual)
        statement.bindLong(5, entity.fechaUltimaActualizacion)
      }
    }
  }

  public override suspend fun saveClimateConfig(config: ClimateConfigEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfClimateConfigEntity.insert(_connection, config)
  }

  public override fun getClimateConfig(): Flow<ClimateConfigEntity?> {
    val _sql: String = "SELECT * FROM climate_config WHERE id = 1"
    return createFlow(__db, false, arrayOf("climate_config")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemperaturaPromedio: Int = getColumnIndexOrThrow(_stmt,
            "temperaturaPromedio")
        val _columnIndexOfHumedadAmbiental: Int = getColumnIndexOrThrow(_stmt, "humedadAmbiental")
        val _columnIndexOfEstacionActual: Int = getColumnIndexOrThrow(_stmt, "estacionActual")
        val _columnIndexOfFechaUltimaActualizacion: Int = getColumnIndexOrThrow(_stmt,
            "fechaUltimaActualizacion")
        val _result: ClimateConfigEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTemperaturaPromedio: Float
          _tmpTemperaturaPromedio = _stmt.getDouble(_columnIndexOfTemperaturaPromedio).toFloat()
          val _tmpHumedadAmbiental: Float
          _tmpHumedadAmbiental = _stmt.getDouble(_columnIndexOfHumedadAmbiental).toFloat()
          val _tmpEstacionActual: String
          _tmpEstacionActual = _stmt.getText(_columnIndexOfEstacionActual)
          val _tmpFechaUltimaActualizacion: Long
          _tmpFechaUltimaActualizacion = _stmt.getLong(_columnIndexOfFechaUltimaActualizacion)
          _result =
              ClimateConfigEntity(_tmpId,_tmpTemperaturaPromedio,_tmpHumedadAmbiental,_tmpEstacionActual,_tmpFechaUltimaActualizacion)
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
