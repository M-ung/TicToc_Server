package tictoc.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import tictoc.config.database.type.DataSourceType;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Import({MasterDataSourceConfig.class, SlaveDataSourceConfig.class})
public class RoutingDataSourceConfig {

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
            @Qualifier("masterDataSource") final DataSource masterDataSource,
            @Qualifier("slaveDataSource") final DataSource slaveDataSource
    ) {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(DataSourceType.Master, masterDataSource);
        dataSourceMap.put(DataSourceType.Slave, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}