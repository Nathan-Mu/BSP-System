package BSP;

import java.util.*;
import java.io.*;
import org.json.JSONObject;
import static BSP.JSONReader.*;
import static BSP.BSPConstant.*;

/**
 * Created by nathan on 13/5/17.
 */
public class DataManager {
    public void writeFile(List<String> list)
    {
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(FILE_NAME);
            for (String s: list)
            {
                writer.println(s);
            }
            writer.close();
        }
        catch (Exception e)
        {
        }
    }
    
    public List<JSONObject> readFile()
    {
        FileReader reader;
        List<JSONObject> list = new ArrayList<>();
        JSONObject json = null;
        try
        {
            reader = new FileReader(FILE_NAME);
            Scanner parser = new Scanner(reader);
            while (parser.hasNextLine())
            {
                list.add(toJSONObject(parser.nextLine()));
            }
            reader.close();
        }
        catch (Exception e)
        {
        }
        return list;
    }
}
