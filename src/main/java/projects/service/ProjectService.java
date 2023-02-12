package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;


public class ProjectService {
	
	private static ProjectDao projectDao = new ProjectDao();

	public static Project appProject(Project project) {
		return projectDao.insertProject(project);
	}

	public List<Project> fetchAllProjects() {
		
		return projectDao.fetchAllProjects();
	}

	public Project fetchAllProjectsById(Integer projectId) {
		
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID = " + projectId + 
				" does not exist!"));
		
		
		
	}

	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID " + projectId + " does not exist!");
		}
		
	}

	public void modifyProjectDetail(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID " + project.getProjectId() + " does not exist!");
		}
		
	}

	public Project fetchProjectsById(Integer projectId) {
		// TODO Auto-generated method stub
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID = " + projectId + 
				" does not exist!")) ;
	}

	


}

