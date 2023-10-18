package model;

import java.util.ArrayList;

public class List<T>
{

    protected ArrayList<T> list;

    public ArrayList<T> getList()
    {
        return list;
    }

    //constructor
    public List()
    {
        list = new ArrayList<>();
    }


    public int getSize()
    {
        return -1;
        // TO-BE-DONE
    }
    public void add(T t)
    {
        getList().add(t);
    }
    public void delete(T t)
    {
        //list.remove(t);
    }
    //public T getAtPosition(int position)
    {
        //return T;
        // TO-BE-DONE
    }
    public void clear()
    {
        // TO-BE-DONE
    }
    public boolean isEmpty()
    {

        return true;
        // TO-BE-DONE
    }
    //public ArrayList<T> getArrayList()
    {
       // ArrayList<T> arrlist = new ArrayList<>(list);
       // return arrlist;
    }
}