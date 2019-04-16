public class Common {
    private String id;
    private String name;
    private boolean isWrong;

    public boolean isWrong() {
        return isWrong;
    }

    public void setWrong(boolean wrong) {
        isWrong = wrong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommon(String id, String name, boolean isWrong){
        this.id=id;
        this.name=name;
        this.isWrong=isWrong;

    }

}
