package SmartUtilities.DaoLayer.ReadingService;

import SmartUtilities.Model.Reading.Reading;
import java.util.List;
import java.util.StringTokenizer;

public interface IReadingService {

    boolean addNewReading(Reading reading);

    boolean updateNewReading(Reading reading);

    boolean deleteReading(int customerId, String date);

    boolean deleteReadingByUuid(String uuid);

    Reading getReadingOfCustomer(int customerId, String date);

    List<Reading> getReadings();

    List<Reading> getReadingsByDateRange(String customerId, String start, String end, String kindOfMeter);

    List<Reading> getReading(int customerId);

    Reading getReadingByUuid(String uuid);

    boolean deleteAllReadings();

}
