package edu.utica.jobsub;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sct.messaging.bif.BatchProcessorException;
import com.sct.messaging.bif.BatchResourceHolder;
import com.sct.messaging.bif.banner.BannerBatchProcessor;
import edu.utica.jobsub.csv.CsvService;
import edu.utica.jobsub.model.Term;
import edu.utica.jobsub.service.TermService;
//import edu.utica.spring.boot.starter.jobsub.info.JobInformation;
//import edu.utica.spring.boot.starter.jobsub.job.BannerJob;
import edu.utica.spring.boot.starter.jobsub.job.BannerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Gyrjstp extends BannerBatchProcessor implements CommandLineRunner {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private BannerJob job;
    @Autowired
    private TermService termService;
    @Autowired
    private CsvService csvService;
    @Value("${csv.output.path}")
    private String csvPath;

    /**
     * processJob extended from the Ellucian BannerBatchProcessor initiates the Spring Application.
     */
    @Override
    public void processJob() {
        new SpringApplicationBuilder(Gyrjstp.class)
                .web(WebApplicationType.NONE)
                .run(getJobName(),getJobNumber());
    }

    /**
     * Run method from Spring CommandLineRunner then contains the starting point of our jobs business logic.
     * @param args incoming main method arguments
     * @throws BatchProcessorException
     */
    @Override
    public void run(String... args) throws BatchProcessorException {

//        System.out.println(job);
//        System.out.println(job.getName());
//        String oneup = job.getJobNumber();
        System.out.println(job.getName());
        System.out.println(job.getNumber());
        System.out.println(job.getUser());
        System.out.println(job.getParameters().getList());

        Path csv = Paths.get(csvPath,"GYRJSTP" + "_" + "oneup" + ".csv");

        try {
            List<Term> list = termService.getTermsForAidy((String) BatchResourceHolder.getJobParameterMap().get("01"));

            csvService.createCsv(
                    csv,
                    list,
                    Term.class,
                    resourceLoader.getResource("classpath:/csv/termHeader.csv")
            );

        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new BatchProcessorException("Error with file processing.",ioException);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException csvEx) {
            throw new BatchProcessorException("Error with CSV creation.",csvEx);
        }

    }

}
