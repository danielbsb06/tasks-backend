package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setuṕ(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData(){
        Task todo = new Task();
        todo.setTask("Descricao");

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada(){
        Task todo = new Task();
        todo.setDueDate(LocalDate.now().plusDays(-1));
        todo.setTask("Descricao");

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {

        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        todo.setTask("Descricao");

        controller.save(todo);

        Mockito.verify(taskRepo).save(todo);
    }
}
