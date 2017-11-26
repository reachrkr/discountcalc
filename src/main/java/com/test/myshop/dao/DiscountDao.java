package com.test.myshop.dao;

/**
 * This is the DAO class which used EHCache as the Data Layer to store the contents of the Discount metadata .
 *
 *
 */

public class DiscountDao {




    public static void addBrandsDiscount(String brandName, Float discountPercent){

        if(DiscountCacheHelper.getInstance().getBrandsDiscountCache().containsKey(brandName.trim().toLowerCase())){
            return;
        }

        DiscountCacheHelper.getInstance().getBrandsDiscountCache().put(brandName.trim().toLowerCase(),discountPercent);

    }

    public  static Float getBrandsDiscount(String brandName){
        return DiscountCacheHelper.getInstance().getBrandsDiscountCache().get(brandName.trim().toLowerCase());
    }

    public static void addCategoryDiscount(String category, Float discountPercent){

        if(DiscountCacheHelper.getInstance().getBrandsDiscountCache().containsKey(category.trim().toLowerCase())){
            return;
        }

        DiscountCacheHelper.getInstance().getCategoryDiscountCache().put(category.trim().toLowerCase(),discountPercent);
    }

    public  static Float getCategoryDiscount(String category){
        return DiscountCacheHelper.getInstance().getCategoryDiscountCache().get(category.trim().toLowerCase());
    }

    public static void addRelation(String childCategory , String  parentCategory){

        if(DiscountCacheHelper.getInstance().getRelationCache().containsKey(childCategory.trim().toLowerCase())){
            return;
        }
        DiscountCacheHelper.getInstance().getRelationCache().put(childCategory.trim().toLowerCase(),parentCategory);

    }

    public  static String getParent(String childCategory){
        return DiscountCacheHelper.getInstance().getRelationCache().get(childCategory.trim().toLowerCase());
    }

}
