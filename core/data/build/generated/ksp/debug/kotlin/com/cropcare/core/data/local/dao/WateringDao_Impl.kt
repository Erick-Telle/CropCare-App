package com.cropcare.core.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.cropcare.core.`data`.local.entity.WateringRecordEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class WateringDao_Impl(
  __db: RoomDatabase,
) : WateringDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWateringRecordEntity: EntityInsertAdapter<WateringRecordEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWateringRecordEntity = object :
        EntityInsertAdapter<WateringRecordEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `watering_records` (`id`,`plantId`,`timestamp`,`cantidadAguaMl`,`completadoPorNotificacion`,`notas`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WateringRecordEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.plantId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.cantidadAguaMl.toLong())
        val _tmp: Int = if (entity.completadoPorNotificacion) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        val _tmpNotas: String? = entity.notas
        if (_tmpNotas == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpNotas)
        }
      }
    }
  }

  public override suspend fun insertRecord(record: WateringRecordEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfWateringRecordEntity.insertAndReturnId(_connection, record)
    _result
  }

  public override fun getRecordsByPlantId(plantId: Long): Flow<List<WateringRecordEntity>> {
    val _sql: String = "SELECT * FROM watering_records WHERE plantId = ? ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("watering_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, plantId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlantId: Int = getColumnIndexOrThrow(_stmt, "plantId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfCantidadAguaMl: Int = getColumnIndexOrThrow(_stmt, "cantidadAguaMl")
        val _columnIndexOfCompletadoPorNotificacion: Int = getColumnIndexOrThrow(_stmt,
            "completadoPorNotificacion")
        val _columnIndexOfNotas: Int = getColumnIndexOrThrow(_stmt, "notas")
        val _result: MutableList<WateringRecordEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WateringRecordEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPlantId: Long
          _tmpPlantId = _stmt.getLong(_columnIndexOfPlantId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpCantidadAguaMl: Int
          _tmpCantidadAguaMl = _stmt.getLong(_columnIndexOfCantidadAguaMl).toInt()
          val _tmpCompletadoPorNotificacion: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfCompletadoPorNotificacion).toInt()
          _tmpCompletadoPorNotificacion = _tmp != 0
          val _tmpNotas: String?
          if (_stmt.isNull(_columnIndexOfNotas)) {
            _tmpNotas = null
          } else {
            _tmpNotas = _stmt.getText(_columnIndexOfNotas)
          }
          _item =
              WateringRecordEntity(_tmpId,_tmpPlantId,_tmpTimestamp,_tmpCantidadAguaMl,_tmpCompletadoPorNotificacion,_tmpNotas)
          _result.add(_item)
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
