package javaConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigDriver {

    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        ApplicationContext context =new AnnotationConfigApplicationContext(JavaConfig.class);
        JobLauncher jobLauncher=(JobLauncher) context.getBean("jobLauncher");
        Job job=(Job) context.getBean("job");
        JobExecution execution = jobLauncher.run(job,new JobParameters());
        System.out.println("Exit Status : " + execution.getStatus());
    }

}
