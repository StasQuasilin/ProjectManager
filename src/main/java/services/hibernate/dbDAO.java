package services.hibernate;

import entity.project.Project;
import entity.user.User;

import java.util.List;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public interface dbDAO {

    List<Project> getProjectsByOwner(User user);
    List<Project> getProjectsByMembers(User user);
    <T> T getObjectById(Class<T> tClass, Object id);
}
