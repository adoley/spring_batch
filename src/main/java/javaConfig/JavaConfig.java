package javaConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
//BatchConfigurer https://stackoverflow.com/questions/25540502/use-of-multiple-datasources-in-spring-batch
public class JavaConfig implements BatchConfigurer {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected Tasklet tasklet() {
        System.out.println("Inside tasklet");
        return new Tasklet() {
            public RepeatStatus execute(StepContribution contribution, ChunkContext context) {
                System.out.println("inside tasklet");
                return RepeatStatus.FINISHED;
            }
        };
    }
    @Bean
    public Step stepOne() {
        System.out.println("Inside step");
        return this.steps.get("stepOne")
                //.tasklet(tasklet())
                .tasklet(new JavaConfigTasklet())
                .build();
    }

    @Bean
    public Job job() {
        System.out.println("Inside job");
        return this.jobs.get("demoJob")
                .start(stepOne())
                .build();
    }

    @Bean
    public JobLauncher jobLauncher() {
        System.out.println("Inside jobLauncher");
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        return jobLauncher;
    }

    @Bean
    public JobRepository getJobRepository()  {
        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean(getTransactionManager());
        try {
            System.out.println("Inside getJobRepository");
            JobRepository jobRepository = factoryBean.getObject();
            return jobRepository;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        System.out.println("Inside getTransactionManager");
        return new ResourcelessTransactionManager();
    }

    public JobLauncher getJobLauncher()  {
        return null;
    }

    public JobExplorer getJobExplorer()  {
        return null;
    }

}
//https://www.baeldung.com/introduction-to-spring-batch