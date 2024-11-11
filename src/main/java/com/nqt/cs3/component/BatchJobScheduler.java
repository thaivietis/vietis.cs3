package com.nqt.cs3.component;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    public BatchJobScheduler(JobLauncher jobLauncher, Job importUserJob) {
        this.jobLauncher = jobLauncher;
        this.importUserJob = importUserJob;
    }

    @Scheduled(cron = "0/10 * * * * *") 
    public void runBatchJob() throws JobExecutionException, NoSuchJobException {
        jobLauncher.run(importUserJob, new JobParameters());
    }
}
