package com.insist.file.opencsv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020-02-17 11:09
 */
public class CsvUtil {

    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    private static final String CSV_SUFFIX = ".csv";

    public static  <T> List<T> parseFileToUserBean(InputStream stream, Class<T> clazz,Map<String,String> columnMapping) {

        Reader reader = null;
        try {
            // column mapping
            HeaderColumnNameTranslateMappingStrategy strategy = new HeaderColumnNameTranslateMappingStrategy();
            strategy.setType(clazz);
            strategy.setColumnMapping(columnMapping);

            reader = new InputStreamReader(new BOMInputStream(stream), StandardCharsets.UTF_8);
            CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader);
            List<T> objects = csvToBeanBuilder.withType(clazz).withMappingStrategy(strategy).build().parse();

            return objects;
        } catch (Exception e) {
            logger.error("parseFileToUserBean class:{} error," + clazz.getSimpleName(),e);
            throw new RuntimeException("CSV_TO_BEAN_ERROR",e);
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
                if(stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                logger.error("reader or stream close exception:",e);
            }
        }
    }


    /**
     *  write
     */
    public static <T> String writeCsv(String prefix,List<T> dataList, Class clazz,String[] columnField,String[] header,String path,boolean printError){
        Writer writer = null;
        CSVWriter csvWriter = null;
        //step 1 create local file
        String fileId = prefix + "_"
                + System.currentTimeMillis()
                + CSV_SUFFIX;
        try {
                String[] columnFields = columnField;
                String[] headers = header;
                writer = new OutputStreamWriter(new FileOutputStream(new File(path,fileId)),StandardCharsets.UTF_8);
                writer.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
                if(printError){
                    String[] columnFieldError = new String[columnField.length + 1];
                    System.arraycopy(columnField,0,columnFieldError,0,columnField.length);
                    columnFieldError[columnField.length] = "errorMsg";
                    columnFields = columnFieldError;


                    String[] headerError = new String[header.length + 1];
                    System.arraycopy(header,0,headerError,0,header.length);
                    headerError[header.length] = "errorMsg";
                    headers = headerError;
                }
                ColumnPositionMappingStrategy mapper =
                        new ColumnPositionMappingStrategy();
                mapper.setType(clazz);
                mapper.setColumnMapping(columnFields);

                csvWriter = new CSVWriter(writer);
                csvWriter.writeNext(headers,false);

                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(mapper)
                        .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .build();
                beanToCsv.write(dataList);
                csvWriter.flush();
                writer.flush();
        } catch (Exception e ) {
            logger.error("write csv error:",e);
            throw new RuntimeException("WRITE_CSV_ERROR",e);
        }  finally {
            try {
                if(csvWriter != null) {
                    csvWriter.close();
                }
                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                logger.error("csvWriter or writer close exception:",e);
            }
        }
        return fileId;
    }
}
