package com.nqt.cs3.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.dto.ReportRegisterCourseDTO;
import com.nqt.cs3.repository.EnrollmentRepository;
import com.nqt.cs3.service.ReportRegisterCourseService;

@Configuration
public class SpringBatchConfig {

	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	@Bean
	public Job importUserJob(JobRepository jobRepository, Step step1) {
		return new JobBuilder("importUserJob", jobRepository)
			.start(step1)
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, JpaTransactionManager transactionManager,
					RepositoryItemReader<Enrollment> reader, ReportRegisterCourseService processor,
					FlatFileItemWriter<ReportRegisterCourseDTO> writer) {
		return new StepBuilder("step1", jobRepository)
			.<Enrollment, ReportRegisterCourseDTO> chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean
	public RepositoryItemReader<Enrollment> myReader() {
		return new RepositoryItemReaderBuilder<Enrollment>()
				.repository(enrollmentRepository)
				.methodName("findAll")
				.saveState(false)
				.build();
	}

    @Bean
	public ReportRegisterCourseService processor() {
		return new ReportRegisterCourseService();
	}

	@Bean
	public FlatFileItemWriter<ReportRegisterCourseDTO> writer() {
		// Định nghĩa cách thức chuyển đổi đối tượng thành các dòng CSV
		DelimitedLineAggregator<ReportRegisterCourseDTO> aggregator = new DelimitedLineAggregator<>();
    
		// Cấu hình FieldExtractor để ánh xạ các trường trong DTO thành các cột trong CSV
		BeanWrapperFieldExtractor<ReportRegisterCourseDTO> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[]{"courseId", "studentName", "registrationDate"});  // Tên các trường trong DTO
		aggregator.setFieldExtractor(fieldExtractor);
		return new FlatFileItemWriterBuilder<ReportRegisterCourseDTO>()
			.name("personItemReader")
			.resource(new FileSystemResource("report_register_course.csv"))
			.lineAggregator(aggregator)
			.append(false)
			.build();
	}
}
