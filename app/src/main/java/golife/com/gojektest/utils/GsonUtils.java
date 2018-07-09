package golife.com.gojektest.utils;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class GsonUtils {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static Gson gson;


    public static JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            // return src == null ? null : new JsonPrimitive(src.getTime());
            // GsonUtils.format.setCalendar(new GregorianCalendar(new
            // SimpleTimeZone(0, "GMT")));
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

public static JsonSerializer<Date> customDateJsonSerializer = new JsonSerializer<Date>() {
        @Override
		public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			return new JsonPrimitive(sdf.format(date));
		}
	};



    public static JsonDeserializer<Date> customDateJsonDeserializer = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new Date(json.getAsLong());
        }
    };

    public static JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // GsonUtils.format.setCalendar(new GregorianCalendar(new
            // SimpleTimeZone(0, "GMT")));
            Date date = null;
            try {
                if (json.getAsString().contains("NumberLong")) {
                    date = new Date(Double.valueOf(json.getAsString().replace("NumberLong", "").replace("(", "").replace(")", "").replace("\"", ""))
                            .longValue());
                } else if (json != null) {
                    date = new Date(json.getAsLong());
                }
            } catch (NumberFormatException e) {

            }
            return date;
        }
    };


    public static JsonSerializer<Class> classJsonSerializer = new JsonSerializer<Class>() {
        @Override
        public JsonElement serialize(Class arg0, Type arg1, JsonSerializationContext arg2) {
            return null;// new JsonPrimitive(arg0.getCanonicalName());
        }

    };
    public static JsonDeserializer<Class> classJsonDeserializer = new JsonDeserializer<Class>() {
        @Override
        public Class deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return null;// Class.forName(json.getAsString());
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    };

    public static JsonSerializer<URL> urlJsonSerializer = new JsonSerializer<URL>() {
        @Override
        public JsonElement serialize(URL arg0, Type arg1, JsonSerializationContext arg2) {
            return new JsonPrimitive(arg0.toString());
        }

    };
    public static JsonDeserializer<URL> urlJsonDeserializer = new JsonDeserializer<URL>() {
        @Override
        public URL deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return new URL(json.getAsString());
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    };

    public static JsonSerializer<File> fileJsonSerializer = new JsonSerializer<File>() {
        @Override
        public JsonElement serialize(File arg0, Type arg1, JsonSerializationContext arg2) {
            return null;// new JsonPrimitive(arg0.getCanonicalName());
        }

    };
    public static JsonDeserializer<File> fileJsonDeserializer = new JsonDeserializer<File>() {
        @Override
        public File deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return null;// Class.forName(json.getAsString());
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    };

    public static JsonDeserializer<Long> longJsonDeserializer = new JsonDeserializer<Long>() {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().contains("NumberLong")) {
                    return Double.valueOf(json.getAsString().replace("NumberLong", "").replace("(", "").replace(")", "").replace("\"", ""))
                            .longValue();
                } else {
                    return Double.valueOf(json.getAsString()).longValue();
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    };


}
