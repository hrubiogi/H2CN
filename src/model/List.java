package model;

import java.util.ArrayList;

public class List<T>
{

    protected ArrayList<T> list;

    //constructor
    public List()
    {
        list = new ArrayList<>();
    }

    public ArrayList<T> getList()
    {
        return list;
    }

    public void add(T t)
    {
        getList().add(t);
    }


}