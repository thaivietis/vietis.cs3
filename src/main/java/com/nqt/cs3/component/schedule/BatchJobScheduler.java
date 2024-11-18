package com.nqt.cs3.component.schedule;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    @Qualifier("reportJob")
    private final Job reportJob;
    @Qualifier("sendMailJob")
    private final Job sendMailJob;

    public BatchJobScheduler(JobLauncher jobLauncher, @Qualifier("reportJob") Job reportJob,
            @Qualifier("sendMailJob") Job sendMailJob) {
        this.jobLauncher = jobLauncher;
        this.reportJob = reportJob;
        this.sendMailJob = sendMailJob;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void runBatchReportJob() throws JobExecutionException, NoSuchJobException {
        JobParameters reportJobParameters = new JobParametersBuilder()
                .addString("uniqueKeyReportJob", UUID.randomUUID().toString())
                .toJobParameters();
        jobLauncher.run(reportJob, reportJobParameters);
        JobParameters sendMailJobParameters = new JobParametersBuilder()
                .addString("uniqueKeySendMailJob", UUID.randomUUID().toString())
                .toJobParameters();
        jobLauncher.run(sendMailJob, sendMailJobParameters);
    }
}
