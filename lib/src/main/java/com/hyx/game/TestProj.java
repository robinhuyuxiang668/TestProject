package com.hyx.game;


public class TestProj {
    public static void main(String[] args) {
        //多试几次，看过程和结果如何
        for(int i = 1 ; i <= 20 ;i++){
            System.out.println("=======================第"+i+"次比赛=======================");
            GameManage gm = new GameManage();
            gm.play();
        }

    }
}
