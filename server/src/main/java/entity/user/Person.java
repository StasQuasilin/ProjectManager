package entity.user;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.JsonAble;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 14.02.2020.
 */
@Entity
@Table(name = Keys.PERSON)
public class Person extends JsonAble implements Keys {
    private int id;
    private String surname;
    private String forename;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = SURNAME)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = FORENAME)
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(VALUE, surname + SPACE + forename);
        object.put(SURNAME, surname);
        object.put(FORENAME, forename);
        return object;
    }

    @Transient
    public String getValue() {
        return forename + SPACE + surname;
    }
}
