package tie.backend.model;

public class Address {
    private String street;
    private String postCode;
    private String city;

    public Address(){}
    public Address(String street, String postCode, String city){
        this.street = street;
        this.postCode = postCode;
        this.city = city;
    }

    public String getCity() {
        return city;
    }
    public String getPostCode() {
        return postCode;
    }
    public String getStreet() {
        return street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}
