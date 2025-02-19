package SmartUtilities.Services.ReadingService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Model.Reading.Reading;
import SmartUtilities.Services.CustomerService.ICustomerService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingService implements IReadingService {

    private Database _database;
    private ICustomerService customerService;

    public ReadingService(Database database, ICustomerService customerService) {
        this.customerService = customerService;
        this._database = database;
    }

    @Override
    public boolean addNewReading(Reading reading) {
        if( reading != null)
        {
            int customerId = reading.getCustomerId();
    
            //se getCustomerId() for vazio ou nulo, ele eh automaticamente zero!!
            if (customerId == 0) {
                Customer newCustomer = new Customer(null, "x", "x", "1900-01-01", "M");
                //In case customer does not exist
                UUID uuid = newCustomer.getUuid();
                customerService.addNewCustomer(newCustomer);
                Customer retrievedCustomer = customerService.getCustomerByUuid(uuid.toString());
                customerId = retrievedCustomer.getId().orElse(0);
                reading.setCustomerId(customerId);
            }
    
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
        if( reading != null  && reading.getCustomerId() != 0)
        {
            String sqlCustomer = "SELECT * FROM customers WHERE id = '" + reading.getCustomerId() + "'";
            String sqlReading = "UPDATE data_reading SET kind_of_meter = '" + reading.getKindOfMeter() + "', " +
                    "comment = '" + reading.getComment() + "', " +
                    "meter_id = '" + reading.getMeterId() + "', " +
                    "meter_count = '" + reading.getMeterCount() + "', " +
                    "substitute = '" + (reading.getSubstitute() ? "1" : "0") + "', " +
                    "date_of_reading = '" + reading.getDateOfReading() + "' " +
                    "WHERE customer_id = '" + reading.getCustomerId() + "'" +
                    "AND uui_id = '" + reading.getUuid().toString() + "'";
    
            //testar se exister customer
            ResultSet rs = this._database.queryWithReturn(sqlCustomer);
            this._database.queryWithoutReturn(sqlReading);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReading(int userId, String date) {
        if(userId != 0 && date != null)
        {
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
    public boolean deleteReadingByUuid(String uuid)
    {
        if(uuid != null)
        {
            String sqlReading = "DELETE FROM data_reading WHERE uui_id = '" +
                    uuid + "'";
    
            try (ResultSet rs = this._database.queryWithReturn(sqlReading)) {
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
                        rs.getInt("customer_id")
                );

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
                        rs.getInt("customer_id")
                );

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
                        rs.getInt("customer_id")
                );

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
                        rs.getInt("customer_id")
                );

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
    public List<Reading> getReadingsByDateRange(String customerId, String start, String end)
    {   
        if(end == null)
            {
                LocalDate currenteDate = LocalDate.now();
                String formatedDate = currenteDate.toString();
                end = formatedDate;
            }

        String sqlReading = (start == null) ? "SELECT * FROM data_reading WHERE customer_id = '" + customerId + 
        "' AND date_of_reading <= '" + end + "'" :  
        "SELECT * FROM data_reading WHERE customer_id = '" + customerId + 
        "' AND date_of_reading >= '" + start + "' AND date_of_reading <= '" + end + "'"; 
        
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
                        rs.getInt("customer_id")
                );

                dbReading.setUuid(UUID.fromString(rs.getString("uui_id")));
                readings.add(dbReading);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data." + e.getMessage());
            return null;
        }
        return readings;

    }
}
