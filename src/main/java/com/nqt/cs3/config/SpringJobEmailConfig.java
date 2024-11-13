package com.nqt.cs3.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.nqt.cs3.component.EmailItemProcess;
import com.nqt.cs3.component.EmailItemReader;
import com.nqt.cs3.component.ReportItemProcess;
import com.nqt.cs3.component.ReportItemReader;
import com.nqt.cs3.dto.EmailReaderItemDTO;
import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportDTO;
import com.nqt.cs3.service.EnrollmentService;

@Configuration
@EnableScheduling
public class SpringJobEmailConfig {

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job sendMailJob(Step stepSendMail) {
        System.out.println("Job");
        return new JobBuilder("sendMailJob", jobRepository)
                .start(stepSendMail)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step stepSendMail(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            EmailItemReader reader, EmailItemProcess processor) {
        System.out.println("stepSendMail");
        return new StepBuilder("stepSendMail", jobRepository)
                .<EmailReaderItemDTO, Void>chunk(4, transactionManager)
                .reader(reader)
                .processor(processor)
                .build();
    }

    @Bean
    @StepScope
    public EmailItemReader reader(EnrollmentService enrollmentService) {
        System.out.println("reader");
        return new EmailItemReader(enrollmentService);
    }

    @Bean
    @StepScope
    public EmailItemProcess processor() {
        System.out.println("processor");
        return new EmailItemProcess();
    }
}
