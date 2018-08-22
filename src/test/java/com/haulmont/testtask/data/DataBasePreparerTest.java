package com.haulmont.testtask.data;

import org.junit.Test;

public class DataBasePreparerTest {

    @Test
    public void create_database_if_absent() {
        DataBasePreparer preparer = new DataBasePreparer();
        preparer.createDB();
        preparer.filling();
    }

    @Test
    public void test_default_filling() {
        DataBasePreparer preparer = new DataBasePreparer();
        preparer.show();
    }
}
