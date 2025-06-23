package carsharing.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<E> {

    E map(ResultSet resultSet) throws SQLException;
}
