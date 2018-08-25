package com.haulmont.testtask.data;

import org.junit.Test;

import java.io.File;

public class DataBasePreparerTest {

    private static final String CONFIG_FILE_PATH = "hibernate.cfg.xml";

    @Test
    public void create_database_if_absent() {
        File file = new File("database/Medicaments.script");
        System.out.println(file.delete());
        file = new File("database/Medicaments.tmp");
        System.out.println(file.delete());
        file = new File("database/Medicaments.lck");
        System.out.println(file.delete());
        file = new File("database/Medicaments.log");
        System.out.println(file.delete());
        file = new File("database/Medicaments.properties");
        System.out.println(file.delete());
        DAO.init();
        new DataBasePreparer(CONFIG_FILE_PATH).prepareDataBase();
        DAO.closeDB();
    }
}
