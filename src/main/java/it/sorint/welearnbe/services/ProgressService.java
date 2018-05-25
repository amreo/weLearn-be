package it.sorint.welearnbe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.sorint.welearnbe.repository.ProgressRepository;
import it.sorint.welearnbe.repository.entity.ProgressBE;
import it.sorint.welearnbe.repository.entity.ProgressProjectBE;
import it.sorint.welearnbe.repository.entity.ProjectBE;

@Service
public class ProgressService {

	@Autowired
	ProgressRepository progressRepository;
	@Autowired
	ProjectService projectService;
	@Autowired
	FileService fileService;
	
	
	public List<ProgressBE> getProgresses(String username) {
		//FIXME: limit visibility
		return progressRepository.findAll();
	}
	
	public Optional<ProgressBE> getProgress(String student, String username) {
		//FIXME: limit visibility
		return Optional.ofNullable(progressRepository.findOne(student));
	}
	
	public Optional<ProgressProjectBE> getProgressProject(String student, UUID projectID, String username) {
		Optional<ProgressBE> progress = getProgress(student, username);
		if (!progress.isPresent())
			return Optional.empty();
		
		return progress.get().getProjects().stream().filter(pr -> pr.getId()==projectID).findFirst();
	}

	public ProgressBE getOrCreateProgress(String student) {
		ProgressBE fromRepository = progressRepository.findOne(student);
		if (fromRepository == null) {
			ProgressBE progress = new ProgressBE();
			progress.setStudent(student);
			progress.setCourses(new ArrayList<>());
			progress.setProjects(new ArrayList<>());
			progressRepository.save(progress);
			return progress;	
		} else {
			return fromRepository;
		}
	}
	
	public Optional<ProgressProjectBE> getOrCreateProgressProject(String student, UUID projectID) {
		ProgressBE progress = getOrCreateProgress(student);
		Optional<ProgressProjectBE> fromRepository = progress.getProjects().stream().filter(pr -> pr.getId() == projectID).findFirst();
		if (fromRepository.isPresent()) {
			return Optional.of(fromRepository.get());
		} else {
			//Get the project
			Optional<ProjectBE> project = projectService.getProject(projectID);
			if (project.isPresent()) {
				//Create a new progressProject
				ProgressProjectBE newProgressProject = new ProgressProjectBE();
				newProgressProject.setId(projectID);
				newProgressProject.setVersion(project.get().getVersion());
				newProgressProject.setFiles(fileService.cloneFiles(project.get().getFiles()));
				progress.getProjects().add(newProgressProject);
				progressRepository.save(progress);
				return Optional.of(newProgressProject);
			} else {
				return Optional.empty();
			}
		}
	}
	
	public Optional<byte[]> getFileOfProgressProject(String student, UUID projectID, String filename, String name) {
		//FIXME: security!
		//Get the progress
		Optional<ProgressProjectBE> progressProject = getOrCreateProgressProject(student, projectID);
		if (progressProject.isPresent()) {
			return fileService.loadFileByFilenameAndAnyOfIds(progressProject.get().getFiles(), filename);
		} else {
			return Optional.empty();
		}
	}
}
