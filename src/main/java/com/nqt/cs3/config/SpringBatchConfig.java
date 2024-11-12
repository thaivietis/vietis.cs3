package com.nqt.cs3.config;

import java.time.Instant;
import java.time.LocalDate;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nqt.cs3.component.EnrollmentItemReader;
import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportWeekDTO;
import com.nqt.cs3.service.EnrollmentService;
import com.nqt.cs3.service.ReportRegisterCourseService;

@Configuration
@EnableScheduling
public class SpringBatchConfig {

	@Autowired
	private JobRepository jobRepository;

	@Bean
	public Job importUserJob(Step step1) {
		return new JobBuilder("importUserJob", jobRepository)
				.start(step1)
				.incrementer(new RunIdIncrementer())
				.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, JpaTransactionManager transactionManager,
			EnrollmentItemReader reader, ReportRegisterCourseService processor,
			FlatFileItemWriter<ReportWeekDTO> writer) {
		return new StepBuilder("step1", jobRepository)
				.<ReaderItemDTO, ReportWeekDTO>chunk(10, transactionManager)
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
	public FlatFileItemWriter<ReportWeekDTO> writer() {
		DelimitedLineAggregator<ReportWeekDTO> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		FlatFileHeaderCallback headerCallback = writer -> writer.write("Course Name; Student Registered; Date Start; Date End; Date Report;");
		BeanWrapperFieldExtractor<ReportWeekDTO> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "nameCourse", "studentRegistered", "startDate", "endDate", "reportDate" });
		aggregator.setFieldExtractor(fieldExtractor);
		return new FlatFileItemWriterBuilder<ReportWeekDTO>()
				.name("personItemReader")
				.resource(new FileSystemResource("store/report_register_course_" + LocalDateTime.now().getSecond() + ".csv"))
				.lineAggregator(aggregator)
				.headerCallback(headerCallback)
				.append(false)
				.build();
	}
}
