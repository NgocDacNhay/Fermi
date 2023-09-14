package com.girlkun.models.boss.list_boss.BrolySuper;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;


public class BrolyThuong extends Boss {

    public BrolyThuong() throws Exception {
        super(Util.randomBossId(), BossesData.BOSS_BROLY_THUONG,BossesData.BOSS_BROLY_SUPER);
    }

    @Override
    public void reward(Player plKill) {
        int[] daquy = new int[]{1330};
        int[] nhanthan = new int[]{561};

        int randomAWJ = new Random().nextInt(daquy.length);
        int randomNTL = new Random().nextInt(nhanthan.length);
        if (Util.isTrue(95, 100)) {
        if (Util.isTrue(95, 100)) {
            Service.gI().dropItemMap(this.zone, Util.manhTS(zone, daquy[randomAWJ], 10, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, Util.ratiDTL(zone, nhanthan[randomNTL], 1, this.location.x, this.location.y, plKill.id));
        }
        }
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 2500000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage/1);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage/2;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

//    @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
//
//    @Override
//    public void reward(Player plKill) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.reward(plKill);
//    }
//    
//    @Override
//    protected void notifyJoinMap() {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.notifyJoinMap();
//    }
}