package SmartUtilities.models.Customer;
import java.time.LocalDate;

import SmartUtilities.Enums.Gender;

public class Customer implements ICustomer {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    @Override
    public String getFirstName() {
        
        return firstName;
    }
    
    @Override
    public String getLastName() {
        return lastName;
    }
   
    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
            
    }
        
    @Override
    public void setBirthDate(LocalDate birtDate) {
       this.birthDate = birtDate;
    }


    @Override
    public void setGender(Gender gender) {
       this.gender = gender;
        
    }
}
