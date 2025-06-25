package io.github.xctec.demo.config;

import io.github.xctec.demo.pojo.Pojo1;
import io.github.xctec.demo.pojo.Pojo2;
import io.github.xctec.rocksdb.builder.*;
import io.github.xctec.rocksdb.serializer.Jackson2JsonRocksdbSerializer;
import io.github.xctec.rocksdb.serializer.RocksdbSerializer;
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
        ColumnFamilyOptionsConfigurer configurer = columnFamilyOptions -> {
            // columnFamilyOptions.setBlobFileSize(1024 * 1024 * 1204);
        };
        ColumnFamilyOperationsConfigurer operationsConfigurer = columnFamilyOptions -> {
            columnFamilyOptions.setKeySerializer(RocksdbSerializer.string());
            columnFamilyOptions.setValueSerializer(new Jackson2JsonRocksdbSerializer(Pojo1.class));
        };
        return new DefaultColumnFamilyConfigurer(configurer, operationsConfigurer);
    }


    @Bean
    public ColumnFamilyConfigurer cf1ColumnFamilyOptionsConfigurer() {
        ColumnFamilyOptionsConfigurer configurer = columnFamilyOptions -> {
            // columnFamilyOptions.setBlobFileSize(1024 * 1024 * 1204);
        };
        ColumnFamilyOperationsConfigurer operationsConfigurer = columnFamilyOptions -> {
            columnFamilyOptions.setKeySerializer(RocksdbSerializer.string());
            columnFamilyOptions.setValueSerializer(new Jackson2JsonRocksdbSerializer(Pojo2.class));
        };
        return new ColumnFamilyConfigurer("cf1", configurer, operationsConfigurer);
    }
}
