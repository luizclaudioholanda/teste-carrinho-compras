package br.com.carrinho;

import org.junit.runner.RunWith;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = "br.com.carrinho.repository")
@EntityScan(basePackages = "br.com.carrinho.entity")
@EnableTransactionManagement
@PropertySource("classpath:application-dev.properties")
public abstract class AbstractRepositoryTest {

}
