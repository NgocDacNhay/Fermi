package com.girlkun.models.boss.list_boss.Yadart;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;


public class YadartClone extends Boss {

    public YadartClone() throws Exception {
        super(BossID.YADART_CLONE, BossesData.YADART_CLONE);
    
    }
    
//    @Override
//    public void active() {
//        super.active();
//        if(Util.canDoWithTime(st,300000)){
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
//    }
//    
//    @Override
//    public void joinMap() {
//        super.joinMap();
//        st= System.currentTimeMillis();
//    }
//    private long st;
//    
//        @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
     @Override
    public void reward(Player plKill) {
        super.reward(plKill);
        if (this.currentLevel == 1) {
            return;
     
        }
        int[] NRs = new int[]{590};
       
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(50, 100)) {
           
            Service.getInstance().dropItemMap(this.zone, Util.ratiItem(zone, 590, 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 10, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
    }
    
    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(70, 100)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage >= 5000000) {
                damage = 5000000;
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
}
