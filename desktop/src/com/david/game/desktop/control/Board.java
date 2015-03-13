package com.david.game.desktop.control;

/**
 * Created by davidleen29 on 2015/3/13.
 */
public class Board {



    public int[] cells;
    public static final int LENGTH=17;

    public static final int TOTAL_LENGTH=LENGTH*LENGTH;
    public Board() {


        cells=new int[TOTAL_LENGTH];





    }

    /**
     * init ，set each point;
     */
    public void init()
    {


        int mid=LENGTH/2;


        for(int i=0;i<13;i++)
        {

            for(int j=0;j<i+1;j++)
            {

                cells[i*LENGTH+j+4]++;

                cells[j*LENGTH+i+4]++;
            }


        }




    }








}
