package com.hyx.test.game;

/**
 * 牙签实体类
 */
public class ToothPick {
    //当前这根牙签位于第几行
    private int linePosition;

    public ToothPick(int linePosition) {
        this.linePosition = linePosition;
    }

    public int getLinePosition() {
        return linePosition;
    }

    public void setLinePosition(int linePosition) {
        this.linePosition = linePosition;
    }
}
