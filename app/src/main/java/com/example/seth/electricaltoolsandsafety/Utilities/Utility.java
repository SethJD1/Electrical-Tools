package com.example.seth.electricaltoolsandsafety.Utilities;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.support.annotation.ColorRes;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Class of various utility functions.
 */
public class Utility {

    /**
     * Tints a menu icon to a specific color.
     *
     * @param context of the menu
     * @param item to change the color on
     * @param color to change the icon to
     */
    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {

        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }

    /**
     * Checks if a string can be converted to a double value
     * @param numberValue - the string to convert
     * @return - true if the String is a double and false otherwise
     */
    public static boolean isDouble(String numberValue){

        try{
            Double.parseDouble(numberValue);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Converts a String to a double. Returns zero if the the string is not a double value.
     * @param numberValue - the string to convert
     * @return - the value of the double or -1 if the string is not a double.
     */
    public static Double convertStringToDouble(String numberValue){

        if(isDouble(numberValue)){
            return  Double.parseDouble(numberValue);
        } else {
            return Double.valueOf(-1);
        }

    }

    /**
     * Checks if a string can be converted to a int value
     * @param numberValue - the string to convert
     * @return - true if the String is a int and false otherwise
     */
    public static boolean isInt(String numberValue){

        try{
            Integer.parseInt(numberValue);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Converts a String to a int. Returns zero if the the string is not an int value.
     * @param numberValue - the string to convert
     * @return - the value of the int or zero if the string is not an int.
     */
    public static int convertStringToInt(String numberValue){

        if(isInt(numberValue)){
            return  Integer.parseInt(numberValue);
        } else {
            return Integer.valueOf(-1);
        }

    }

    /**
     * Returns the current date and time in a formal format.
     * @return current date and time.
     */
    public static String getCurrentDate(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }
}
