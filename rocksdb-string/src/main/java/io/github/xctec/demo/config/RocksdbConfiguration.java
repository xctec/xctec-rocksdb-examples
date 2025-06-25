package io.github.xctec.demo.config;

import io.github.xctec.rocksdb.builder.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocksdbConfiguration {
    @Bean
    public DBOptionsConfigurer dbOptionsConfigurer() {
        return dbOptions -> {
            // 设置其他的配置
            dbOptions.setCreateIfMissing(true)
                    .setCreateMissingColumnFamilies(true);
        };
    }

    @Bean
    public ColumnFamilyConfigurer defaultColumnFamilyOptionsConfigurer() {
        ColumnFamilyOptionsConfigurer configurer = ColumnFamilyOptions -> {

        };
        ColumnFamilyOperationsConfigurer operationsConfigurer = columnFamilyOptions -> {

        };
        return new DefaultColumnFamilyConfigurer(configurer, operationsConfigurer);
    }


    @Bean
    public ColumnFamilyConfigurer cf1ColumnFamilyOptionsConfigurer() {
        ColumnFamilyOptionsConfigurer configurer = ColumnFamilyOptions -> {

        };
        ColumnFamilyOperationsConfigurer operationsConfigurer = columnFamilyOptions -> {

        };
        return new ColumnFamilyConfigurer("cf1", configurer, operationsConfigurer);
    }
}
