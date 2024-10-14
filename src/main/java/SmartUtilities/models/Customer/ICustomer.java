package SmartUtilities.models.Customer;

import java.time.LocalDate;

import SmartUtilities.Enums.Gender;

public interface ICustomer {
    
    String getFirstName();
    
    String getLastName();
    
    LocalDate getBirthDate();

    Gender getGender();

    void setFirstName(String firstName);
   
    void setLastName(String lastName);
    
    void setBirthDate(LocalDate birtDate);

    void setGender(Gender gender);

}
