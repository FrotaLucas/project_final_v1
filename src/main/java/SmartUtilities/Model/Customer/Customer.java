package SmartUtilities.Model.Customer;

import java.time.LocalDate;
import java.util.UUID;

import SmartUtilities.Enums.Gender;
import SmartUtilities.Shared.IID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Customer implements ICustomer, IID {

    private Optional<Integer> Id;

    private UUID uuid;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    // Constructor
    @JsonCreator
    public Customer(@JsonProperty("Id") Integer Id, @JsonProperty("firstName") String firstName,
                    @JsonProperty("lastName") String lastName, @JsonProperty("birthDate") String birthDate,
                    @JsonProperty("gender") String gender) {
        this.Id = Optional.ofNullable(Id);
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = LocalDate.parse(birthDate);
        this.gender = Gender.valueOf(gender.toUpperCase()); // parametro gender eh uma string que vamos converter
        //para um enum. Se for D eh 0 e M eh 1...
    }

    public Optional<Integer> getId()
    {
        return Id;
    }

    @Override
    public UUID getUuid() {
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
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //Instr. for  Jackson JsonFormatter
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    public void setId(Integer Id)
    {
        //If null is passed, Id can be null
        this.Id = Optional.ofNullable(Id);
    }

    @Override
    public void setUuid(UUID uuid) {
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
