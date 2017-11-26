package com.test.myshop.service;

import com.test.myshop.dao.DiscountDao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

/**
 * Service class which Loads the Discount metadata CSV content to Cache
 *
 */
public class DiscountMetadataReader {

    public static boolean debug;




    public static void  loadContentOfBrandsDiscountInCache(Reader in) throws IOException {

        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        //initialize the CSVParser object

        CSVParser parser = new CSVParser(in, format);

        for(CSVRecord record : parser){

            log(record.get("brandName"));
            log(""+Float.valueOf(record.get("discount").toString()));
            DiscountDao.addBrandsDiscount(record.get("brandName"),Float.valueOf(record.get("discount").toString()));
        }
        //close the parser
        parser.close();

    }


    public static void  loadCategoriesDiscInCache(Reader in) throws IOException {

        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        //initialize the CSVParser object
        CSVParser parser = new CSVParser( in, format);

        for(CSVRecord record : parser){

            log(record.get("category"));
            log(""+Float.valueOf(record.get("discount").toString()));
            DiscountDao.addCategoryDiscount(record.get("category"),Float.valueOf(record.get("discount").toString()));
        }
        //close the parser
        parser.close();


    }

    public static void  loadRelationsInCache(Reader  in) throws IOException {

        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        //initialize the CSVParser object
        CSVParser parser = new CSVParser(in , format);

        for(CSVRecord record : parser){

            log(record.get("category"));
            log(record.get("parent_category"));
            DiscountDao.addRelation(record.get("category"),record.get("parent_category"));
        }
        //close the parser
        parser.close();

    }
    private static void log(String printString){

        if(debug){
            System.out.println(printString);
        }


    }


}
