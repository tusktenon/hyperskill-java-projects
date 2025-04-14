package server;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

public class StorageEmulator {

    enum File {file1, file2, file3, file4, file5, file6, file7, file8, file9, file10}

    private final Set<File> storage = EnumSet.noneOf(File.class);

    boolean add(String fileName) {
        return commandWrapper(fileName, storage::add);
    }

    boolean get(String fileName) {
        return commandWrapper(fileName, storage::contains);
    }

    boolean delete(String fileName) {
        return commandWrapper(fileName, storage::remove);
    }

    private boolean commandWrapper(String filename, Predicate<File> command) {
        try {
            return command.test(File.valueOf(filename));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
