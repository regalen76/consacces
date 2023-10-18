package com.regalen.consacces.repository

import com.regalen.consacces.utils.exceptions.DataDeleteFailedException
import com.regalen.consacces.utils.exceptions.DataNotFoundException
import com.regalen.consacces.utils.exceptions.DataSaveFailedException
import com.regalen.consacces.utils.exceptions.DataUpdateFailedException
import com.regalen.consacces.utils.getSortQuery
import com.regalen.consacces.utils.queryPagination
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.util.*

@Suppress("SqlSourceToSinkFlow")
abstract class BaseRepository {

    protected val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var jdbcTemplate:JdbcTemplate

    /**
     * # check data is existed in DB or not
     * @param sql must use select count (return 1 integer value)
     * */
    protected fun isDataFound(sql: String, params: ArrayList<Any?>): Boolean{
        logger.info("query: $sql")
        logger.info("param: $params")
        return jdbcTemplate.queryForObject(
            sql,
            Int::class.java,
            *params.toTypedArray(),
        ) >= 1
    }

    @Throws(DataSaveFailedException::class)
    protected fun addQuery(sql: String, params: ArrayList<Any?>, tag: String){
        logger.info("query: $sql")
        logger.info("params: $params")
        jdbcTemplate.update(
            sql,
            *params.toTypedArray()
        ).takeIf {it == 0}?.run{
            throw DataSaveFailedException(tag)
        }
    }

    protected inline fun <reified T> getDataWithPagination(
        baseSql: String,
        params: ArrayList<Any?>,
        pageable: Pageable?,
        defaultSortQuery: String = "",
        defaultSortDirection: Sort.Direction?,
    ) : List<T> {
        val sql = buildString {
            append(baseSql)
            // ORDER BY
            append(pageable.getSortQuery(defaultSortQuery, defaultSortDirection ?: Sort.Direction.ASC))

            // PAGING
            append(pageable.queryPagination())
        }
        logger.info("query: $sql")
        logger.info("params: $params")
        return jdbcTemplate.query(
            sql,
            BeanPropertyRowMapper(T::class.java),
            *params.toTypedArray(),
        )
    }

    protected fun <T> getDataWithPaginationRowMap(
        baseSql: String,
        params: ArrayList<Any?>,
        pageable: Pageable?,
        defaultSortQuery: String = "",
        defaultSortDirection: Sort.Direction?,
        rowMapper: RowMapper<T>
    ) : List<T> {
        val sql = buildString {
            append(baseSql)
            // ORDER BY
            append(pageable.getSortQuery(defaultSortQuery, defaultSortDirection ?: Sort.Direction.ASC))

            // PAGING
            append(pageable.queryPagination())
        }
        logger.info("query: $sql")
        logger.info("params: $params")
        return jdbcTemplate.query(
            sql,
            rowMapper,
            *params.toTypedArray(),
        )
    }

    protected inline fun <reified T> getDataListWithoutPagination(
        sql: String,
        params: ArrayList<Any?>,
        defaultSortQuery: String = "",
        defaultSortDirection: Sort.Direction?,
    ) : List<T> {
        val completeSql = buildString {
            append(sql)
            append("ORDER BY $defaultSortQuery $defaultSortDirection ")
        }
        logger.info("query: $completeSql")
        return jdbcTemplate.query(
            completeSql,
            BeanPropertyRowMapper(T::class.java),
            *params.toTypedArray(),
        )
    }

    protected fun <T> getDataListWithoutPaginationRowMap(
        sql: String,
        params: ArrayList<Any?>,
        defaultSortQuery: String = "",
        defaultSortDirection: Sort.Direction?,
        mapper: RowMapper<T>
    ) : List<T> {
        val completeSql = buildString {
            append(sql)
            append("ORDER BY $defaultSortQuery $defaultSortDirection ")
        }
        logger.info("query: $completeSql")
        return jdbcTemplate.query(
            completeSql,
            mapper,
            *params.toTypedArray(),
        )
    }

    /**
     * Get single data
     *
     * @param params user for inserted into query
     * @param sql query get data
     * @param tag for throw in error
     *
     * @throws DataNotFoundException if data not found
     * @return single object data
     * */
    protected inline fun <reified T> getData(
        sql: String,
        params: ArrayList<Any?>,
        tag: String = "",
    ) : T {
        logger.info("query: $sql")
        logger.info("params: $params")
        try {
            return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper(T::class.java),
                *params.toTypedArray(),
            )[0]
        }catch (e: IndexOutOfBoundsException){
            throw DataNotFoundException(fieldCauseError = tag)
        }
    }
    protected fun <T> getDataRowMap(
        sql: String,
        params: ArrayList<Any?>,
        tag: String = "",
        mapper: RowMapper<T>
    ) : T {
        logger.info("query: $sql")
        logger.info("params: $params")
        try {
            return jdbcTemplate.query(
                sql,
                mapper,
                *params.toTypedArray(),
            )[0]
        }catch (e: IndexOutOfBoundsException){
            throw DataNotFoundException(fieldCauseError = tag)
        }
    }
    protected fun countRow(sql: String, params: ArrayList<Any?>): Int {
        val countQuery = buildString {
            append("SELECT COUNT(*) ")
            append("FROM ( ")
            append("$sql ")
            append(") ")
        }
        logger.info("query: $sql")
        logger.info("params: $params")
        return jdbcTemplate.queryForObject(
            countQuery,
            Int::class.java,
            *(params.toTypedArray()),
        )
    }

    fun delete (sql: String, params: ArrayList<Any?>, tag: String){
        logger.info("query: $sql")
        logger.info("params: $params")
        jdbcTemplate.update(
            sql,
            *params.toTypedArray()
        ).takeIf {it == 0}?.run{
            throw DataDeleteFailedException(tag)
        }
    }

    fun update (sql: String, params: ArrayList<Any?>, tag: String){
        logger.info("query: $sql")
        logger.info("params: $params")
        jdbcTemplate.update(
            sql,
            *params.toTypedArray()
        ).takeIf {it == 0}?.run{
            throw DataUpdateFailedException(tag)
        }
    }

    protected fun <E> ArrayList<E>.addAll(vararg value:E) {
        value.forEach {this.add(it)}
    }
}