package SmartUtilities.Model.Customer;

import java.time.LocalDate;
import java.util.UUID;

import SmartUtilities.Enums.Gender;
import SmartUtilities.Shared.IID;

public class Customer implements ICustomer, IID {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    // Constructor
    public Customer(String firstName, String lastName, String birthDate, String gender) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = LocalDate.parse(birthDate);
        this.gender = Gender.valueOf(gender.toUpperCase()); // parametro gender eh uma string que vamos converter
        //para um enum. Se for D eh 0 e M eh 1...
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    //eu preciso de todos esses metodos abaixo ? acho que precisa nao pq o Construtor Customer substitui eles
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
    public void setId(UUID uuid) {
        this.uuid = uuid;
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
