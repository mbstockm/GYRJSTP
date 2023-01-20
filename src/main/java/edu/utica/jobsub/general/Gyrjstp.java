package edu.utica.jobsub.general;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sct.messaging.bif.BatchProcessorException;
import com.sct.messaging.bif.BatchResourceHolder;
import com.sct.messaging.bif.banner.BannerBatchProcessor;
import edu.utica.jobsub.general.csv.CsvService;
import edu.utica.jobsub.general.model.Term;
import edu.utica.jobsub.general.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Gyrjstp extends BannerBatchProcessor implements CommandLineRunner {

    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private TermService termService;

    @Autowired
    private CsvService csvService;

    @Value("${csv.output.path}")
    private String csvPath;

    @Override
    public void processJob() {
        SpringApplication.run(Gyrjstp.class,new String[]{getJobName(),getJobNumber()});
    }
    @Override
    public void run(String... args) throws BatchProcessorException {

        Path csv = Paths.get(csvPath,"gyrjstp.csv");

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
            throw new BatchProcessorException("Error with CSV generation.",csvEx);
        }

    }

}
