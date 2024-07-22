package db;

/**
 *
 * @author Reza Sabzi
 */
public class Instructor {
    
    private int insCode;
    private String firstName;
    private String lastName;
    private byte gender;

    public Instructor() {
    }
    
    

    public Instructor(int insCode, String firstName, String lastName, byte gender) {
        this.insCode = insCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public int getInsCode() {
        return insCode;
    }

    public void setInsCode(int insCode) {
        this.insCode = insCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }
    
    
    
    
    
    
}
