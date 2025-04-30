package stage4.server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class FileRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String dataDirectory;
    private volatile long nextID = 1;

    // For serialization, we don't need to save both the IDs-to-names and names-to-IDS maps:
    // either one can be rebuilt from the other
    private final Map<Long, String> idsToNames = new HashMap<>();
    private transient volatile Map<String, Long> namesToIDs = new HashMap<>();

    public record Entry(long id, File file) {}

    FileRegistry(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    @Serial
    private void readObject(ObjectInputStream in) throws Exception {
        in.defaultReadObject();
        namesToIDs = new HashMap<>();
        idsToNames.forEach((id, name) -> namesToIDs.put(name, id));
    }

    static FileRegistry load(File data) throws Exception {
        try (var fileInputStream = new FileInputStream(data);
             var bufferedInputStream = new BufferedInputStream(fileInputStream);
             var objectInputStream = new ObjectInputStream(bufferedInputStream)
        ) {
            return (FileRegistry) objectInputStream.readObject();
        }
    }

    synchronized void save(File data) throws Exception {
        try (var fileOutputStream = new FileOutputStream(data);
             var bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             var objectOutputStream = new ObjectOutputStream(bufferedOutputStream)
        ) {
            objectOutputStream.writeObject(this);
        }
    }

    Optional<Entry> lookup(long id) {
        return Optional.ofNullable(idsToNames.get(id))
                .map(name -> new Entry(id, new File(dataDirectory, name)));
    }

    Optional<Entry> lookup(String name) {
        return Optional.ofNullable(namesToIDs.get(name))
                .map(id -> new Entry(id, new File(dataDirectory, name)));
    }

    Optional<Entry> lookup(String method, String reference) {
        return switch (method) {
            case "BY_ID" -> lookup(Long.parseLong(reference));
            case "BY_NAME" -> lookup(reference);
            default -> throw new IllegalArgumentException("Unknown lookup method: " + method);
        };
    }

    synchronized Entry add() {
        String generated = String.valueOf(nextID);
        namesToIDs.put(generated, nextID);
        idsToNames.put(nextID, generated);
        File file = new File(dataDirectory, generated);
        return new Entry(nextID++, file);
    }

    synchronized Optional<Entry> add(String name) {
        if (namesToIDs.containsKey(name)) {
            return Optional.empty();
        } else {
            namesToIDs.put(name, nextID);
            idsToNames.put(nextID, name);
            File file = new File(dataDirectory, name);
            return Optional.of(new Entry(nextID++, file));
        }
    }

    synchronized Optional<Entry> delete(long id) {
        return Optional.ofNullable(idsToNames.remove(id))
                .map(name -> new Entry(id, new File(dataDirectory, name)));
    }

    synchronized Optional<Entry> delete(String name) {
        return Optional.ofNullable(namesToIDs.remove(name))
                .map(id -> new Entry(id, new File(dataDirectory, name)));
    }

    synchronized Optional<Entry> delete(String method, String reference) {
        return switch (method) {
            case "BY_ID" -> delete(Long.parseLong(reference));
            case "BY_NAME" -> delete(reference);
            default -> throw new IllegalArgumentException("Unknown deletion method: " + method);
        };
    }
}
