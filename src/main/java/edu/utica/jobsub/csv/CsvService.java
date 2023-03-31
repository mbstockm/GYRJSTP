package edu.utica.jobsub.csv;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvService {

    public <T> void createCsv(Path csv, List<T> list, Class<T> classType, Resource headerTemplate) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try (Writer writer = Files.newBufferedWriter(csv)) {
            StatefulBeanToCsv beanToCsv =
                    new StatefulBeanToCsvBuilder(writer)
                            .withMappingStrategy(headerTemplateStrategy(classType,headerTemplate))
                            .build();
            beanToCsv.write(list);
        }
    }

    public HeaderColumnNameMappingStrategy headerTemplateStrategy(Class classType, Resource headerTemplate) throws IOException {
        HeaderColumnNameMappingStrategy strategy =
                new HeaderColumnNameMappingStrategyBuilder().build();
        strategy.setType(classType);

        try (Reader reader = new BufferedReader(new InputStreamReader(headerTemplate.getInputStream()))) {
            CsvToBean csvToBean =
                    new CsvToBeanBuilder(reader)
                            .withType(classType)
                            .withMappingStrategy(strategy)
                            .build();
            csvToBean.parse();
        }
        return strategy;
    }

}
