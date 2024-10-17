package SmartUtilities.Services.ReadingService;

import SmartUtilities.Model.Reading.Reading;
import java.util.List;

public interface IReadingService {

    void addNewReading(Reading reading);

    void updateNewReading(Reading reading);

    void deleteReading(int customerId);

    Reading getReading(int customerId);

    List<Reading> gerReadings();
}
