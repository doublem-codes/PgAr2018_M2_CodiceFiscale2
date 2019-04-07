public class Person {

    private String fistName ;
    private String lastName ;
    private Sex sex ;
    private String common;
    private Date date ;
    private String fiscalCode;

    public void setPerson(String fistName, String lastName , Sex sex , String common, Date date ) {
        this.fistName = fistName.toUpperCase();
        this.lastName  = lastName.toUpperCase() ;
        this.sex = sex;
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
        return fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public Sex getSex() {
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
