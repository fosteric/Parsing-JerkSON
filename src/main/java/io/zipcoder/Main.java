package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.Iterator;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String input = (new Main()).readRawDataToString();
        //System.out.println(input);
        // TODO: parse the data in output into items, and display to console.
        ItemParser ip = new ItemParser();
        ArrayList<String> groceryItemStringsAsList = ip.parseRawDataIntoStringArray(input);
        ArrayList<Item> itemList = new ArrayList<Item>();
        Iterator<String> itemIterator = groceryItemStringsAsList.iterator();
        try {
            while (itemIterator.hasNext()) {
                itemList.add(ip.parseStringIntoItem(itemIterator.next()));
            }
        }catch (Exception e){
            System.out.println("Why lawd:" + e);
        }
        for (Item i:itemList) {
            System.out.println(i.getName() + " " + i.getPrice());
        }

    }
}
