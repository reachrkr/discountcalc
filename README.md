# Discount Calculator

Discount Calculator java app 


## Requirement 

Building a system that calculates the discounts on all the applicable items a customer has bought.
There are 2 use-cases which needs to catered for 
1) Shopkeeper should be able to add /update the discount schemes at any given movement
2) Calculate the Discount of the Item brought from the shops inventory

##  Design
This Design caters to both the Use cases. Below is the seq flow

![seq](https://github.com/reachrkr/discountcalc/blob/master/dicsountcalc_seq.png)

## Implementation 

From implementation standpoint, Following data points has been considered 

1) Using Maven as the build tool
2) For persistence layer , I have used in-memory cache EHCache 
3) For use case 1, i have created two csv namely , brands_discount.csv and categories_discount.csv which pertains to all the categories combination. The schema for categories_discount
is extensible enough to support multiple sub categories
4) Uploading the inventory csv also has to be done with a file name shop_inventory.csv
5) for Reading CSV  have used apache commons-csv 



## Building and Running 

The environment i have developed is using a linux environment .For Building please make sure you the follwoing pre-requisite env 

1) Maven 4.0 
2) Linux machine 
3) Github account 
4) JDK 1.8 and above 

### Building 
1) Check out the project in a temp or home folder of the linux env 
2) Navigate to ~/discountcalc folfer. 
3) run the command "mvn clean package"


### Running 
1) Navigate to ~/discountcalc/target folder.
2) Run the following command ` java  -jar  ~/discountcalc/target/discount-calc-jar-with-dependencies.jar" `
3) Enter the selection as: 
    * "load" to load the discount schemes
    * "calc" to calculate the discounted prices
    * "quit" to quit the application 
    
4) Check the screen shot below for the "calc" sequence
5) Use the file [shop_inventory.csv](https://github.com/reachrkr/discountcalc/blob/master/shop_inventory.csv)  for adding the inventory

   ![calc](https://github.com/reachrkr/discountcalc/blob/master/dicount_calc.png)

## Open points/issues
1) Validation of choices is not workng properly
2) Validation of selecting inventory is pending
3) Unit test cases




