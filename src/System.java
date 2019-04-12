import java.util.ArrayList;

public class System {

    ArrayList<Person> arrayListPerson =new ArrayList<>();
    ArrayList<Common> arrayListCommon =new ArrayList<>();

    public ArrayList<Person> getArrayListPerson() {
        return arrayListPerson;
    }

    public void setArrayListPerson(Person person) {
        arrayListPerson.add(person);
    }

    public ArrayList<Common> getArrayListCommon() {
        return arrayListCommon;
    }

    public void setArrayListCommon(ArrayList<Common> arrayListCommon) {
        this.arrayListCommon = arrayListCommon;
    }
}
