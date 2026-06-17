package com.cropcare.core.`data`.local.dao

import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.sqlite.SQLiteStatement
import com.cropcare.core.`data`.local.entity.SpeciesEntity
import javax.`annotation`.processing.Generated
import kotlin.Float
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
public class SpeciesDao_Impl(
  __db: RoomDatabase,
) : SpeciesDao {
  private val __db: RoomDatabase
  init {
    this.__db = __db
  }

  public override fun getAllSpecies(): Flow<List<SpeciesEntity>> {
    val _sql: String = "SELECT * FROM species ORDER BY nombreComun ASC"
    return createFlow(__db, false, arrayOf("species")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombreComun: Int = getColumnIndexOrThrow(_stmt, "nombreComun")
        val _columnIndexOfNombreCientifico: Int = getColumnIndexOrThrow(_stmt, "nombreCientifico")
        val _columnIndexOfFrecuenciaBaseDias: Int = getColumnIndexOrThrow(_stmt,
            "frecuenciaBaseDias")
        val _columnIndexOfCantidadBaseAguaMl: Int = getColumnIndexOrThrow(_stmt,
            "cantidadBaseAguaMl")
        val _columnIndexOfAjustePorTemperatura: Int = getColumnIndexOrThrow(_stmt,
            "ajustePorTemperatura")
        val _columnIndexOfAjustePorHumedad: Int = getColumnIndexOrThrow(_stmt, "ajustePorHumedad")
        val _columnIndexOfConsejosLuz: Int = getColumnIndexOrThrow(_stmt, "consejosLuz")
        val _columnIndexOfConsejosHumedad: Int = getColumnIndexOrThrow(_stmt, "consejosHumedad")
        val _columnIndexOfConsejosAbono: Int = getColumnIndexOrThrow(_stmt, "consejosAbono")
        val _result: MutableList<SpeciesEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SpeciesEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombreComun: String
          _tmpNombreComun = _stmt.getText(_columnIndexOfNombreComun)
          val _tmpNombreCientifico: String
          _tmpNombreCientifico = _stmt.getText(_columnIndexOfNombreCientifico)
          val _tmpFrecuenciaBaseDias: Int
          _tmpFrecuenciaBaseDias = _stmt.getLong(_columnIndexOfFrecuenciaBaseDias).toInt()
          val _tmpCantidadBaseAguaMl: Int
          _tmpCantidadBaseAguaMl = _stmt.getLong(_columnIndexOfCantidadBaseAguaMl).toInt()
          val _tmpAjustePorTemperatura: Float
          _tmpAjustePorTemperatura = _stmt.getDouble(_columnIndexOfAjustePorTemperatura).toFloat()
          val _tmpAjustePorHumedad: Float
          _tmpAjustePorHumedad = _stmt.getDouble(_columnIndexOfAjustePorHumedad).toFloat()
          val _tmpConsejosLuz: String
          _tmpConsejosLuz = _stmt.getText(_columnIndexOfConsejosLuz)
          val _tmpConsejosHumedad: String
          _tmpConsejosHumedad = _stmt.getText(_columnIndexOfConsejosHumedad)
          val _tmpConsejosAbono: String
          _tmpConsejosAbono = _stmt.getText(_columnIndexOfConsejosAbono)
          _item =
              SpeciesEntity(_tmpId,_tmpNombreComun,_tmpNombreCientifico,_tmpFrecuenciaBaseDias,_tmpCantidadBaseAguaMl,_tmpAjustePorTemperatura,_tmpAjustePorHumedad,_tmpConsejosLuz,_tmpConsejosHumedad,_tmpConsejosAbono)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSpeciesById(id: Long): Flow<SpeciesEntity?> {
    val _sql: String = "SELECT * FROM species WHERE id = ?"
    return createFlow(__db, false, arrayOf("species")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombreComun: Int = getColumnIndexOrThrow(_stmt, "nombreComun")
        val _columnIndexOfNombreCientifico: Int = getColumnIndexOrThrow(_stmt, "nombreCientifico")
        val _columnIndexOfFrecuenciaBaseDias: Int = getColumnIndexOrThrow(_stmt,
            "frecuenciaBaseDias")
        val _columnIndexOfCantidadBaseAguaMl: Int = getColumnIndexOrThrow(_stmt,
            "cantidadBaseAguaMl")
        val _columnIndexOfAjustePorTemperatura: Int = getColumnIndexOrThrow(_stmt,
            "ajustePorTemperatura")
        val _columnIndexOfAjustePorHumedad: Int = getColumnIndexOrThrow(_stmt, "ajustePorHumedad")
        val _columnIndexOfConsejosLuz: Int = getColumnIndexOrThrow(_stmt, "consejosLuz")
        val _columnIndexOfConsejosHumedad: Int = getColumnIndexOrThrow(_stmt, "consejosHumedad")
        val _columnIndexOfConsejosAbono: Int = getColumnIndexOrThrow(_stmt, "consejosAbono")
        val _result: SpeciesEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombreComun: String
          _tmpNombreComun = _stmt.getText(_columnIndexOfNombreComun)
          val _tmpNombreCientifico: String
          _tmpNombreCientifico = _stmt.getText(_columnIndexOfNombreCientifico)
          val _tmpFrecuenciaBaseDias: Int
          _tmpFrecuenciaBaseDias = _stmt.getLong(_columnIndexOfFrecuenciaBaseDias).toInt()
          val _tmpCantidadBaseAguaMl: Int
          _tmpCantidadBaseAguaMl = _stmt.getLong(_columnIndexOfCantidadBaseAguaMl).toInt()
          val _tmpAjustePorTemperatura: Float
          _tmpAjustePorTemperatura = _stmt.getDouble(_columnIndexOfAjustePorTemperatura).toFloat()
          val _tmpAjustePorHumedad: Float
          _tmpAjustePorHumedad = _stmt.getDouble(_columnIndexOfAjustePorHumedad).toFloat()
          val _tmpConsejosLuz: String
          _tmpConsejosLuz = _stmt.getText(_columnIndexOfConsejosLuz)
          val _tmpConsejosHumedad: String
          _tmpConsejosHumedad = _stmt.getText(_columnIndexOfConsejosHumedad)
          val _tmpConsejosAbono: String
          _tmpConsejosAbono = _stmt.getText(_columnIndexOfConsejosAbono)
          _result =
              SpeciesEntity(_tmpId,_tmpNombreComun,_tmpNombreCientifico,_tmpFrecuenciaBaseDias,_tmpCantidadBaseAguaMl,_tmpAjustePorTemperatura,_tmpAjustePorHumedad,_tmpConsejosLuz,_tmpConsejosHumedad,_tmpConsejosAbono)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchSpeciesByName(query: String): Flow<List<SpeciesEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM species 
        |        WHERE nombreComun LIKE '%' || ? || '%' 
        |           OR nombreCientifico LIKE '%' || ? || '%'
        |        ORDER BY nombreComun ASC
        |        
        """.trimMargin()
    return createFlow(__db, false, arrayOf("species")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombreComun: Int = getColumnIndexOrThrow(_stmt, "nombreComun")
        val _columnIndexOfNombreCientifico: Int = getColumnIndexOrThrow(_stmt, "nombreCientifico")
        val _columnIndexOfFrecuenciaBaseDias: Int = getColumnIndexOrThrow(_stmt,
            "frecuenciaBaseDias")
        val _columnIndexOfCantidadBaseAguaMl: Int = getColumnIndexOrThrow(_stmt,
            "cantidadBaseAguaMl")
        val _columnIndexOfAjustePorTemperatura: Int = getColumnIndexOrThrow(_stmt,
            "ajustePorTemperatura")
        val _columnIndexOfAjustePorHumedad: Int = getColumnIndexOrThrow(_stmt, "ajustePorHumedad")
        val _columnIndexOfConsejosLuz: Int = getColumnIndexOrThrow(_stmt, "consejosLuz")
        val _columnIndexOfConsejosHumedad: Int = getColumnIndexOrThrow(_stmt, "consejosHumedad")
        val _columnIndexOfConsejosAbono: Int = getColumnIndexOrThrow(_stmt, "consejosAbono")
        val _result: MutableList<SpeciesEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SpeciesEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombreComun: String
          _tmpNombreComun = _stmt.getText(_columnIndexOfNombreComun)
          val _tmpNombreCientifico: String
          _tmpNombreCientifico = _stmt.getText(_columnIndexOfNombreCientifico)
          val _tmpFrecuenciaBaseDias: Int
          _tmpFrecuenciaBaseDias = _stmt.getLong(_columnIndexOfFrecuenciaBaseDias).toInt()
          val _tmpCantidadBaseAguaMl: Int
          _tmpCantidadBaseAguaMl = _stmt.getLong(_columnIndexOfCantidadBaseAguaMl).toInt()
          val _tmpAjustePorTemperatura: Float
          _tmpAjustePorTemperatura = _stmt.getDouble(_columnIndexOfAjustePorTemperatura).toFloat()
          val _tmpAjustePorHumedad: Float
          _tmpAjustePorHumedad = _stmt.getDouble(_columnIndexOfAjustePorHumedad).toFloat()
          val _tmpConsejosLuz: String
          _tmpConsejosLuz = _stmt.getText(_columnIndexOfConsejosLuz)
          val _tmpConsejosHumedad: String
          _tmpConsejosHumedad = _stmt.getText(_columnIndexOfConsejosHumedad)
          val _tmpConsejosAbono: String
          _tmpConsejosAbono = _stmt.getText(_columnIndexOfConsejosAbono)
          _item =
              SpeciesEntity(_tmpId,_tmpNombreComun,_tmpNombreCientifico,_tmpFrecuenciaBaseDias,_tmpCantidadBaseAguaMl,_tmpAjustePorTemperatura,_tmpAjustePorHumedad,_tmpConsejosLuz,_tmpConsejosHumedad,_tmpConsejosAbono)
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
