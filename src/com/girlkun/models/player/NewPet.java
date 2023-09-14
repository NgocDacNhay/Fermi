package com.girlkun.models.player;

import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

/**
 *
 * @Stole By MITCHIKEN ZALO 0358689793
 */
public class NewPet extends Player{
    private long lastTimeChat;
    
    public Player master;
    public short body;
    public short leg;
//     public String name;
    public static int idb = -400000;
    
    public NewPet(Player master , short h,short b,short l, String name) {
        this.name = name;
        this.master = master;
        this.isNewPet = true;
//         this.isNewPet1 = true;
        this.id = idb;
//         this.name = "";
        idb--;
        this.head = h;
        this.body = b;
        this.leg = l;
    }
    
    @Override
    public short getHead() {
        return head;
    }

    @Override
    public short getBody() {
        return body;
    }
    
    @Override
    public short getLeg() {
        return leg;
    }
    
    public void joinMapMaster() {
        if(master == null){
            return;
        }
        this.location.x = master.location.x + Util.nextInt(-10, 10);
        this.location.y = master.location.y;
        ChangeMapService.gI().goToMap(this, master.zone);
        this.zone.load_Me_To_Another(this);
    }
    
    private long lastTimeMoveIdle;
    private int timeMoveIdle;
    public boolean idle;

    private void moveIdle() {
        if (idle && Util.canDoWithTime(lastTimeMoveIdle, timeMoveIdle)) {
            int dir = this.location.x - master.location.x <= 0 ? -1 : 1;
            PlayerService.gI().playerMove(this, master.location.x
                    + Util.nextInt(dir == -1 ? 30 : -50, dir == -1 ? 50 : 30), master.location.y);
            lastTimeMoveIdle = System.currentTimeMillis();
            timeMoveIdle = Util.nextInt(5000, 8000);
        }
    }
    
    @Override
    public void update() {
//        if (Util.canDoWithTime(lastTimeChat, 1000)) {
//            Service.getInstance().chat(this, "Hi lo Béo Đẹp Zai :3");
//            Service.getInstance().chat(this, "Béo ơi ăn cơm đi :33");
//            lastTimeChat = System.currentTimeMillis();
//        }
        super.update();
        if (this.isDie()) {
                Service.gI().hsChar(this, nPoint.hpMax, nPoint.mpMax);
            }
        if (master != null && (this.zone == null || this.zone != master.zone)) {
            joinMapMaster();
        }
        if (master != null && master.isDie()) {
            return;
        }
        moveIdle();
    }
    
    public void followMaster() {
        followMaster(10);
    }

    private void followMaster(int dis) {
        int mX = master.location.x;
        int mY = master.location.y;
        int disX = this.location.x - mX;
        if (Math.sqrt(Math.pow(mX - this.location.x, 2) + Math.pow(mY - this.location.y, 2)) >= dis) {
            if (disX < 0) {
                this.location.x = mX - Util.nextInt(0, dis);
            } else {
                this.location.x = mX + Util.nextInt(0, dis);
            }
            this.location.y = mY;
            PlayerService.gI().playerMove(this, this.location.x, this.location.y);
        }
    }
    
    @Override
    public void dispose() {
        this.master = null;
        super.dispose();
    }
}
