package io.github.xctec.demo;


import io.github.xctec.demo.pojo.Pojo1;
//  import io.github.xctec.demo.pojo.Pojo2;
import io.github.xctec.demo.pojo.Pojo2;
import io.github.xctec.rocksdb.core.RocksdbTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
            System.out.println(ctx.getBean(RedisProperties.class).getHost());
            RocksdbTemplate s = ctx.getBean(RocksdbTemplate.class);
            s.put("key1", new Pojo1());
            System.out.println(s.get("key1"));
            s.getColumnFamilyOperations("cf1").put("key2", new Pojo2());
            Object v = s.getColumnFamilyOperations("cf1").get("key2");
            System.out.println(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
