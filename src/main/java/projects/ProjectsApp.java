package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	
	private List<String> operations = List.of(
			"1. Add a project",
			"2. List Projects",
			"3. Select a Project",
			"4. Select a project to Upadte",
			"5 Select a project to delete"
			
			);
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
			

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new ProjectsApp().processUserSelections();
		
		
		
	}
			// takes the users selection and then allows the switch statement to make our choice.
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				case 2:
					listProjects();
					break;
				case 3:
					selectProject();
					break;
				case 4:
					updateProjectDetails();
					break;
				case 5:
					deleteProject();
					break;
					
					
					default:
						System.out.println("\n" + selection + " is not a valid selection. Please try again.");
				}
			}
			catch(Exception e) {
				System.out.println("\n Error: " + e + " Please Try Again.");
			}
		}
		
		
	}
	
	
	private void deleteProject() {
		listProjects();
		
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
		projectService.deleteProject(projectId);
		System.out.println("project " + projectId + " was deleted succesfully.");
		
		if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			curProject = null;
		}
		
		
		
	}
	private void updateProjectDetails() {
				if(Objects.isNull(curProject)) {
					System.out.println("/nPlease select a project.");
					return;
				}
				
				String projectName =
						getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
				
				BigDecimal estimatedHours = 
						getDecimalInput(" Enter estimated hours of work [" + curProject.getEstimatedHours() + "]" );
				
				BigDecimal actualHours = 
						getDecimalInput(" Enter estimated hours of work [" + curProject.getActualHours() + "]" );
				
				Integer difficulty =
						getIntInput("Enter the project difficulty (1 - 5) [" + curProject.getDifficulty());
				
				String notes =
						getStringInput("enter Notes for the project [" + curProject.getNotes());
				
				Project project = new Project();
				
				project.setProjectId(curProject.getProjectId());
				project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
				
				project.setEstimatedHours(
						Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
				
				project.setActualHours(
						Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
				
				project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
				project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
				
				projectService.modifyProjectDetail(project);
				
				curProject = projectService.fetchProjectsById(curProject.getProjectId());
				
				
				
			}
	
	
	private void listProjects() {
				List<Project> projects = projectService.fetchAllProjects();
				
				System.out.println("\nProjects: ");
				
				projects.forEach(project -> System.out.println(" " + project.getProjectId() + ": " + project.getProjectName()));
				
				
			}
	
	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a Project Id to Select a Project");
		
		curProject = null;
		
		curProject = projectService.fetchAllProjectsById(projectId);
		
		
	}
	
	
	// here we create the project and get it ready to upload to the database.
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours: ");
		BigDecimal actualHours = getDecimalInput("Enter the Actual hours for the project: ");
		Integer difficulty = getIntInput("Enter the project difficulty(1 - 5)");
		String notes = getStringInput("Enter any notes for the project");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = ProjectService.appProject(project);
		System.out.println("You have Successfully created project: " + dbProject);
		
		
	}
	
	
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number");
		}
		
	}
	
	
	
		// method gets user selection from the menu
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu Selection please");
		
		return Objects.isNull(input) ? -1 : input;
	}
	
	
		// creates the menus operations available to the user
	private void printOperations() {
		System.out.println("\n These are the available selections. Press the Enter key to quit");
		
		operations.forEach(line -> System.out.println(" " + line));
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project");
		}
			else {
				System.out.println("\nYou are working with project: " + curProject);
			}
		}
	
	
	
	// this handles the string input and converts it to a int and then checks to make sure it is a valid int.
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
		
		
	
		
												}
	//gets the string from the menu
	private String getStringInput(String prompt) {
		
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
