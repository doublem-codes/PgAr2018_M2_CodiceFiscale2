public class Person {

    private String firstName ;
    private String lastName ;
    private String sex ;
    private String common;
    private String fiscalCode;
    private boolean isWrong;
    private int day;
    private int month;
    private int year;

    public void setPerson(String firstName, String lastName, String sex, String common, Date date, boolean isWrong) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.sex = sex;
        this.common = common;
        this.day = date.getDay();
        this.month = date.getMounth();
        this.year = date.getYear();
        this.isWrong=isWrong;
    }



    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFiscalCode(String code){
        this.fiscalCode =code;
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
