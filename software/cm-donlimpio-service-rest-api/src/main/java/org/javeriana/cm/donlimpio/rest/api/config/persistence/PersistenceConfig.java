package org.javeriana.cm.donlimpio.rest.api.config.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.javeriana.cm.donlimpio.rest.api.config.util.ServiceProperties;
import org.javeriana.cm.donlimpio.rest.api.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.javeriana.cm.donlimpio.rest.api.persistence.repository"})
@ComponentScan(basePackages = {"org.javeriana.cm.donlimpio.rest.api.persistence"})
@EnableTransactionManagement
public class PersistenceConfig {

    private static final String HIBERNATE_PROPERTY_FILE = "/hibernate.properties";
    private static final String HIKARI_PROPERTY_FILE = "/hikari.properties";

    @Autowired
    private ServiceProperties properties;

    @Bean
    public HikariDataSource dataSource() throws IOException {
        HikariConfig config = new HikariConfig(
                FileUtils.loadPropertyFile(PersistenceConfig.HIKARI_PROPERTY_FILE));
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(properties.getDonlimpioDbPackagesToScan());
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPersistenceUnitName(properties.getDonlimpioDbPersistenceUnitName());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(
                FileUtils.loadPropertyFile(PersistenceConfig.HIBERNATE_PROPERTY_FILE));
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager() throws IOException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory((EntityManagerFactory) entityManagerFactory());
        return txManager;
    }

    //TODO create spring DAO repository
}
