package org.example.managers;

import org.example.Address;
import org.example.Coordinates;
import org.example.Organization;
import org.example.OrganizationType;
import org.example.handlers.HashHandler;

import java.sql.*;
import java.util.Hashtable;

import static org.example.system.Main.LOGGER;

public class DataBaseManager {
    private static String URL;
    private static String username;
    private static String password;
    private static Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String GET_USER_BY_USERNAME = "SELECT id, login, password FROM users WHERE login = ?";
    private static final String GET_OWNER_BY_KEY = "SELECT owner_id FROM organization WHERE org_key = ?";
    private static final String INSERT_ORGANIZATION = "INSERT INTO organization (owner_id, org_key, name, x, y, creationdate, " +
            "annualturnover, fullname, employeescount, organizationtype, street, zipcode) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ORGANIZATION_BY_ID = "UPDATE organization SET owner_id = ?, org_key = ?, name = ?, x = ?, y = ?, creationdate = ?, " +
            "annualturnover = ?, fullname = ?, employeescount = ?, organizationtype = ?, street = ?, zipcode = ? WHERE id = ?";
    private static final String REMOVE_ORGANIZATION = "DELETE FROM organization WHERE org_key = ?";
    private static final String REMOVE_ALL_USER_ORGANIZATION = "DELETE FROM organization WHERE owner_id = ?";
    private static DataBaseManager instance = null;

    public DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public static void connectToDataBase() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            LOGGER.info("Connection is ready");
        } catch (SQLException e) {
            LOGGER.warn("Error while connecting to database");
            System.exit(-1);
        }
    }

    // получение id владельца по ключу
    public static int getOwnerId(String key) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_OWNER_BY_KEY);
            getStatement.setString(1, key);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("owner_id");
            }
            return -2;
        } catch (Exception e) {
            return -2;
        }
    }

    // получение id пользователя по логину
    public static int getUserId(String login) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_USER_BY_USERNAME);
            getStatement.setString(1, login);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    // корректность имени + пароля
    public static boolean checkUser(String login, String password) {
        try {
            PreparedStatement getStatement = connection.prepareStatement(GET_USER_BY_USERNAME);
            getStatement.setString(1, login);
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(HashHandler.encryptString(password));
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // регистрация пользователя
    public static boolean insertUser(String username, String password) {
        try (PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST)) {
            addStatement.setString(1, username);
            addStatement.setString(2, HashHandler.encryptString(password));
            addStatement.executeUpdate();
            addStatement.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Couldn't register user. Reason: " + e.getMessage());
            return false;
        }
    }

    public static boolean insertOrganization(Organization organization, String login, String key) {
        int userId = getUserId(login);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORGANIZATION)) {
            statement.setInt(1, userId);
            statement.setString(2, key);
            insertOrganizationDataIntoStatement(organization, statement);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Couldn't add organization. Reason: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateOrganizationById(int id, String key, String login, Organization organization) {
        int userId = getUserId(login);
        if (userId == getOwnerId(key)) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORGANIZATION_BY_ID)) {
                statement.setInt(1, userId);
                statement.setString(2, key);
                insertOrganizationDataIntoStatement(organization, statement);
                statement.setInt(13, id);
                statement.executeUpdate();
                statement.close();
                return true;
            } catch (SQLException e) {
                LOGGER.error("Couldn't update organization. Reason: " + e.getMessage());
                return false;
            }
        } else return false;
    }

    public static boolean removeOrganizationByKey(String login, String key) {
        int userId = getUserId(login);
        if (userId == getOwnerId(key)) {
            try (PreparedStatement statement = connection.prepareStatement(REMOVE_ORGANIZATION)) {
                statement.setString(1, key);
                statement.executeUpdate();
                statement.close();
                return true;
            } catch (SQLException e) {
                LOGGER.error("Couldn't remove organization. Reason: " + e.getMessage());
                return false;
            }
        } else return false;
    }

    // вставить организацию в запрос
    private static void insertOrganizationDataIntoStatement(Organization organization, PreparedStatement statement) {
        try {
            statement.setString(3, organization.getName());
            statement.setInt(4, organization.getCoordinates().getX());
            statement.setInt(5, organization.getCoordinates().getY());
            statement.setDate(6, Date.valueOf(organization.getCreationDate()));
            statement.setDouble(7, organization.getAnnualTurnover());
            statement.setString(8, organization.getFullName());
            statement.setInt(9, organization.getEmployeesCount());
            statement.setString(10, organization.getType().toString());
            statement.setString(11, organization.getPostalAddress().getStreet());
            statement.setString(12, organization.getPostalAddress().getZipCode());
        } catch (SQLException e) {
            LOGGER.error("Couldn't insert organization data into statement. Reason: " + e.getMessage());
        }
    }

    // создать организацию по результату запроса
    private static Organization extractOrganizationFromEntry(ResultSet rs) throws SQLException {
        Organization organization = new Organization();
        organization.setId((long) rs.getInt("id"));
        organization.setName(rs.getString("name"));
        organization.setCoordinates(new Coordinates(rs.getInt("x"), rs.getInt("y")));
        organization.setCreationDate(rs.getDate("creationdate").toLocalDate());
        organization.setAnnualTurnover(rs.getDouble("annualturnover"));
        organization.setFullName(rs.getString("fullname"));
        organization.setEmployeesCount(rs.getInt("employeescount"));
        organization.setType(OrganizationType.valueOf(rs.getString("organizationtype")));
        organization.setPostalAddress(new Address(rs.getString("street"), rs.getString("zipcode")));
        return organization;
    }

    // получить таблицу
    public static Hashtable<String, Organization> getDataFromDatabase() {
        Hashtable<String, Organization> organizations = new Hashtable<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM organization");
            while (rs.next()) {
                try {
                    Organization organization = extractOrganizationFromEntry(rs);
                    organizations.put(rs.getString("org_key"), organization);
                } catch (Exception e) {
                    LOGGER.error("Invalid route entry in DB. Reason: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't load data from DB. Reason: " + e.getMessage());
            System.exit(-1);
        }
        return organizations;
    }

    public static boolean clear(String login) {
        int userId = getUserId(login);
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_ALL_USER_ORGANIZATION)){
            statement.setInt(1, userId);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e){
            LOGGER.error("Couldn't clear user data from DB. Reason: " + e.getMessage());
        }
        return false;
    }


    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        DataBaseManager.URL = URL;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataBaseManager.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBaseManager.password = password;
    }
}
