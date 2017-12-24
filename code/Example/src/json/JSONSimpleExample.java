package json;

import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import  java.util.Iterator;

/** A class that shows how to use JSONSimple library to parse a simple json file (see "file.json"); */
public class JSONSimpleExample {
    public static void main(String[] args) {

        readJSON("file.json");
    }

    public static void readJSON(String filename) {
        JSONParser parser = new JSONParser();
        try {JSONObject obj = (JSONObject)parser.parse(new FileReader(filename));
            JSONArray arr = (JSONArray)obj.get("data");
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject res = iterator.next();
                System.out.println((String) res.get("name"));
             }
        }
        catch  (FileNotFoundException e) {
            System.out.println("Could not find file: " + filename);
        } catch (ParseException e) {
            System.out.println("Can not parse a given json file. ");
        } catch (IOException e) {
            System.out.println("General IO Exception in readJSON");
        }
        // should close JSONParser...
    }

}
