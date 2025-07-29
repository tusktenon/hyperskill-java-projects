package server;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.locks.*;

public class Database {

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

    public JsonElement get(String key) {
        readLock.lock();
        JsonElement value = data.get(key);
        readLock.unlock();
        return value;
    }

    public void set(String key, String value) throws IOException {
        writeLock.lock();
        data.addProperty(key, value);
        writeLock.unlock();
        writeData();
    }

    public JsonElement delete(String key) throws IOException {
        writeLock.lock();
        JsonElement deleted = data.remove(key);
        writeLock.unlock();
        if (deleted != null) writeData();
        return deleted;
    }

    private void writeData() throws IOException {
        readLock.lock();
        String contents = new Gson().toJson(data);
        readLock.unlock();
        Files.writeString(dataFilePath, contents, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
