package com.nqt.cs3.config;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nqt.cs3.component.EnrollmentItemReader;
import com.nqt.cs3.dto.ReportRegisterCourseDTO;
import com.nqt.cs3.service.EnrollmentService;
import com.nqt.cs3.service.ReportRegisterCourseService;

@Configuration
@EnableScheduling
public class SpringBatchConfig {

	@Bean
	public Job importUserJob(JobRepository jobRepository, Step step) {
		return new JobBuilder("importUserJob", jobRepository)
				.start(step)
				.incrementer(new RunIdIncrementer())
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, JpaTransactionManager transactionManager,
			EnrollmentItemReader reader, ReportRegisterCourseService processor,
			FlatFileItemWriter<ReportRegisterCourseDTO> writer) {
		return new StepBuilder("step", jobRepository)
				.<ReportRegisterCourseDTO, ReportRegisterCourseDTO>chunk(10, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	public EnrollmentItemReader reader(EnrollmentService enrollmentService) {
		return new EnrollmentItemReader(enrollmentService);
	}

	@Bean
	public ReportRegisterCourseService processor() {
		return new ReportRegisterCourseService();
	}

	@Bean
	public FlatFileItemWriter<ReportRegisterCourseDTO> writer() {
		DelimitedLineAggregator<ReportRegisterCourseDTO> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		FlatFileHeaderCallback headerCallback = writer -> writer.write("Course Name; Student Registered");
		BeanWrapperFieldExtractor<ReportRegisterCourseDTO> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "nameCourse", "studentRegistered" });
		aggregator.setFieldExtractor(fieldExtractor);
		return new FlatFileItemWriterBuilder<ReportRegisterCourseDTO>()
				.name("personItemReader")
				.resource(new FileSystemResource("report_register_course_" + Instant.now().getEpochSecond() + ".csv"))
				.lineAggregator(aggregator)
				.headerCallback(headerCallback)
				.append(false)
				.build();
	}
}
