package SmartUtilities.Services.ReadingService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Services.CustomerService.CustomerService;
import SmartUtilities.Services.CustomerService.ICustomerService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingService implements IReadingService {

    private Database _database;
    private CustomerService _customerService;

    public ReadingService(Database database) {
        this._database = database;
        this._customerService = new CustomerService(_database);

    }

    @Override
    public boolean addNewReading(Reading reading) {
        if (reading != null) {
            int customerId = reading.getCustomer().getId().orElse(0);

            // if getCustomerId() is null or zero, get automatic 0 !
            if (customerId == 0) {
                UUID uuidCustomer = reading.getCustomer().getUuid();
                String firstName = reading.getCustomer().getFirstName();
                String lastName = reading.getCustomer().getLastName();
                String birthDate = reading.getCustomer().getBirthDate().toString();
                String gender = reading.getCustomer().getGender().toString();

                Customer newCustomer = new Customer(null, firstName, lastName, birthDate, gender);
                newCustomer.setUuid(uuidCustomer);
                _customerService.addNewCustomer(newCustomer);
                Customer retrievedCustomer = _customerService.getCustomerByUuid(uuidCustomer.toString());
                customerId = retrievedCustomer.getId().orElse(0);
                reading.setCustomerId(customerId);
                reading.getCustomer().setId(customerId);
            }

            // update json of Reading
            Customer dbCustomer = _customerService.getCustomer(customerId);
            reading.getCustomer().setUuid(dbCustomer.getUuid());

            String sqlReading = "INSERT INTO data_reading (customer_id, kind_of_meter, " +
                    "comment, meter_id," +
                    "meter_count, substitute," +
                    " date_of_reading, uui_id) " +
                    "VALUES (' " +
                    customerId + "', '" +
                    reading.getKindOfMeter() + "', '" +
                    reading.getComment() + "', '" +
                    reading.getMeterId() + "', '" +
                    reading.getMeterCount() + "', '" +
                    (reading.getSubstitute() ? "1" : "0") + "', '" +
                    reading.getDateOfReading() + "', '" +
                    reading.getUuid() + "' )";

            this._database.queryWithoutReturn(sqlReading);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNewReading(Reading reading) {
        if (reading != null && reading.getCustomerId() != 0) {
            String sqlCustomer = "SELECT * FROM customers WHERE id = '" + reading.getCustomerId() + "'";
            String sqlReading = "UPDATE data_reading SET kind_of_meter = '" + reading.getKindOfMeter() + "', " +
                    "comment = '" + reading.getComment() + "', " +
                    "meter_id = '" + reading.getMeterId() + "', " +
                    "meter_count = '" + reading.getMeterCount() + "', " +
                    "substitute = '" + (reading.getSubstitute() ? "1" : "0") + "', " +
                    "date_of_reading = '" + reading.getDateOfReading() + "' " +
                    "WHERE customer_id = '" + reading.getCustomerId() + "'" +
                    "AND uui_id = '" + reading.getUuid().toString() + "'";

            // testar se exister customer
            ResultSet rs = this._database.queryWithReturn(sqlCustomer);
            this._database.queryWithoutReturn(sqlReading);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReading(int userId, String date) {
        if (userId != 0 && date != null) {
            String sqlCustomer = "SELECT * FROM customers WHERE id = '" + userId + "'";
            String sqlReading = "DELETE FROM data_reading WHERE customer_id = '" +
                    userId + "' AND date_of_reading = '" + date + "'";

            try (ResultSet rs = this._database.queryWithReturn(sqlCustomer)) {
            } catch (SQLException e) {
                System.out.println("Customer does not exist." + e.getMessage());
                return false;
            }
            this._database.queryWithoutReturn(sqlReading);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReadingByUuid(String uuid) {
        if (uuid != null) {
            String sqlReading = "DELETE FROM data_reading WHERE uui_id = '" +
                    uuid + "'";

            try {
                this._database.queryWithoutReturn(sqlReading);
                return true;
            } catch (Exception e) {
                System.out.println("Data does not exist." + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<Reading> getReading(int customerId) {
        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + customerId + "'";
        String sqlReading = "SELECT * FROM data_reading WHERE customer_id = '" + customerId + "'";
        List<Reading> readingsOfCustomer = new ArrayList<>();

        try (ResultSet rs = this._database.queryWithReturn(sqlCustomer)) {
        } catch (SQLException e) {
            System.out.println("Customer does not exist." + e.getMessage());
            return null;
        }

        try (ResultSet rs = this._database.queryWithReturn(sqlReading)) {
            while (rs.next()) {
                Reading dbReading = new Reading(
                        rs.getString("kind_of_meter"),
                        rs.getString("comment"),
                        rs.getString("meter_id"),
                        rs.getDouble("meter_count"),
                        rs.getBoolean("substitute"),
                        rs.getString("date_of_reading"),
                        rs.getInt("customer_id"));

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                readingsOfCustomer.add(dbReading);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
            return null;
        }
        return readingsOfCustomer;

    }

    @Override
    public Reading getReadingOfCustomer(int customerId, String date) {

        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + customerId + "'";
        String sqlReading = "SELECT * FROM data_reading WHERE customer_id = '" + customerId + "'"
                + "AND date_of_reading = '" + date + "'";

        try (ResultSet rs = this._database.queryWithReturn(sqlCustomer)) {
        } catch (SQLException e) {
            System.out.println("Customer does not exist." + e.getMessage());
        }

        try (ResultSet rs = this._database.queryWithReturn(sqlReading)) {
            while (rs.next()) {
                Reading dbReading = new Reading(
                        rs.getString("kind_of_meter"),
                        rs.getString("comment"),
                        rs.getString("meter_id"),
                        rs.getDouble("meter_count"),
                        rs.getBoolean("substitute"),
                        rs.getString("date_of_reading"),
                        rs.getInt("customer_id"));

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                return dbReading;
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
        }
        return null;

    }

    @Override
    public Reading getReadingByUuid(String uuid) {
        String sqlReading = "SELECT * FROM data_reading WHERE uui_id = '" + uuid + "'";

        try (ResultSet rs = this._database.queryWithReturn(sqlReading)) {
            while (rs.next()) {
                Reading dbReading = new Reading(
                        rs.getString("kind_of_meter"),
                        rs.getString("comment"),
                        rs.getString("meter_id"),
                        rs.getDouble("meter_count"),
                        rs.getBoolean("substitute"),
                        rs.getString("date_of_reading"),
                        rs.getInt("customer_id"));

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                return dbReading;
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
        }
        return null;

    }

    @Override
    public List<Reading> getReadings() {
        String sqlReading = "SELECT * FROM data_reading";
        List<Reading> readings = new ArrayList<>();

        try (ResultSet rs = this._database.queryWithReturn(sqlReading);) {
            while (rs.next()) {
                Reading dbReading = new Reading(
                        rs.getString("kind_of_meter"),
                        rs.getString("comment"),
                        rs.getString("meter_id"),
                        rs.getDouble("meter_count"),
                        rs.getBoolean("substitute"),
                        rs.getString("date_of_reading"),
                        rs.getInt("customer_id"));

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                readings.add(dbReading);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
            return null;
        }
        return readings;

    }

    @Override
    public List<Reading> getReadingsByDateRange(String customerId, String start, String end, String kindOfMeter) {
        if (end == null) {
            LocalDate currenteDate = LocalDate.now();
            String formatedDate = currenteDate.toString();
            end = formatedDate;
        }

        String sqlReading = (start == null) ? "SELECT * FROM data_reading WHERE customer_id = '" + customerId +
                "' AND date_of_reading <= '" + end + "'"
                : "SELECT * FROM data_reading WHERE customer_id = '" + customerId +
                        "' AND kind_of_meter = '" + kindOfMeter +
                        "' AND date_of_reading >= '" + start + "' AND date_of_reading <= '" + end + "'";

        List<Reading> readings = new ArrayList<>();

        try (ResultSet rs = this._database.queryWithReturn(sqlReading)) {
            while (rs.next()) {
                Reading dbReading = new Reading(
                        rs.getString("kind_of_meter"),
                        rs.getString("comment"),
                        rs.getString("meter_id"),
                        rs.getDouble("meter_count"),
                        rs.getBoolean("substitute"),
                        rs.getString("date_of_reading"),
                        rs.getInt("customer_id"));

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                readings.add(dbReading);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
            return null;
        }
        return readings;
    }

    @Override
    public boolean deleteAllReadins() {
        try {
            // delete all readings from table
            // String sql = "DELETE FROM data_reading";
            String sql = "DROP TABLE IF EXISTS data_reading_test;";
            this._database.queryWithoutReturn(sql);

            String sql2 = """
                    CREATE TABLE IF NOT EXISTS data_reading_test (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        customer_id INT,
                        kind_of_meter VARCHAR(10),
                        comment VARCHAR(255),
                        meter_id VARCHAR(20),
                        meter_count DOUBLE,
                        substitute BOOLEAN,
                        date_of_reading VARCHAR(20),
                        uui_id VARCHAR(255),
                        FOREIGN KEY (customer_id) REFERENCES customers_test(id)
                    );
                    """;
            this._database.queryWithoutReturn(sql2);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error while deleting all readings: " + e.getMessage());
            return false;
        }
    }
}
