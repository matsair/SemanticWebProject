package com.matsschade.semanticquizapp.Processing;

import android.util.Log;

/**
 * Created by rober_000 on 24.11.2015.
 */
public class StringProcessing {


    public static String clean(String string) {

        Log.d("test", string);

        if (string.contains("\"")) {
            string = removeQuotation(string);
        }

        if (string.contains("^^")) {
            string = removeEnd(string);
        }

        if (string.contains("@")) {
            string = removeAt(string);
        }

        Log.d("test", string);
        return string;
    }

    public static String convertToUSD(String string) {
        Log.d("test", string);
        String currency = "undefined";
        double value = 0;

        //first check for currency
        if (string.contains("US$") || string.toLowerCase().contains("/usdollar") || string.contains("US $")) {
            currency = "dollar";
        } else if (string.contains("€") || string.toLowerCase().contains("/euro")) {
            currency = "euro";
        }

        //remove unnecessary string attachments
        string = clean(string);

        try {
            //if String has the form "23.0E10", we can use the value
            value = Double.valueOf(string).longValue();
        } catch (Exception e) {
//
//            if (
//                string.contains("million")){ milBil = "million";
//                string = string.replace("million", "");
//            }
//            else if (string.contains ("billion")) { milBil = "billion";
//                string = string.replace("billion", "");
//            }
//
//                Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");
//                Matcher m = p.matcher(string);
//
//                while (m.find()) {
//                    value = Double.parseDouble(m.group());
//                }
//
        }

        if (value == 0 || currency == "undefined") {
            value = 0;
        } else if (currency == "dollar") {
            // do nothing and keep the value
        } else {
            value = value * 1.06;
        }

        Log.d("test", Double.toString(value));
        return Double.toString(value);
    }

    private static String removeQuotation(String quoted) {
        String unquoted;
        unquoted = quoted.replace("\"", "");

        return unquoted;
    }

    private static String removeEnd(String withEnd) {
        String withoutEnd;
        withoutEnd = withEnd.split("\\^")[0];

        return withoutEnd;
    }

    private static String removeAt(String withAt) {
        String withoutAt;
        withoutAt = withAt.split("@")[0];

        return withoutAt;
    }

}