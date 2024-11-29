package SmartUtilities.Services.ReadingService;

import SmartUtilities.Model.Reading.Reading;
import java.util.List;
import java.util.StringTokenizer;

public interface IReadingService {

    void addNewReading(Reading reading);

    void updateNewReading(Reading reading);

    void deleteReading(int customerId, String date);

    Reading getReadingOfCustomer(int customerId, String date);

    List<Reading> getReadings();

    List<Reading> getReading(int customerId);

    Reading getReadingByUuid(String uuid);

}
