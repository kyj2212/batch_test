package com.ll.exam.batch_test.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloWorldJob() { // Job 빈의 이름은 반드시 프로젝트내에서 uniq 해야한다.
        return jobBuilderFactory.get("helloWorldJob")
                .incrementer(new RunIdIncrementer())
                .start(helloWorldStep1())
                .build();
    }

    @Bean
    @JobScope // Job에 속한다는 스프링 생명주기의 범위를 지정하는 것으로 반드시 지정하자
    public Step helloWorldStep1(){
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldTasklet1())
                .build();
    }

    @Bean
    @StepScope // Step에 속한다는 스프링 생명주기의 범위를 지정하는 것으로 반드시 지정하자
    public Tasklet helloWorldTasklet1() {
        return (contribution, chunkContext) -> {
            System.out.println("[helloWorldJob] tasklet1 start: hello world ");
            return RepeatStatus.FINISHED;
        };
    }
}
