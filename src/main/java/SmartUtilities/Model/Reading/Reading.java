package SmartUtilities.Model.Reading;

import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.Customer;
import java.time.LocalDate;

public class Reading implements IReading {

    //private Customer customer;

    //private int customerId;

    private KindOfMeter kindOfMeter;

    private String comment;

    private String meterId;

    private Double meterCount;

    private boolean substitute;

    private LocalDate dateOfReading;

    public Reading(KindOfMeter kindOfMeter, String comment, String meterId, Double meterCount, boolean substitute, LocalDate dateOfReading){
        this.kindOfMeter = kindOfMeter;
        this.comment = comment;
        this.meterId = meterId;
        this.meterCount = meterCount;
        this.substitute = substitute;
        this.dateOfReading = dateOfReading;
    }

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public KindOfMeter getKindOfMeter() {
        return kindOfMeter;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String getMeterId() {
        return meterId;
    }

    @Override
    public Double getMeterCount() {
        return meterCount;
    }

    @Override
    public Boolean getSubstitute() {
        return substitute;
    }

    @Override
    public LocalDate getDateOfReading() {
        return dateOfReading;
    }

    @Override
    public void setCustomer(Customer customer) {

    }

    @Override
    public void setKindOfMeter(KindOfMeter kindOfMeter) {
            this.kindOfMeter = kindOfMeter;
    }

    @Override
    public void setComment(String comment) {
            this.comment = comment;
    }

    @Override
    public void setMeterId(String meterId) {
            this.meterId = meterId;
    }

    @Override
    public void setMeterCount(Double meterCount) {
            this.meterCount = meterCount;
    }

    @Override
    public void setSubstitute(Boolean substitute) {
            this.substitute = substitute;
    }

    @Override
    public void setDateOfReading(LocalDate dateOfReading) {
            this.dateOfReading = dateOfReading;
    }

    @Override
    public String printDateOfReading() {
        return "";
    }
}
