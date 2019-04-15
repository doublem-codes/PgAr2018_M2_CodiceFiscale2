public class Person {

    private String firstName ;
    private String lastName ;
    private String sex ;

    private String common;
    private int day;
    private int month;
    private int year;
    private String fiscalCode;


    public void Person(String firstName, String lastName, String sex, String common, Date date) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.sex = sex;
        this.common = common;
        this.day = date.getDay();
        this.month = date.getMounth();
        this.year = date.getYear();
    }

    public void Person(String firstName, String lastName, String sex, String common, int day, int month, int year) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.sex = sex;
        this.common = common;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }


    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getCommon() {
        return common;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}
