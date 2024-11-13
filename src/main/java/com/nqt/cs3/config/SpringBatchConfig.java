package com.nqt.cs3.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
import org.springframework.transaction.PlatformTransactionManager;

import com.nqt.cs3.component.EnrollmentItemReader;
import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportDTO;
import com.nqt.cs3.service.EnrollmentService;
import com.nqt.cs3.service.ReportRegisterCourseService;
import com.nqt.cs3.service.ReportService;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableScheduling
public class SpringBatchConfig {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
    private ReportService reportService;
	
	@Bean
	public Job importUserJob(Step step1) {
		System.out.println("Job");
		return new JobBuilder("importUserJob", jobRepository)
				.start(step1)
				.incrementer(new RunIdIncrementer())
				.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			EnrollmentItemReader reader, ReportRegisterCourseService processor,
			FlatFileItemWriter<ReportDTO> writer) {
		System.out.println("step1");
		return new StepBuilder("step1", jobRepository)
				.<ReaderItemDTO, ReportDTO>chunk(4, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	@StepScope
	public EnrollmentItemReader reader(EnrollmentService enrollmentService) {
		System.out.println("reader");
		return new EnrollmentItemReader(enrollmentService);
	}

	@Bean
	@StepScope
	public ReportRegisterCourseService processor() {
		System.out.println("processor");
		return new ReportRegisterCourseService();
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<ReportDTO> writer() {
		System.out.println("writer");
		DelimitedLineAggregator<ReportDTO> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		FlatFileHeaderCallback headerCallback = writer -> writer.write("Course Name;  Student Registered; Date Start; Date End; Date Report;");
		BeanWrapperFieldExtractor<ReportDTO> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "nameCourse", "studentRegistered", "startDate", "endDate", "reportDate" });
		aggregator.setFieldExtractor(fieldExtractor);
		this.reportService.createNewReport();
		return new FlatFileItemWriterBuilder<ReportDTO>()
				.name("personItemReader")
				.resource(new FileSystemResource("store/report_register_course_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv"))
				.lineAggregator(aggregator)
				.headerCallback(headerCallback)
				.append(false)
				.build();
	}
}
