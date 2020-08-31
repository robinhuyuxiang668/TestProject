package com.hyx.test.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 游戏业务逻辑处理
 */
public class GameManage {
    private List<ToothPick> mTpList1;//放第一行牙签列表 3根
    private List<ToothPick> mTpList2;//放第二行牙签列表 5根
    private List<ToothPick> mTpList3;//放第三行牙签列表 7根
    private Map<Integer ,List<ToothPick>> mTpMap;//放置所有牙签行列表的键值对,key为第几行，value是对应行的牙签列表

    public static final String A_PLAYER = "A";
    public static final String B_PLAYER = "B";

    public GameManage() {
        init();
    }

    private void init(){
        mTpList1 = new ArrayList<>();
        mTpList2 = new ArrayList<>();
        mTpList3 = new ArrayList<>();
        mTpMap = new HashMap<>();

        generateToothPicksByLine(mTpList1 ,1,3);
        generateToothPicksByLine(mTpList2 ,2,5);
        generateToothPicksByLine(mTpList3 ,3,7);
    }


    /**
     * 每行生产牙签
     * @param tpList ：第几行列表
     * @param linePosition ：从第1行开始 1,2,3行一共
     * @param generateCount：每行生产几个牙签
     */
    private void generateToothPicksByLine(List<ToothPick> tpList, int linePosition, int generateCount){
        for(int i = 1 ; i <= generateCount ;i++){
            ToothPick tp = new ToothPick(linePosition);
            tpList.add(tp);
        }

        mTpMap.put(linePosition ,tpList);
    }

    public void play(){
        while(getAllLineCount() > 0){
            boolean aPick = pickToothPickByPlayer(A_PLAYER);
            if(!aPick){
                break;
            }

            boolean bPick = pickToothPickByPlayer(B_PLAYER);
            if(!bPick){
                break;
            }
        }
    }

    /**
     * A或B选牙签
     * @param player:两个选手，用A或B标记
     * @return: 输了返回false,正常选完返回true
     */
    private boolean pickToothPickByPlayer(String player){
        int pickLine = getPickLine();
        List<ToothPick> list =  mTpMap.get(pickLine);
        int pickCount = getPickCountByLine(pickLine);
        for (int i = 1 ;i <= pickCount ;i++){
            ToothPick tp = list.remove(0);
            if(getAllLineCount() == 0){
                System.out.println(player+"输了,拿了最后一根");
                return false;
            }
        }
        System.out.println(player+"在第"+pickLine+"行取走"+pickCount+"根牙签后 ， 第一行还有"+mTpList1.size()+"个，第二行还有"+mTpList2.size()+"个，第三行还有"+mTpList3.size());
        return true;
    }

    /**
     * 获取三行还未被拿走的牙签总数
     * @return
     */
    private int getAllLineCount(){
        return mTpList1.size()+mTpList2.size()+mTpList3.size();
    }

    /**
     * 从第几行取 ,此次是模拟随机数产生[1,3]随机数
     */
    private int getPickLine(){
        Random rand = new Random();
        //产生[1,3]间随机数
        int line = rand.nextInt(3)+1;
        if(mTpMap.get(line).size() == 0){
            return getPickLine();
        }

        return line;
    }

    /**
     * 从第line行拿掉几根牙签，拿掉数量随机产生
     * @param line:第几行
     * @return: 从该行拿走牙签的数量
     */
    private int getPickCountByLine(int line){
        int size = mTpMap.get(line).size();

        Random rand = new Random();
        //产生[1,size]间随机数
        return rand.nextInt(size)+1;
    }
}
