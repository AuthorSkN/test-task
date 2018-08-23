package com.haulmont.testtask.data;

import com.haulmont.testtask.data.entity.Specialization;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

public class DataBasePreparerTest {

    @Test
    public void create_database_if_absent() {
        //DataBasePreparer preparer = new DataBasePreparer("database/");
        File folderWithDB = new File("database");
        System.out.println(folderWithDB.delete());
    }

    private static SchemaExport getSchemaExport() {

        SchemaExport export = new SchemaExport();
        // Script file.
        File outputFile = new File("exportScript.sql");
        String outputFilePath = outputFile.getAbsolutePath();

        System.out.println("Export file: " + outputFilePath);

        export.setDelimiter(";");
        export.setOutputFile(outputFilePath);

        // No Stop if Error
        export.setHaltOnError(false);
        //
        return export;
    }

    public void createDB() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        SchemaExport export = getSchemaExport();
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);

        SchemaExport.Action action = SchemaExport.Action.CREATE;
        //
        export.execute(targetTypes, action, metadata);
    }

    @Test
    public void test_default_filling() {
       /* createDB();
        Specialization specialization = new Specialization();
        specialization.setSpecializationId(new Long(0));
        specialization.setName("Хирург");

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(specialization);
        session.getTransaction().commit();
        session.close();*/

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session;
        createDB();
        session = factory.openSession();
        session.beginTransaction();
        List specializations = session.createQuery("From Specialization").list();
        session.getTransaction().commit();
        session.close();

        System.out.println(specializations.size());
    }
}
