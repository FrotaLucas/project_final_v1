package SmartUtilities.Model.Reading;

import java.time.LocalDate;
import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.Customer;


public interface IReading {

    Customer getCustomer();

    KindOfMeter getKindOfMeter();

    String getComment();

    String getMeterId();

    Double getMeterCount();

    Boolean getSubstitute();

    LocalDate getDateOfReading();

    void setCustomer(Customer customer);

    void setKindOfMeter(KindOfMeter kindOfMeter);

    void setComment(String comment);

    void setMeterId(String meterId);

    void setMeterCount(Double meterCount);

    void setSubstitute(Boolean substitute);

    void setDateOfReading(LocalDate dateOfReading);

    String printDateOfReading();
}
