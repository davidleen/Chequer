package com.david.game.control;

/**
 * Created by davidleen29 on 2015/3/13.
 */
public class Board {



    public int[][] cells;
    public static final int LENGTH=17;

    public static final int TOTAL_LENGTH=LENGTH*LENGTH;


    public int[][] players;
    public Board() {


        cells=new int[LENGTH][LENGTH];





    }

    /**
     * init ，set each point;
     */
    public void init()
    {





        int MAX_ROW=13;
        //初始化
        for(int i=0;i<cells.length;i++)

            for (int j = 0; j <cells[i].length; j++)
            {
                cells[i][j]=-1;
        }


        //记录所有棋盘内的点
        for(int i=0;i<MAX_ROW;i++)
        {

            for(int j=0;j<i+1;j++)
            {
                cells[i][j+4]=6;
                cells[j+4][i]=6;
            }


        }





        //记录棋手位置
        players=new int[6][10];
        //记录六个角落棋子  逆时针
        //player 1
        int index=0;
        int sizePerPlayer=10;
        int z=0;
        int playerIndex;
        int[] playerIndexCounter=new int[6];
        for(int i=0;i<4;i++)
        {

            for (int j = i; j <4 ; j++) {
                //player 1
                playerIndex=0;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i+4,j);
                cells[i+4][j]=playerIndex;

             //player 5
                playerIndex=4;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i+4,j+9);
                cells[i+4][j+9]=playerIndex;



                //player 3
                playerIndex=2;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i+13,j+9);
                cells[i+13][j+9]=playerIndex;
            }
            
        }

        for(int i=0;i<4;i++)
        {

            for (int j = 0; j <=i ; j++) {

                //player 6
                playerIndex=5;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i ,j+4);
                cells[i ][j+4]=playerIndex;

                //player 2
                playerIndex=1;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i+9,j+4);
                cells[i+9][j+4]=playerIndex;



                //player 4
                playerIndex=3;
                players[playerIndex][playerIndexCounter[playerIndex]++]=combineXY(i+9,j+13);
                cells[i+9][j+13]=playerIndex;
            }

        }



    }


    public static  int combineXY(int x, int y)
    {
        return x<<4+y;
    }

    public static  int[] decoupleXY(int combineValue)
    {
        return new int[]{combineValue>>8,combineValue&0xff00};
    }





}
