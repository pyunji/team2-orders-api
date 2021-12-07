package com.mycompany.webapp.config.jta;

import javax.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JtaTransactionManagerConfig {
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
    	log.info("transactionManager() 실행");
    	
    	UserTransaction userTransaction = new UserTransactionImp();
    	userTransaction.setTransactionTimeout(10000);
        
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        
        JtaTransactionManager manager = new JtaTransactionManager(
        		userTransaction, userTransactionManager);
        return manager;
    }
}


