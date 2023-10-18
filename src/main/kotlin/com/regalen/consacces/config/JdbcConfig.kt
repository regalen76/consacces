package com.regalen.consacces.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
@ComponentScan("com.regalen.consacces")
class JdbcConfig {
    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun jdbcTemplate(dataSource: DataSource?): JdbcTemplate {
        val jdbcTemplate = JdbcTemplate(this.dataSource)
        jdbcTemplate.isResultsMapCaseInsensitive = true
        return jdbcTemplate
    }
}