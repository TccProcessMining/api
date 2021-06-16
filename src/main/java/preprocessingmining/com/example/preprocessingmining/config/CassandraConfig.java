package preprocessingmining.com.example.preprocessingmining.config;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.time.Duration;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.example.model.cassandra"})
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "db_api";
    }

    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
        return new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
                return cqlSessionBuilder
                        .withConfigLoader(DriverConfigLoader
                                .programmaticBuilder()
                                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000)).build());
            }
        };
    }
}