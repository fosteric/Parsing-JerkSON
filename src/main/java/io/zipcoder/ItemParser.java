package io.zipcoder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private int error = 0;

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
        String lowerCaseString = toLowercase(rawItem);
        ArrayList<String> groceryItemKeyValues = findKeyValuePairsInRawItemData(lowerCaseString);
        String name = null;
        Double price = null;
        String type = null;
        String expiration = null;
        for (int i = 0; i <groceryItemKeyValues.size(); i++){
            ArrayList<String> keyAndValue = findKeyAndValueInRawItemData(groceryItemKeyValues.get(i));
            if (keyAndValue.size()<2){
                error++;
                throw new ItemParseException();
            }
                switch (i) {
                    case 0:
                        name = keyAndValue.get(1);
                        break;
                    case 1:
                        price = Double.parseDouble(keyAndValue.get(1));
                        break;
                    case 2:
                        type = keyAndValue.get(1);
                        break;
                    case 3:
                        expiration = keyAndValue.get(1);
                        break;
                }

        }
        return new Item(name, price, type, expiration);
    }

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> groceryItemsAsList = splitStringWithRegexPattern(stringPattern , rawData);
        return groceryItemsAsList;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern2 = "##";
        Matcher m = Pattern.compile(stringPattern2,Pattern.CASE_INSENSITIVE).matcher(rawItem);
        String hashesRemoved = m.replaceAll("");
        String stringPattern = "[% *!;|^]";
        ArrayList<String> keyValuePairAsList = splitStringWithRegexPattern(stringPattern , hashesRemoved);

        return keyValuePairAsList;
    }

    public String toLowercase(String rawData){
        Matcher milkMatch = Pattern.compile("milk",Pattern.CASE_INSENSITIVE).matcher(rawData);
        String milkResponse = milkMatch.replaceAll("milk");
        Matcher breadMatch = Pattern.compile("bread",Pattern.CASE_INSENSITIVE).matcher(milkResponse);
        String breadResponse = breadMatch.replaceAll("bread");
        Matcher applesMatch = Pattern.compile("apples",Pattern.CASE_INSENSITIVE).matcher(breadResponse);
        String applesResponse = applesMatch.replaceAll("apples");
        Matcher cookiesMatch = Pattern.compile("cookies",Pattern.CASE_INSENSITIVE).matcher(applesResponse);
        String cookiesResponse = cookiesMatch.replaceAll("cookies");
        Matcher foodMatch = Pattern.compile("food",Pattern.CASE_INSENSITIVE).matcher(cookiesResponse);
        return foodMatch.replaceAll("food");
    }

    public int getCount(){
        return 0;
    }

    public ArrayList<String> findKeyAndValueInRawItemData(String rawItem){
        String stringPattern = ":";
        ArrayList<String> keyAndValueList = splitStringWithRegexPattern(stringPattern , rawItem);
        return keyAndValueList;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public String formatList(ArrayList<Item> items){

        return null;
    }



}
