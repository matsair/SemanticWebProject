package com.matsschade.semanticquizapp.Processing;

/**
 * Created by rober_000 on 24.11.2015.
 */
public class StringProcessing {

    private final String TAG = "STRING PROCESSING";

    public static String clean(String string, String attributeType) {

//        Log.d(TAG, string);

        if (attributeType.equals("string")  || attributeType.equals("location")) {
            string = cleanString(string);
        }
        else if (attributeType.equals("number")) {
            string = cleanString(string);
            double value = Double.valueOf(string);
            string = Double.toString(value);
        }

        else if (attributeType.equals("year")) {
            string = cleanString(string);
            string = Double.toString(Double.valueOf(string.substring(0, 4)));
        }

        else if (attributeType.equals("currency")) {
            string = cleanCurrency(string);
        }

//        Log.d(TAG, string);
        return string;
    }

    public static String cleanString (String string){

        if (string.contains("\"")) {
            string = removeQuotation(string);
        }

        if (string.contains("^^")) {
            string = removeEnd(string);
        }

        if (string.contains("@")) {
            string = removeAt(string);
        }

        if(string.contains("(")) {
            string = string.replaceAll("\\(.+\\)", "");
        }

        //Log.d(TAG, string);
        return string;
    }

    public static String cleanCurrency(String string) {
        //Log.d(TAG, string);
        String currency = "undefined";
        double value = 0;

        //first check for currency
        if (string.contains("US$") || string.toLowerCase().contains("/usdollar") || string.contains("US $")) {
            currency = "dollar";
        } else if (string.contains("€") || string.toLowerCase().contains("/euro")) {
            currency = "euro";
        }

        //remove unnecessary string attachments
        string = cleanString(string);

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

        //Log.d(TAG, Double.toString(value));
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