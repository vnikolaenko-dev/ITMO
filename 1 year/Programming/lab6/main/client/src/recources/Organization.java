package recources;


import java.io.*;
import java.time.LocalDate;

/**
 * Модель объекта "Организация"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения. Он подписан под каждым методом поля
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class Organization implements Comparable<Organization>, Serializable{
    private static final long serialVersionUID = 5760575944040770153L;
    /**
     * Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически при помощи IdGenerator
     *
     * @since 1.0
     */
    private Long id;
    /**
     * Название организации
     * Поле не может быть null, Строка не может быть пустой
     *
     * @since 1.0
     */
    private String name;
    /**
     * Координаты
     * Поле не может быть null
     *
     * @see Coordinates
     * @since 1.0
     */
    private Coordinates coordinates;
    /**
     * Дата создания организации
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     *
     * @since 1.0
     */
    private LocalDate creationDate;
    /**
     * Среднее значение годового оборота
     * Поле не может быть null, Значение поля должно быть больше 0
     *
     * @since 1.0
     */
    private Double annualTurnover;
    /**
     * Полное название организации
     * Поле не может быть null
     *
     * @since 1.0
     */
    private String fullName; //Поле не может быть null
    /**
     * Число работников
     * Поле не может быть null, Значение поля должно быть больше 0
     *
     * @since 1.0
     */
    private Integer employeesCount;
    /**
     * Тип организации
     * Поле не может быть null
     *
     * @see OrganizationType
     * @since 1.0
     */
    private OrganizationType type;
    /**
     * Почтовый адрес
     * Поле не может быть null
     *
     * @see Address
     * @since 1.0
     */
    private Address postalAddress; //Поле не может быть null


    /**
     * Базовый конструктор
     *
     * @author vnikolaenko
     * @since 1.0
     */
    public Organization() {
        this.id = 0L;
        this.name = null;
        this.coordinates = null;
        this.creationDate = LocalDate.now();
        this.annualTurnover = null;
        this.fullName = null;
        this.employeesCount = null;
        this.type = null;
        this.postalAddress = null;
    }

    /**
     * Конструктор с заданным id
     *
     * @param id ID для организации
     * @author vnikolaenko
     * @since 1.0
     */
    public Organization(Long id) {
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

    /**
     * Конструктор с данными из списка
     * Метод проверяет корректность всех значений
     *
     * @param data параметры
     * @throws Exception исключение при некорректном значении или исключение при создании
     * @author vnikolaenko
     * @since 1.0
     */
    public Organization(String[] data) throws Exception {
        this.id = 0L;
        this.name = data[1];
        this.coordinates = new Coordinates(Integer.parseInt(data[2]), Integer.parseInt(data[3]));
        this.creationDate = LocalDate.parse(data[4]);
        this.annualTurnover = Double.parseDouble(data[5]);
        this.fullName = data[6];
        this.employeesCount = Integer.parseInt(data[7]);
        this.type = OrganizationType.valueOf(data[8]);
        this.postalAddress = new Address(data[9], data[10]);
    }

    public byte[] obj2Byte() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(this);
        return byteStream.toByteArray();
    }

    public Object byte2Obj(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);
        return objStream.readObject();
    }

    /**
     * Конвертирует данные в строку для XML
     *
     * @return строка для XML
     * @author vnikolaenko
     * @since 1.0
     */
    public String toXML() {
        return "id=\"" + id + "\"" +
                " name=\"" + name + "\"" +
                " x=\"" + coordinates.getX() + "\"" +
                " y=\"" + coordinates.getY() + "\"" +
                " creationDate=\"" + creationDate + "\"" +
                " annualTurnover=\"" + annualTurnover + "\"" +
                " fullName=\"" + fullName + "\"" +
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

    @Override
    public int compareTo(Organization o) {
        return (int) (this.id - o.getId());
    }
}
