package com.example.resources;

import com.example.core.Task;
import com.example.db.TaskDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GET
    @UnitOfWork
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getTask(@PathParam("id") long id) {
        Optional<Task> task = taskDAO.findById(id);
        if (task.isPresent()) {
            return Response.ok(task.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTask(Task task) {
        task.setStatus("TODO");
        taskDAO.insert(task);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") long id, Task updatedTask) {
        try {
            Optional<Task> existingTask = taskDAO.findById(id);
            if (existingTask.isPresent()) {
                updatedTask.setId(id);  // Ensure the updated task has the correct ID
                taskDAO.update(updatedTask);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Task with id " + id + " not found")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to update task with id: " + id + ". " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteTask(@PathParam("id") long id) {
        try {
            Optional<Task> existingTask = taskDAO.findById(id);
            if (existingTask.isPresent()) {
                taskDAO.deleteById(id);
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Task with id " + id + " not found")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete task with id: " + id + ". " + e.getMessage())
                    .build();
        }
    }
}
