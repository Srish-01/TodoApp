package com.example.db;

import com.example.core.Task;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(TaskMapper.class)
public interface TaskDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS tasks (id BIGINT AUTO_INCREMENT PRIMARY KEY, description VARCHAR(255), start_date DATE, target_date DATE, status VARCHAR(10))")
    void createTaskTable();

    @SqlUpdate("INSERT INTO tasks (description, start_date, target_date, status) VALUES (:description, :startDate, :targetDate, :status)")
    void insert(@BindBean Task task);

    @SqlQuery("SELECT * FROM tasks WHERE id = :id")
    Optional<Task> findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM tasks")
    List<Task> findAll();

    @SqlUpdate("UPDATE tasks SET description = :description, start_date = :startDate, target_date = :targetDate, status = :status WHERE id = :id")
    void update(@BindBean Task task);

    @SqlUpdate("DELETE FROM tasks WHERE id = :id")
    void deleteById(@Bind("id") long id);
}
