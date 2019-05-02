package javaConfig;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class JavaConfigTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        System.out.println("Inside JavaConfigTasklet tasklet");
        return RepeatStatus.FINISHED;
    }
}
