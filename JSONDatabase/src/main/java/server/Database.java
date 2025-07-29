package server;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.locks.*;

public class Database {

    record ObjectKeyPair(JsonObject object, String key) {}

    private final JsonObject data;
    private final Path dataFilePath;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private Database(JsonObject data, Path dataFilePath) {
        this.data = data;
        this.dataFilePath = dataFilePath;
    }

    public static Database load(String dataFile) throws IOException {
        Path dataFilePath = Path.of(System.getProperty("user.dir"), dataFile);
        JsonObject data = new Gson().fromJson(Files.readString(dataFilePath), JsonObject.class);
        return new Database(data, dataFilePath);
    }

    public JsonElement get(JsonElement key) {
        readLock.lock();
        ObjectKeyPair location = traverse(key);
        JsonElement value = location.object().get(location.key());
        readLock.unlock();
        return value;
    }

    public void set(JsonElement key, JsonElement value) throws IOException {
        writeLock.lock();
        if (key.isJsonArray()) {
            set(data, key.getAsJsonArray(), value);
        } else {
            data.add(key.getAsString(), value);
        }
        writeLock.unlock();
        writeData();
    }

    public JsonElement delete(JsonElement key) throws IOException {
        writeLock.lock();
        ObjectKeyPair location = traverse(key);
        JsonElement deleted = location.object().remove(location.key());
        writeLock.unlock();
        if (deleted != null) writeData();
        return deleted;
    }

    private ObjectKeyPair traverse(JsonElement keyElement) {
        if (!keyElement.isJsonArray()) {
            return new ObjectKeyPair(data, keyElement.getAsString());
        }
        JsonObject object = data;
        JsonArray keys = keyElement.getAsJsonArray();
        String finalKey = keys.remove(keys.size() - 1).getAsString();
        for (JsonElement key : keys) {
            object = object.getAsJsonObject(key.getAsString());
        }
        return new ObjectKeyPair(object, finalKey);
    }

    private JsonObject set(JsonObject object, JsonArray keys, JsonElement value) {
        String firstKey = keys.remove(0).getAsString();
        if (keys.isEmpty()) {
            object.add(firstKey, value);
        } else {
            object.add(firstKey, set(object.getAsJsonObject(firstKey), keys, value));
        }
        return object;
    }

    private void writeData() throws IOException {
        readLock.lock();
        String contents = new Gson().toJson(data);
        readLock.unlock();
        Files.writeString(dataFilePath, contents, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
