package SmartUtilities.Model.Reading;

import SmartUtilities.Enums.KindOfMeter;
import SmartUtilities.Model.Customer.Customer;
import SmartUtilities.Shared.IID;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reading implements IReading, IID {

    private Customer customer;

    private int customerId;

    private KindOfMeter kindOfMeter;

    private String comment;

    private String meterId;

    private Double meterCount;

    private boolean substitute;

    private String dateOfReading;

    private UUID uuid;

    @JsonCreator
    public Reading(@JsonProperty("kindOfMeter") String k, @JsonProperty("comment") String comment, 
    @JsonProperty("meterId") String meterId, @JsonProperty("meterCount") Double meterCount,
    @JsonProperty("substitute") boolean substitute, @JsonProperty("dateOfReading") String date, 
    @JsonProperty("customerId") int customerId) {
        this.kindOfMeter = KindOfMeter.valueOf(k);
        this.comment = comment;
        this.meterId = meterId;
        this.meterCount = meterCount;
        this.substitute = substitute;
        this.dateOfReading = date;
        this.customerId = customerId;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public Customer getCustomer() {
        return customer;
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
    public String getDateOfReading() {
        return dateOfReading;
    }

    @Override
    public int getCustomerId(){
        return this.customerId;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setCustomer(Customer customer) {
            this.customer = customer;
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
    public void setDateOfReading(String dateOfReading) {
            this.dateOfReading = dateOfReading;
    }


    @Override //NUNCA DEVERIA SER USADO
    public void setUuid(UUID uuid) {
            this.uuid = uuid;
    }

    @Override
    public void setCustomerId(int id) {this.customerId = id;}

    @Override
    public String printDateOfReading() {
        return "";
    }
}
