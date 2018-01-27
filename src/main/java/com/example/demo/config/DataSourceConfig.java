package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.example.demo.base.ModuloDatabaseShardingAlgorithm;
import com.example.demo.base.ModuloTableShardingAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shidong.wu on 2018/1/27.
 * 分表分库的配置
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        return buildDataSource();
    }

    @Bean
    public IdGenerator getIdGenerator() {
        return new CommonSelfIdGenerator();
    }


    private DataSource buildDataSource() {
        //设置分库映射
        Map<String ,DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库ds_0,ds_1到map里
        dataSourceMap.put("ds_0", createDataSource("ds_0"));
        dataSourceMap.put("ds_1", createDataSource("ds_1"));

        //设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，
        // 只有一个库时不需要指定默认库，
        // 但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap,"ds_0");


        //设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
        //0和1两个表是真实的表，t_order是个虚拟不存在的表，
        // 只是供使用。如查询所有数据就是select * from t_order就能查完0和1表的
        TableRule orderTableRule = TableRule.builder("t_order")
                                            .actualTables(Arrays.asList("t_order_0","t_order_1"))
                                            .dataSourceRule(dataSourceRule)
                                            .build();

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                                                .dataSourceRule(dataSourceRule)
                                                .tableRules(Arrays.asList(orderTableRule))
                                                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id",new ModuloDatabaseShardingAlgorithm()))
                                                .tableShardingStrategy(new TableShardingStrategy("order_id",new ModuloTableShardingAlgorithm())).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }


    /**
     * 创建druid数据源
     * @param dataSourceName
     * @return
     */
    private static DataSource createDataSource(final String dataSourceName) {
        // 使用Druid连接池连接数据库
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }


}
