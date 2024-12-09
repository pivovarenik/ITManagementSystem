package dao;

import entities.Project;

public class ProjectDao extends GenericDAOImpl<Project, Integer>{
    public ProjectDao() {
        super(Project.class);
    }

}
