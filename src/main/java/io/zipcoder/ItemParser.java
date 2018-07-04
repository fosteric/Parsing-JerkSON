package io.zipcoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private int error = 0;

    public int getError() {
        return error;
    }

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
                this.error++;
                System.out.println("Why lawd:io.zipcoder.ItemParseException");
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

    public ArrayList<String> findKeyAndValueInRawItemData(String rawItem){
        String stringPattern = ":";
        ArrayList<String> keyAndValueList = splitStringWithRegexPattern(stringPattern , rawItem);
        return keyAndValueList;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public String formatItemListToString(ArrayList<Item> itemList){
        Map<String, Integer> itemAtPriceCountMap = new TreeMap<String, Integer>();
        for (Item i : itemList){
            String itemKey = i.getName() + " at " + i.getPrice() +  " per unit";
            Integer seen = itemAtPriceCountMap.containsKey(itemKey) ? itemAtPriceCountMap.get(itemKey) : 0;
            itemAtPriceCountMap.put(itemKey, seen + 1);
        }
        String itemsStringOutput = formatItemMapToString(itemAtPriceCountMap);
        return itemsStringOutput;
    }

    public String formatItemMapToString(Map<String, Integer> itemAtPriceCountMap){
        Iterator itemMapIterator = itemAtPriceCountMap.entrySet().iterator();
        String formattedItemString = "";
        while (itemMapIterator.hasNext()){
            Map.Entry pair = (Map.Entry)itemMapIterator.next();
            formattedItemString += pair.getKey() + ", seen: " + pair.getValue() + "\n";
        }
        formattedItemString += "Errors seen: " + getError();
        return formattedItemString;
    }

    public void printOutput(String itemStringOutput) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File("output.txt"));
        PrintStream console = System.out;
        System.setOut(o);
        System.out.println(itemStringOutput);
        System.setOut(console);
        System.out.println(itemStringOutput);
    }

    public ArrayList<Item> parseItemStringToItemObject(ArrayList<String> groceryItemStringsAsList) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Iterator<String> itemIterator = groceryItemStringsAsList.iterator();
        try {
            while (itemIterator.hasNext()) {
                itemList.add(parseStringIntoItem(itemIterator.next()));
            }
        } catch (Exception e) {
            error++;
            System.out.println("Why lawd:" + e);
        }
        return itemList;
    }



}
