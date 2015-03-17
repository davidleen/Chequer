package com.david.game.control;

/**
 * Created by davidleen29 on 2015/3/13.
 */
public class Board {



    public int[][] cells;
    public static final int LENGTH=17;

    public static final int TOTAL_LENGTH=LENGTH*LENGTH;
    public Board() {


        cells=new int[LENGTH][LENGTH];





    }

    /**
     * init ，set each point;
     */
    public void init()
    {





        int MAX_ROW=13;


        for(int i=0;i<MAX_ROW;i++)
        {

            for(int j=0;j<i+1;j++)
            {
                cells[i][j+4]++;
                cells[j+4][i]++;
            }


        }




    }








}
