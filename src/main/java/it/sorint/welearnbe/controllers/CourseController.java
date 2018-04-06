package it.sorint.welearnbe.controllers;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sorint.welearnbe.controllers.entity.CourseFE;
import it.sorint.welearnbe.controllers.entity.CourseWithUnitsFE;
import it.sorint.welearnbe.controllers.entity.UnitFE;
import it.sorint.welearnbe.controllers.entity.UnitWithWidgetsFE;
import it.sorint.welearnbe.controllers.entity.WidgetFE;
import it.sorint.welearnbe.services.SessionService;

@RestController
@RequestMapping("/api")
public class CourseController {

	@Autowired
	private SessionService sessionService;
	
	@GetMapping("/courses")
	public ResponseEntity<List<CourseFE>> getCourses() {
		return null;
	}
	
	@GetMapping("/courses/{courseID}")
	public ResponseEntity<CourseWithUnitsFE> getCourse(Principal principal, @PathVariable("courseID") UUID courseID) {
		if (sessionService.isStudentOfCourse(principal.getName(), courseID) | sessionService.isTeacherOf(principal.getName(), courseID)) {
			//TODO: return the correct value! not null
			return ResponseEntity.ok(null);	
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@GetMapping("/courses/{courseID}/units")
	public ResponseEntity<List<UnitFE>> getUnits(Principal principal, @PathVariable("courseID") UUID courseID) {
		if (sessionService.isStudentOfCourse(principal.getName(), courseID) | sessionService.isTeacherOf(principal.getName(), courseID)) {
			//TODO: return the correct value! not null
			return ResponseEntity.ok(null);	
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@GetMapping("/courses/{courseID}/units/{unitID}")
	public ResponseEntity<UnitWithWidgetsFE> getUnit(Principal principal, @PathVariable("courseID") UUID courseID, @PathVariable("unitID") UUID unitID) {
		if (sessionService.isStudentOfCourse(principal.getName(), courseID) | sessionService.isTeacherOf(principal.getName(), courseID)) {
			//TODO: return the correct value! not null
			return ResponseEntity.ok(null);	
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@GetMapping("/courses/{courseID}/units/{unitID}/widgets")
	public ResponseEntity<List<WidgetFE>> getWidget(Principal principal, @PathVariable("courseID") UUID courseID, @PathVariable("unitID") UUID unitID) {
		if (sessionService.isStudentOfCourse(principal.getName(), courseID) | sessionService.isTeacherOf(principal.getName(), courseID)) {
			//TODO: return the correct value! not null
			return ResponseEntity.ok(null);	
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
}