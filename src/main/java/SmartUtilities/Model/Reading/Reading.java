package SmartUtilities.Model.Reading;

import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.Customer;
import java.time.LocalDate;

public class Reading implements IReading {

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public KindOfMeter getKindOfMeter() {
        return null;
    }

    @Override
    public String getComment() {
        return "";
    }

    @Override
    public String getMeterId() {
        return "";
    }

    @Override
    public Double getMeterCount() {
        return 0.0;
    }

    @Override
    public Boolean getSubstitute() {
        return null;
    }

    @Override
    public LocalDate getDateOfReading() {
        return null;
    }

    @Override
    public void setCustomer(Customer customer) {

    }

    @Override
    public void setKindOfMeter(KindOfMeter kindOfMeter) {

    }

    @Override
    public void setComment(String comment) {

    }

    @Override
    public void setMeterId(String meterId) {

    }

    @Override
    public void setMeterCount(Double meterCount) {

    }

    @Override
    public void setSubstitute(Boolean substitute) {

    }

    @Override
    public void setDateOfReading(LocalDate dateOfReading) {

    }

    @Override
    public String printDateOfReading() {
        return "";
    }
}
