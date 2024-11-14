package com.nqt.cs3.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.nqt.cs3.component.EmailItemProcess;
import com.nqt.cs3.component.EmailItemReader;
import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.EmailReaderItemDTO;
import com.nqt.cs3.service.CourseService;
import com.nqt.cs3.service.EnrollmentService;
import com.nqt.cs3.service.MailService;
import com.nqt.cs3.service.StudentService;


@Configuration
@EnableScheduling
public class SpringJobEmailConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MailService emailService;

    @Autowired
    private StudentService studentService;

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
            EmailItemReader readerEmail, EmailItemProcess processorEmail, 
            ItemWriter<EmailReaderItemDTO> writerEmail) {
        System.out.println("stepSendMail");
        return new StepBuilder("stepSendMail", jobRepository)
                .<EmailReaderItemDTO, EmailReaderItemDTO>chunk(1, transactionManager)
                .reader(readerEmail)
                .processor(processorEmail)
                .writer(writerEmail)
                .build();
    }

    @Bean
    @JobScope
    public EmailItemReader readerEmail(EnrollmentService enrollmentService, CourseService courseService,  StudentService studentService) {
        System.out.println("reader");
        return new EmailItemReader(enrollmentService, courseService, studentService);
    }

    @Bean
    @JobScope
    public EmailItemProcess processorEmail() {
        System.out.println("processor");
        return new EmailItemProcess();
    }

    @Bean
    @JobScope
    public ItemWriter<EmailReaderItemDTO> writerSendMail() {
        return items -> {
            String subject = "Thông báo khóa học chưa đăng ký";
            for (EmailReaderItemDTO item : items) {
                System.out.println("Sending email to: " + item.getEmail());
                Student student = studentService.findByEmail(item.getEmail());
                emailService.sendMessageUsingThymeleafTemplate(item.getEmail(), subject, student.getFullName(), item.getCourses());
            }
            System.out.println("Complete".toUpperCase());
        };
    }
}
