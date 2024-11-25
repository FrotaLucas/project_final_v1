package SmartUtilities.Services.ReadingService;

import SmartUtilities.DataBase.Database;
import SmartUtilities.Model.Reading.Reading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReadingService implements IReadingService{

    private Database _database;

    public ReadingService(Database database){

        this._database = database;
    }

    @Override
    public void addNewReading(Reading reading) {
        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + reading.getCustomerId() + "'";
        String sqlReading = "INSERT INTO data_reading (customer_id, kind_of_meter, " +
                "comment, meter_id," +
                "meter_count, substitute," +
                " date_of_reading, uui_id) " +
                "VALUES (' " +
                reading.getCustomerId() + "', '" +
                reading.getKindOfMeter() + "', '" +
                reading.getComment() + "', '" +
                reading.getMeterId() + "', '" +
                reading.getMeterCount() + "', '" +
                (reading.getSubstitute() ? "1" : "0") + "', '" +
                reading.getDateOfReading() + "', '" +
                reading.getId() + "' )";


        try (ResultSet rs = this._database.executeQuery(sqlCustomer)){

        } catch (SQLException e) {
            System.out.println("Customer does not exist." + e.getMessage());
        }

        try {
            this._database.executeUpdate(sqlReading);
        } catch (SQLException e)
        {
            System.out.println("Error while saving new data." + e.getMessage());
        }

    }

    @Override
    public void updateNewReading(Reading reading) {
        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + reading.getCustomerId() + "'";
        String sqlReading = "UPDATE data_reading SET kind_of_meter = '" + reading.getKindOfMeter() + "', " +
                "comment = '" + reading.getComment() + "', " +
                "meter_id = '" + reading.getMeterId()  + "', " +
              "meter_count = '" +  reading.getMeterCount() + "', " +
               "substitute = '" + (reading.getSubstitute() ? "1" : "0") + "', " +
                "date_of_reading = '" + reading.getDateOfReading() + "' " +
                "WHERE customer_id = '" + reading.getCustomerId() + "'";

        try (ResultSet rs = this._database.executeQuery(sqlCustomer)){
        } catch (SQLException e) {
            System.out.println("Customer does not exist." + e.getMessage());
        }
        try {
            this._database.executeUpdate(sqlReading);
        } catch (SQLException e)
        {
            System.out.println("Error while saving new data." + e.getMessage());
        }
    }

//  @Override
//    public void deleteReading(int userId, String date) {
//        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + userId + "'";
//        String sqlReading = "DELETE FROM data_reading WHERE customer_id = '" +
//        userId + "' AND date_of_reading = '" + date + "'";
//
//        try (ResultSet rs = this._database.executeQuery(sqlCustomer)){
//        } catch (SQLException e) {
//            System.out.println("Customer does not exist." + e.getMessage());
//        }
//
//        try (ResultSet rs = this._database.executeQuery(sqlReading)){
//        }
//        catch  (SQLExecption e){
//            System.out.println("Error while deleting data." + e.getMessage());
//        }
//
//
//    }
//
//    @Override
//    public List<Reading> getReading(int customerId)
//    {
//        String sqlCustomer = "SELECT * FROM customers WHERE id = '" + customerId + "'";
//        String sqlReading = "SELECT * FROM data_reading WHERE customer_id = '" + customerId + "'";
//        List<Reading> readingsOfCustomer = new ArrayList<>();
//
//        try (ResultSet rs = this._database.executeQuery(sqlCustomer)){
//        } catch (SQLException e) {
//            System.out.println("Customer does not exist." + e.getMessage());
//        }
//
//        try (ResultSet rs = this._database.executeQuery(sqlReading)) {
//            while (rs.next())
//            {
//                readingsOfCustomer.add(new Reading(rs.getString("customer_id"),
//                rs.getString("kind_of_meter"),
//                rs.getString("comment"),
//                rs.getString("meter_id"),
//                rs.getString("meter_count"),
//                rs.getString("substitute"),
//                rs.getString("date_of_reading")
//                ));
//            }
//            return readingsOfCustomer;
//        }
//        catch (SQLExeption e){
//            System.out.println("Error while retrieving data." + e.getMessage());
//        }
//
//    }
//
//    @Override
//    public List<Reading> getReadings() {
//         String sqlReading = "SELECT * FROM data_reading";
//         List<Reading> readings = new ArrayList<>();
//
//         try(ResultSet rs = this.update.executeQuery(sqlReading) ){
//             while (rs.next())
//             {
//                 readings.add(new Reading(rs.getString("customer_id"),
//                  rs.getString("kind_of_meter"),
//                  rs.getString("comment"),
//                  rs.getString("meter_id"),
//                  rs.getString("meter_count"),
//                  rs.getString("substitute"),
//                  rs.getString("date_of_reading")
//                 ));
//             }
//             return readings;
//         }
//            catch (SQLExeption e){
//            System.out.println("Error while retrieving data." + e.getMessage());
//        }
//    }
}
