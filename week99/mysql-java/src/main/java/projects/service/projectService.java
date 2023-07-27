package projects.service;
import projects.dao.ProjectDao;

import projects.entity.Project;


public class projectService {
	private ProjectDao ProjectDao = new ProjectDao();

  
 public  Project addProject(Project project) {
        return ProjectDao.insertProject(project);
}
}