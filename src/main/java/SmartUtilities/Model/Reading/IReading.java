package SmartUtilities.Model.Reading;

import java.time.LocalDate;
import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.Customer;


public interface IReading {

    String getComment();

    Customer getCustomer();

    LocalDate getDateOfReading();

    KindOfMeter getKindOfMeter();

    Double getMeterCount();

    String getMeterId();

    Boolean getSubstitute();

    String printDateOfReading();

    void setComment(String comment);

    void setCustomer(Customer customer);

    void setDateOfReading(LocalDate dateOfReading);

    void setKindOfMeter(KindOfMeter kindOfMeter);

    void setMeterCount(Double meterCount);

    void setMeterId(String meterId);

    void setSubstitute(Boolean substitute);
}
