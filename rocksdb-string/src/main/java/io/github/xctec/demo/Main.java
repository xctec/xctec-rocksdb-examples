package io.github.xctec.demo;


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
            s.put("abc", "abc");
            s.get("abc");
            System.out.println(s.get("abc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
