package SmartUtilities.Model.Reading;

import java.time.LocalDate;
import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.ICustomer;


public interface IReading {

    String getComment();

    ICustomer getCustomer();

    LocalDate getDateOfReading();

    KindOfMeter getKindOfMeter();

    Double getMeterCount();

    String getMeterId();

    Boolean getSubstitute();

    String printDateOfReading();

    void setComment(String comment);

    void setCustomer(ICustomer customer);

    void setDateOfReading(LocalDate dateOfReading);

    void setKindOfMeter(KindOfMeter kindOfMeter);

    void setMeterCount(Double meterCount);

    void setMeterId(String meterId);

    void setSubstitute(Boolean substitute);
}
