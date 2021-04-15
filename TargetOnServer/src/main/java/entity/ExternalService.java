package entity;

import javax.persistence.*;

@Entity
@Table(name = "external_services")
public class ExternalService {
    private int id;
    private ServiceType type;
    private String api;
    private String key;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public ServiceType getType() {
        return type;
    }
    public void setType(ServiceType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "_api")
    public String getApi() {
        return api;
    }
    public void setApi(String api) {
        this.api = api;
    }

    @Basic
    @Column(name = "_key")
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
