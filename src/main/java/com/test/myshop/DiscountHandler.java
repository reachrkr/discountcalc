package com.test.myshop;

import com.test.myshop.service.DiscountCalculator;
import com.test.myshop.service.DiscountMetadataReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import java.io.*;

import java.util.*;

/**
 *  This is the main class which handles
 *  1) Adding Discount metadata combinations for Brand and categories
 *  2) Calculating the Discounted price for the selected shopping inventory
 *
 */
public class DiscountHandler {
    public static void main(final String[] arguments)
    {

        Scanner scanner = new Scanner(System.in);

        while (true) {


            System.out.println("If you want load Discount Metadata please type \"load\"");
            System.out.println("If you want to Calculate Discount please type \"calc\"");
            System.out.println("If at anytime you want to leave, type \"quit\"");
            System.out.println("ENTER YOU SELECTION :- ");


            String input = scanner.nextLine();

            if ("quit".equals(input)) {
                System.out.println("Exit!");
                break;
            }

            if ("load".equals(input)){
                System.out.print("Enter the full filepath location of the Brands discount CSV file :-  ");
                String filepath1 = scanner.nextLine();
                filepath1=filepath1.trim();
                if (!isFileExists(filepath1)){
                    System.out.println(" Exiting ... File "+filepath1+"  Not found ");
                    continue;
                }

                System.out.print("Enter the full filepath location of the category discount CSV file :- ");
                String filepath2 = scanner.nextLine();
                filepath2=filepath2.trim();
                if (!isFileExists(filepath2)){
                    continue;
                }

                try {
                    DiscountMetadataReader.debug=false;
                    DiscountMetadataReader.loadContentOfBrandsDiscountInCache(new DiscountHandler().loadResourceAsFile(filepath1));
                    DiscountMetadataReader.loadCategoriesDiscInCache(new DiscountHandler().loadResourceAsFile(filepath2));
                    DiscountMetadataReader.loadRelationsInCache(new DiscountHandler().loadResourceAsFile(filepath2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.printf(" ----------------------------------- %n ");
                System.out.println(" Discount Metadata Loaded Successfully ");
                System.out.printf(" ----------------------------------- %n ");
            }

            if("calc".equals(input)){

                 new DiscountHandler().loadDefaultDiscountMetadata();

                System.out.print("Enter the full filepath location of Shop Inventory CSV file :- ");
                String shopInvfilepath = scanner.nextLine();
                shopInvfilepath=shopInvfilepath.trim();
                if (!isFileExists(shopInvfilepath)){
                    System.out.println(" Exiting ... File "+shopInvfilepath+"  Not found ");
                    continue;
                }

                try {
                    displayInventory(shopInvfilepath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println ("Select the no of Brand combinations choices to  select  ");
                System.out.print("Please enter the no of choices :- ");
                String choices = scanner.nextLine();
                do {
                        System.out.println ("Select the no of Brand combinations choices to  select  ");
                        System.out.print("Please enter a POSITIVE no of choices :- ");
                        choices = scanner.nextLine();
                        continue;
                }while(!checkforPostiveNumbers(choices));
                System.out.println("Please select a COMMA SEPARATED ID from the SHOP INVENTORY above for each CHOICE : ex 1,3  ");
                List<Double> resultList  = new ArrayList<Double>();
                for (int i = 0; i < Integer.valueOf(choices).intValue() ; i++) {
                    System.out.print("Please select a comma seperated ID from the List above for choice No "+i+1+"  :-  ");
                    String inputData= scanner.nextLine();
                    try {
                         double total =  calculateTotal(inputData,shopInvfilepath);
                         resultList.add(total);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(" --------------------------------------------------------------------------------------");
                System.out.println(" Total Cost of the selected choices after Discount is :- "+resultList);
                System.out.println(" --------------------------------------------------------------------------------------");
            }

        }

        scanner.close();

    }

    /**
     * Loads the default Discount Metadata if no metadata is given
     *
     */
    private  void loadDefaultDiscountMetadata() {

        DiscountMetadataReader.debug=false;
        try {
            DiscountMetadataReader.loadContentOfBrandsDiscountInCache(loadResource("/brands_discount.csv"));
            DiscountMetadataReader.loadCategoriesDiscInCache(loadResource("/categories_discount.csv"));
            DiscountMetadataReader.loadRelationsInCache(loadResource("/categories_discount.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate the Total Price after applying the discount
     *
     *
     * @param inputData
     * @param shopInvfilepath
     * @return
     * @throws IOException
     */

    private static double calculateTotal(String inputData,String shopInvfilepath) throws IOException {

        double total = 0.0;
        String[] id = inputData.split(",");
        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        //initialize the CSVParser object
        CSVParser parser = new CSVParser(new FileReader(shopInvfilepath), format);
        DiscountCalculator.debug=false;

        for (int i = 0; i < id.length ; i++) {
            for(CSVRecord record : parser){
                 if(Integer.valueOf(id[i]) == Integer.valueOf(record.get("ID"))){
                     //System.out.println(record.get("BRAND"));
                     // System.out.println(record.get("PRICE"));
                     double discountedPrice = DiscountCalculator.calculateDiscountedPrice(record.get("BRAND"), record.get("CATEGORY"), Float.valueOf(record.get("PRICE")));
                     total=total+discountedPrice;
                     break;
                 }
            }

        }
        return total;
    }

    /**
     * Display the Inventory Item Read from the CSV file
     *
     * @param filePath
     * @throws IOException
     */
    private static void displayInventory(String filePath) throws IOException{

        //Create the CSVFormat object
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        //initialize the CSVParser object
        CSVParser parser = new CSVParser(new FileReader(filePath), format);
        System.out.println("SHOP INVENTORY");
        System.out.println("-------------------------------------");
        for(CSVRecord record : parser){
            System.out.println(record.toMap());
        }
        System.out.println("-------------------------------------");
    }


    /**
     * Check for postive numbers only
     *
     * @param input
     * @return
     */
    private  static boolean checkforPostiveNumbers(String input){

        if(input.matches("^[1-9]\\d*$")){
            return true;
        }
        return false;

    }

    /**
     * check if file exists and is a file
     *
     * @param filePath
     * @return
     */
    private static boolean isFileExists(String filePath){

        boolean exists=false;
        File file = new File(filePath);

        if (file.exists() && file.isFile())
        {
            exists=true;
        }
        return exists;
    }


    private InputStreamReader loadResource(String filepath) throws UnsupportedEncodingException {

        return   new InputStreamReader( getClass().getResourceAsStream(filepath));

    }

    private FileReader loadResourceAsFile(String filepath) throws  FileNotFoundException {

        return   new FileReader( filepath);

    }


}
