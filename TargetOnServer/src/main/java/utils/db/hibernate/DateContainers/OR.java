package utils.db.hibernate.DateContainers;

public class OR {
    private Object [] objects;

    public OR(Object ... objects){
        this.objects = objects;
    }

    public Object[] getObjects() {
        return objects;
    }

    public int length() {
        return objects.length;
    }
}
