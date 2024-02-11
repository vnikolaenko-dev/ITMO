package data;

import data.generators.IdGenerator;
import managers.Validator;

import java.time.LocalDate;

public class Organization {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private String fullName; //Поле не может быть null
    private Integer employeesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле не может быть null

    public Organization(){
        this.id = IdGenerator.generateId();
        this.name = null;
        this.coordinates = null;
        this.creationDate = LocalDate.now();
        this.annualTurnover = null;
        this.fullName = null;
        this.employeesCount = null;
        this.type = null;
        this.postalAddress = null;
    }

    public Organization(Long id){
        this.id = id;
        this.name = null;
        this.coordinates = null;
        this.creationDate = LocalDate.now();
        this.annualTurnover = null;
        this.fullName = null;
        this.employeesCount = null;
        this.type = null;
        this.postalAddress = null;
    }

    public Organization(String[] data) throws Exception{
        // Проверяем корректность всех значений
        try {
            Validator.idIsOk(data[1]);
            Validator.inputIsNotEmpty(data[2], "NAME");
            Validator.coordinatesIsOk(data[3]);
            Validator.coordinatesIsOk(data[4]);
            Validator.inputIsNotEmpty(data[5], "DATE");
            Validator.annualTurnoverIsOk(data[6]);
            Validator.inputIsNotEmpty(data[7], "FullName");
            Validator.employeesCountIsOk(data[8]);
            Validator.typeIsOk(data[9]);
            Validator.inputIsNotEmpty(data[10], "STREET");
            Validator.inputIsNotEmpty(data[11], "ziCode");
        } catch (Exception e) {
            throw e;
        }

        this.id = Long.parseLong(data[1]);
        this.name = data[2];
        this.coordinates = new Coordinates(Integer.parseInt(data[3]), Integer.parseInt(data[4]));
        this.creationDate = LocalDate.parse(data[5]);
        this.annualTurnover = Double.parseDouble(data[6]);
        this.fullName = data[7];
        this.employeesCount = Integer.parseInt(data[8]);
        this.type = OrganizationType.valueOf(data[9]);
        this.postalAddress = new Address(data[10], data[11]);
    }

    public String toXML(){
        return "id=\"" + id + "\"" +
                " name=\"" + name + "\"" +
                " x=\"" + coordinates.getX() + "\"" +
                " y=\"" + coordinates.getY() + "\"" +
                " creationDate=\"" + creationDate + "\"" +
                " annualTurnover=\"" + annualTurnover + "\"" +
                " fullName=\"" + fullName +"\"" +
                " employeesCount=\"" + employeesCount + "\"" +
                " type=\"" + type + "\"" +
                " street=\"" + postalAddress.getStreet() + "\"" +
                " zipCode=\"" + postalAddress.getZipCode() + "\"";
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", annualTurnover=" + annualTurnover +
                ", fullName='" + fullName + '\'' +
                ", employeesCount=" + employeesCount +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }
    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }
}
