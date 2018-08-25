package com.haulmont.testtask.data;

import com.haulmont.testtask.data.entity.RecipePriority;
import com.haulmont.testtask.data.entity.Specialization;
import com.haulmont.testtask.exceptions.MedicamentsSystemException;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.SchemaManagementException;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public final class DataBasePreparer extends DAO {

    private final List<String> SPECIALIZATIONS = Arrays.asList("Терапевт", "Хирург", "Дерматолог", "Стоматолог",
                                                                "Гениколог", "Психиатр", "Окулист", "Невролог");

    private final List<String> RECIPE_PRIORITIES = Arrays.asList("Normalem", "Cito", "Statim");

    private final String configDBFilePath;

    public DataBasePreparer(String configDBFilePath) {
        this.configDBFilePath = configDBFilePath;
    }

    private void insertStaticDataInDB() {
        Session activity = beginActivity();
        SPECIALIZATIONS.forEach(specializationName -> activity.save(new Specialization(specializationName)));
        RECIPE_PRIORITIES.forEach(priorityName -> activity.save(new RecipePriority(priorityName)));
        commit(activity);
    }

    private void createDB() throws SchemaManagementException {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure(this.configDBFilePath).build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        SchemaExport export = new SchemaExport();
        export.setHaltOnError(true);
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.STDOUT);
        SchemaExport.Action action = SchemaExport.Action.CREATE;
        export.execute(targetTypes, action, metadata);
    }

    /**
     * Подготавливает базу данных к работе в системе
     * @throws MedicamentsSystemException
     */
    public void prepareDataBase() throws MedicamentsSystemException {
        try {
            createDB();
            insertStaticDataInDB();
        } catch (SchemaManagementException exc) {
            System.out.println("Database is already exists.");
        }
    }

}
