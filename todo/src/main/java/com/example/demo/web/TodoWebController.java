package com.example.demo.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.TaskDaoJpql;
import com.example.demo.domain.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TodoService;

@Controller
public class TodoWebController {

	private static final String TASKS = "tasks";

	private static final String REDIRECT_TO = "redirect:/" + TASKS;

	@Autowired
	TodoService todoService;
	@Autowired
	Task task;
	@Autowired
	EntityManagerController entityManagerController;
	@Autowired
	TodoRestController todoRestController;

	@PersistenceContext
	EntityManager entityManager;

	TaskDaoJpql dao;

	@PostConstruct
	public void init() {
		dao = new TaskDaoJpql(entityManager);
	}

	@RequestMapping(value="/tasks")
	@ResponseBody
	public ModelAndView readAllTasks() {
		TaskForm form = createInitialForm();
		ModelAndView modelAndView = toTasksPage();
		modelAndView.addObject("form", form);
		List<Task> tasks = todoService.findAllTasks();
		modelAndView.addObject(TASKS, tasks);
		return modelAndView;
	}

	private ModelAndView toTasksPage() {
		return new ModelAndView(TASKS);
	}

	private  TaskForm createInitialForm() {
		String formSubject = "";
		LocalDate formDeadLine = LocalDate.now();
		Boolean isNewTask = true;
		Boolean hasDone = false;
		return new TaskForm(formSubject, formDeadLine, hasDone, isNewTask);
	}

	@RequestMapping(value="/add")
	@ResponseBody
	public List<Task> createOneTask(TaskForm form) {
		System.out.print("通過「createOneTask」");
		System.out.print(form);

		createTaskFromForm(form);

//		こっちだとカラム名が出ない
//		List<Task> list = entityManagerController.findWhereName(form.getSubject());

//		現状検索結果が2個出るとエラー
//		Task t = todoRestController.getByName(form.getSubject(), form.getDeadLine());

		List<Task> tasks = todoService.findAllTasks();

		return tasks;
	}

//	@RequestMapping(value="/add")
//	@ResponseBody
//	public void createOneTask(TaskForm form) {
//		System.out.print("通過「createOneTask」");
//		createTaskFromForm(form);
//	}

//	@PostMapping(value = "/tasks")
//	public ModelAndView createOneTask(@ModelAttribute TaskForm form) {
//		createTaskFromForm(form);
//		return new ModelAndView(REDIRECT_TO);
//	}

	private void createTaskFromForm(TaskForm form) {
		String subject = form.getSubject();
		LocalDate deadLine = form.getDeadLine();
		Boolean hasDone = form.getHasDone();
		Task task = new Task(subject, deadLine, hasDone);
		todoService.createTask(task);
	}

	@RequestMapping(value="/update")
	@ResponseBody
	public ModelAndView readOneTask(Integer id) {
		Optional<TaskForm> form = readTaskFromId(id);
		if (!form.isPresent()) {
			return new ModelAndView(REDIRECT_TO);
		}
		ModelAndView modelAndView = toTasksPage();
		modelAndView.addObject("taskId", id);
		modelAndView.addObject("form", form.get());
		List<Task> tasks = todoService.findAllTasks();
		modelAndView.addObject(TASKS, tasks);
		return modelAndView;
	}

//	@RequestMapping(value="/update")
//	@ResponseBody
//	public ModelAndView readOneTask(Integer id) {
//		Optional<TaskForm> form = readTaskFromId(id);
//		if (!form.isPresent()) {
//			return new ModelAndView(REDIRECT_TO);
//		}
//		ModelAndView modelAndView = toTasksPage();
//		modelAndView.addObject("taskId", id);
//		modelAndView.addObject("form", form.get());
//		List<Task> tasks = todoService.findAllTasks();
//		modelAndView.addObject(TASKS, tasks);
//		return modelAndView;
//	}

	private Optional<TaskForm> readTaskFromId(Integer id) {
		Optional<Task> task = todoService.findOneTask(id);
		if (!task.isPresent()) {
			return Optional.ofNullable(null);
		}
		String formSubject = task.get().getSubject();
		LocalDate formDeadLine = task.get().getDeadLine();
        Boolean hasDone = task.get().getHasDone();
        Boolean isNewTask = false;
        TaskForm form = new TaskForm(formSubject, formDeadLine, hasDone, isNewTask);
        return Optional.ofNullable(form);
	}

//	@RequestMapping(value="/put")
//	@ResponseBody
//	public ModelAndView updateOneTask(Integer id, TaskForm form) {
//        updateTask(id, form);
//        return new ModelAndView(REDIRECT_TO);
//    }

	@RequestMapping(value="/put")
	@ResponseBody
	public List<Task> updateOneTask(Integer id, TaskForm form) {
		System.out.print("通過「updateTask」");
		System.out.print(form);

        updateTask(id, form);

//		Task t = todoRestController.getById(id);

		List<Task> tasks = todoService.findAllTasks();

        return tasks;
    }

    private void updateTask(Integer id, TaskForm form) {
        String subject = form.getSubject();
        LocalDate deadLine = form.getDeadLine();
        Boolean hasDone = form.getHasDone();
        Task task = new Task(id, subject, deadLine, hasDone);
        todoService.updateTask(task);
    }

	@RequestMapping(value="/delete")
	@ResponseBody
	public void deleteOneTask(Integer id) {
		System.out.print("通過「deleteOneTask」");

        deleteTask(id);
    }

//    @DeleteMapping(value = "/delete")
//    public ModelAndView deleteOneTask(@PathVariable Integer id) {
//        deleteTask(id);
//        return new ModelAndView(REDIRECT_TO);
//    }

    private void deleteTask(Integer id) {
        Optional<Task> task = todoService.findOneTask(id);
        if (task.isPresent()) {
            todoService.deleteTask(id);
        }
    }
}