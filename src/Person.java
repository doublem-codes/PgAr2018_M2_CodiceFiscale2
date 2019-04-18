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

    /**
     *
     * @param firstName : name of the person
     * @param lastName : last name of the person
     * @param sex : M or F
     * @param common : city of the person
     * @param date : date of the person
     * @param isWrong : true: if some of the upper parameters during the reading are wrong
     */
    public void setPerson(String firstName, String lastName, String sex, String common, Date date, boolean isWrong) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.sex = sex;
        this.common = common;
        this.day = date.getDay();
        this.month = date.getMonth();
        this.year = date.getYear();
        this.isWrong=isWrong;
    }
    public boolean getIsWrong() {
        return isWrong;
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
