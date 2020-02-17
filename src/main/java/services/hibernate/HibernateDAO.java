package services.hibernate;

import constants.Keys;
import entity.project.Project;
import entity.project.ProjectMember;
import entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class HibernateDAO implements dbDAO, Keys {
    private Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Project> getProjectsByOwner(User user) {
        return hibernator.query(Project.class, OWNER, user.getId());
    }

    @Override
    public List<Project> getProjectsByMembers(User user) {
        List<Project> result = new ArrayList<>();
        for (ProjectMember member : hibernator.query(ProjectMember.class, MEMBER, user)){
            result.add(member.getProject());
        }
        return result;
    }

    @Override
    public <T> T getObjectById(Class<T> tClass, Object id) {
        return hibernator.get(tClass, ID, id);
    }
}
