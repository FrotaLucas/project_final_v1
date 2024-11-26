package SmartUtilities.Services.ReadingService;

import SmartUtilities.Model.Reading.Reading;
import java.util.List;

public interface IReadingService {

    void addNewReading(Reading reading);

    void updateNewReading(Reading reading);

    void deleteReading(int customerId, String data);

    List<Reading> getReading(int customerId);

    List<Reading> getReadings();
}
