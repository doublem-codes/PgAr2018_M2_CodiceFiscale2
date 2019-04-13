public class Person {

    private String firstName ;
    private String lastName ;
    private String sex ;
    private String common;
    private Date date ;
    private String fiscalCode;

    public void setPerson(String firstName, String lastName , String sex , String common, Date date ) {
        this.firstName = firstName.toUpperCase();
        this.lastName  = lastName.toUpperCase() ;
        this.sex=sex;
        this.common = common.toUpperCase().trim();
        this.date = date;
    }

    public void setFiscalCode(String fiscalCode){
        this.fiscalCode = fiscalCode;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getFistName() {
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

    public Date getDate() {
        return date;
    }


    public String calcFiscalCode(){
        String a = "inserirecodice";
        return a;
    }
}
