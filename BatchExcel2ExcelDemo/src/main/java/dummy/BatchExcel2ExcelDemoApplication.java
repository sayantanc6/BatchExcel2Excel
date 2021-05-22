package dummy;

import java.io.IOException;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchExcel2ExcelDemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BatchExcel2ExcelDemoApplication.class, args); 
	}
}
