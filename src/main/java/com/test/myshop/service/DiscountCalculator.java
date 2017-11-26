package com.test.myshop.service;

import com.test.myshop.dao.DiscountDao;

public class DiscountCalculator {


    public static boolean debug;

    /**Calculate the Discounted price based on the brandname, category and proice
     *
     *
     * @param brandName
     * @param category
     * @param price
     * @return
     */
    public static Double calculateDiscountedPrice(String brandName, String category, Float price){

        float highestDiscount = calculateHighestDiscount(brandName, category);
        log("highestDiscount"+highestDiscount);
        log("price"+price);
        float deduct = price*(highestDiscount/100);

        log("deduct"+deduct);
        double discountedPrice=price-Math.round(deduct) ;
        log("discountedPrice"+discountedPrice);
        return discountedPrice;

    }

    /**
     * Calculated  the Highest Discount based on brandName and Category
     *
     * @param brandName
     * @param category
     * @return
     */
    private static Float calculateHighestDiscount(String brandName,String category){

        Float categoryDiscount = DiscountDao.getCategoryDiscount(category).floatValue();
        Float brandsDiscount = DiscountDao.getBrandsDiscount(brandName).floatValue();

        String parent = DiscountDao.getParent(category);
        log("parent"+parent);
        Float parentCategoryDiscount = 0.0f;
        if(parent  != null && !parent.equals("none") && !parent.equals("")){

             parentCategoryDiscount = DiscountDao.getCategoryDiscount(parent).floatValue();
        }

        if(brandsDiscount >= categoryDiscount && brandsDiscount >= parentCategoryDiscount){

            return  brandsDiscount;
        }else if(categoryDiscount >= brandsDiscount && categoryDiscount >= parentCategoryDiscount ){
            return categoryDiscount;
        }else
            return parentCategoryDiscount;
    }
    
    private static void log(String printString){

        if(debug){
            System.out.println(printString);
        }

        
    }

    }
