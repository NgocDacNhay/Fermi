package com.arriety.models.map.RanDoc;

import com.girlkun.models.boss.list_boss.ConDuongRanDoc.Saibamen;
import static com.arriety.models.map.gas.Gas.TIME_KHI_GAS;
import com.girlkun.models.player.Player;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.util.List;
/**
 *
 * @author BTH
 *
 */
public class ConDuongRanDocService {

    private static ConDuongRanDocService i;

    private ConDuongRanDocService() {

    }

    public static ConDuongRanDocService gI() {
        if (i == null) {
            i = new ConDuongRanDocService();
        }
        return i;
    }
    public void update(Player player){
        if (player.isPl() == true && player.clan.ConDuongRanDoc != null
                && player.clan.timeOpenConDuongRanDoc != 0){
            if(Util.canDoWithTime(player.clan.timeOpenConDuongRanDoc,TIME_KHI_GAS)){
                ketthucCDRD(player);
                player.clan.ConDuongRanDoc = null;
            }
        }
    }
    
     private void kickOutOfCDRD(Player player) {
        if (MapService.gI().isMapConDuongRanDoc(player.zone.map.mapId)) {
            Service.gI().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
        }
    }

    public void ketthucCDRD(Player player) {
        List<Player> playersMap = player.zone.getPlayers();
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            kickOutOfCDRD(pl);
        }
    }
    
    public void openConDuongRanDoc(Player player, byte level) {
        if (level >= 1 && level <= 110) {
            if (player.clan != null && player.clan.ConDuongRanDoc == null) {
                
                    ConDuongRanDoc conDuongRanDoc = null;
                    for (ConDuongRanDoc cdrd : ConDuongRanDoc.RAN_DOC) {
                        if (!cdrd.isOpened) {
                            conDuongRanDoc = cdrd;
                            break;
                        }
                    }
                    if (conDuongRanDoc != null) {
                        conDuongRanDoc.openBanDoKhoBau(player, player.clan, level);
                        try {
                            long bossDamage = (20 * level);
                            long bossMaxHealth = (2 * level);
                            bossDamage = Math.min(bossDamage, 200000000L);
                            bossMaxHealth = Math.min(bossMaxHealth, 2000000000L);
                            
//                            new Saibamen(player.clan.ConDuongRanDoc.getMapById(54),
//                                        (int) bossDamage,
//                                    (int) bossMaxHealth
//                 );    
                            Saibamen boss = new Saibamen(
                                    player.clan.ConDuongRanDoc.getMapById(144),
                                    player.clan.ConDuongRanDoc.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                            
                        } catch (Exception exception) {
                            Logger.logException(ConDuongRanDocService.class, exception, "Error initializing boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Con đường rắn độc đã đầy, vui lòng quay lại sau");
                    }
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } 
    }

