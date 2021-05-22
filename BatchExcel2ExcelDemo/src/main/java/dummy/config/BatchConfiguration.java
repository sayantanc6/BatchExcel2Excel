package dummy.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import dummy.MyExcelWriter;
import dummy.Student;
import dummy.StudentProcessor;
import dummy.SudentJobListener;

@Configuration 
public class BatchConfiguration {

	  @Autowired
	  private JobBuilderFactory jobBuilderFactory;
	
	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  
	  @Value("${file.input}") 
	  private String fileInput;
	 
	  @Bean
	  public Job XmlToXmlJob() {
	    return jobBuilderFactory.get("XmlToXmlJob")
	    		.incrementer(new RunIdIncrementer()).listener(listener())
	    		.flow(step1())
	    		.end().build();
	  }
	 
	  @Bean
	  public Step step1() {
	    return stepBuilderFactory.get("step1").<Student, Student>chunk(10)
	    		.reader(excelreader())
	    		.processor(processor()).writer(new MyExcelWriter()).build();
	  }

	@Bean
	  public StudentProcessor processor() {
	    return new StudentProcessor();
	  }
	   
	  @Bean 
	  public ItemReader<Student> excelreader() {
		  PoiItemReader<Student> reader = new PoiItemReader<>();
	        reader.setLinesToSkip(1);
	        reader.setResource(new ClassPathResource(fileInput)); 
	        reader.setRowMapper(new BeanWrapperRowMapper<>() {{
	        	setTargetType(Student.class);
	        }});
	        return reader;
	  }
	 
	@Bean
	public JobExecutionListener listener() {
		return new SudentJobListener();
	}
}
