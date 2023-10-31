package controller;
import model.Data;

import java.time.LocalDate;

public class Controller
{
    private Data data;

    public Data getData()
    {
        return data;
    }

    //constructor
    public Controller()
    {
        data = new Data();
    }
}
