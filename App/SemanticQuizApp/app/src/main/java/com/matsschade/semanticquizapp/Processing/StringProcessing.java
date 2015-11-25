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