package com.example.todoapp;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;

import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.example.db.TaskDAO;
import com.example.resources.TaskResource;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;



public class TodoApp extends Application<TodoAppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodoApp().run(args);
    }

    @Override
    public String getName() {
        return "TodoApplication";
    }

    @Override
    public void initialize(final Bootstrap<TodoAppConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<TodoAppConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(final TodoAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final TodoAppConfiguration configuration,
                    final Environment environment) {

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "db");
        final TaskDAO taskDAO = jdbi.onDemand(TaskDAO.class);



        environment.jersey().register(new TaskResource(taskDAO));



    }
    /*private void registerMetrics(Environment environment) {
        final MetricRegistry registry = environment.metrics();
        new DropwizardExports(registry).register();
    }*/
}