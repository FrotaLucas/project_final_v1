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

    }

    @Override
    public void deleteReading(int customerId) {

    }

    @Override
    public Reading getReading(int customerId) {
        return null;
    }

    @Override
    public List<Reading> gerReadings() {
        return List.of();
    }
}
