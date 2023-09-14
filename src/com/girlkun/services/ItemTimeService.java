package com.girlkun.services;

import com.arriety.models.map.RanDoc.ConDuongRanDoc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.item.Item;
import static com.girlkun.models.item.ItemTime.*;
import com.arriety.models.map.bdkb.BanDoKhoBau;
import com.arriety.models.map.doanhtrai.DoanhTrai;
import com.arriety.models.map.gas.Gas;
//import com.arriety.models.map.khigas.KhiGas;
import com.girlkun.models.player.Fusion;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.utils.Logger;

public class ItemTimeService {

    private static ItemTimeService i;

    public static ItemTimeService gI() {
        if (i == null) {
            i = new ItemTimeService();
        }
        return i;
    }

    //gửi cho client
    public void sendAllItemTime(Player player) {
        sendTextDoanhTrai(player);
        sendTextBanDoKhoBau(player);
        sendTextGas(player);
        sendTextConDuongRanDoc(player);
        if (player.fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
            sendItemTime(player, player.gender == ConstPlayer.NAMEC ? 3901 : 3790,
                    (int) ((Fusion.TIME_FUSION - (System.currentTimeMillis() - player.fusion.lastTimeFusion)) / 1000));
        }
        if (player.itemTime.isUseBoHuyet) {
            sendItemTime(player, 2755, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoHuyet)) / 1000));
        }
        if (player.itemTime.isUseBoKhi) {
            sendItemTime(player, 2756, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoKhi)) / 1000));
        }
        if (player.itemTime.isUseGiapXen) {
            sendItemTime(player, 2757, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeGiapXen)) / 1000));
        }
        if (player.itemTime.isUseCuongNo) {
            sendItemTime(player, 2754, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeCuongNo)) / 1000));
        }

        if (player.itemTime.isUseAnDanh) {
            sendItemTime(player, 2760, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeAnDanh)) / 1000));
        }
        if (player.itemTime.isUseBoHuyet2) {
            sendItemTime(player, 10714, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoHuyet2)) / 1000));
        }
        if (player.itemTime.isUseBoKhi2) {
            sendItemTime(player, 10715, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoKhi2)) / 1000));
        }
        if (player.itemTime.isUseGiapXen2) {
            sendItemTime(player, 10712, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeGiapXen2)) / 1000));
        }
        if (player.itemTime.isUseCuongNo2) {
            sendItemTime(player, 10716, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeCuongNo2)) / 1000));
        }

        if (player.itemTime.isUseAnDanh2) {
            sendItemTime(player, 10717, (int) ((TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeAnDanh2)) / 1000));
        }
        if (player.itemTime.isOpenPower) {
            sendItemTime(player, 3783, (int) ((TIME_OPEN_POWER - (System.currentTimeMillis() - player.itemTime.lastTimeOpenPower)) / 1000));
        }
        if (player.itemTime.isUseMayDo) {
            sendItemTime(player, 2758, (int) ((TIME_MAY_DO - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo)) / 1000));
        }
        if (player.itemTime.isUseMayDo2) {//2758 icon// cai nay time co cho bằng cái máy dò kia ko
            sendItemTime(player, 17019, (int) ((TIME_MAY_DO2 - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo2)) / 1000));
        }
        if (player.itemTime.isUseMaydoDaPs) {//2758 icon// cai nay time co cho bằng cái máy dò kia ko
            sendItemTime(player, 15698, (int) ((TIME_MAY_DO3 - (System.currentTimeMillis() - player.itemTime.lastTimeMaydoDaPs)) / 1000));
        }
        if (player.itemTime.isUseBinhCanx2) {//bình cần x2
            sendItemTime(player, 21881, (int) ((TIME_BINH_CAN - (System.currentTimeMillis() - player.itemTime.lastTimeBinhCanx2)) / 1000));
        }
        if (player.itemTime.isUseBinhCanx5) {//bình cần x5
            sendItemTime(player, 21878, (int) ((TIME_BINH_CAN - (System.currentTimeMillis() - player.itemTime.lastTimeBinhCanx5)) / 1000));
        }
        if (player.itemTime.isUseBinhCanx7) {//bình cần x7
            sendItemTime(player, 21879, (int) ((TIME_BINH_CAN - (System.currentTimeMillis() - player.itemTime.lastTimeBinhCanx7)) / 1000));
        }
        if (player.itemTime.isUseBinhCanx10) {//binh cần x10
            sendItemTime(player, 21880, (int) ((TIME_BINH_CAN - (System.currentTimeMillis() - player.itemTime.lastTimeBinhCanx10)) / 1000));
        }
        if (player.itemTime.isEatMeal) {
            sendItemTime(player, player.itemTime.iconMeal, (int) ((TIME_EAT_MEAL - (System.currentTimeMillis() - player.itemTime.lastTimeEatMeal)) / 1000));
        }
        if (player.itemTime.isUseTDLT) {
            sendItemTime(player, 4387, player.itemTime.timeTDLT / 1000);
        }
        if (player.itemTime.isdkhi) {
            sendItemTime(player, 16228, (int) ((TIME_DUOI_KHI - (System.currentTimeMillis() - player.itemTime.lastTimedkhi)) / 1000));
        }
    }

    //bật tđlt
    public void turnOnTDLT(Player player, Item item) {
        int min = 0;
        for (Item.ItemOption io : item.itemOptions) {
            if (io.optionTemplate.id == 1) {
                min = io.param;
                io.param = 0;
                break;
            }
        }
        player.itemTime.isUseTDLT = true;
        player.itemTime.timeTDLT = min * 60 * 1000;
        player.itemTime.lastTimeUseTDLT = System.currentTimeMillis();
        sendCanAutoPlay(player);
        sendItemTime(player, 4387, player.itemTime.timeTDLT / 1000);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    //tắt tđlt
    public void turnOffTDLT(Player player, Item item) {
        player.itemTime.isUseTDLT = false;
        for (Item.ItemOption io : item.itemOptions) {
            if (io.optionTemplate.id == 1) {
                io.param += (short) ((player.itemTime.timeTDLT - (System.currentTimeMillis() - player.itemTime.lastTimeUseTDLT)) / 60 / 1000);
                break;
            }
        }
        sendCanAutoPlay(player);
        removeItemTime(player, 4387);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void sendCanAutoPlay(Player player) {
        Message msg;
        try {
            msg = new Message(-116);
            msg.writer().writeByte(player.itemTime.isUseTDLT ? 1 : 0);
            player.sendMessage(msg);
        } catch (Exception e) {
            Logger.logException(ItemTimeService.class, e);
        }
    }

    public void sendTextDoanhTrai(Player player) {
        if (player.clan != null) {
            if (player.clan.doanhTrai != null && player.clan != null && !player.clan.doanhTrai_haveGone && player.clan.doanhTrai_lastTimeOpen != 0) {
                int secondPassed = (int) ((System.currentTimeMillis() - player.clan.doanhTrai.getLastTimeOpen()) / 1000);
                int secondsLeft = (DoanhTrai.TIME_DOANH_TRAI / 1000) - secondPassed;
                sendTextTime(player, DOANH_TRAI, "Doanh trại độc nhãn", secondsLeft);
            }
        }
    }
    public void sendTextGas(Player player) {
        if (player.clan != null
                && player.clan.timeOpenKhiGas != 0) {
            int secondPassed = (int) ((System.currentTimeMillis() - player.clan.timeOpenKhiGas) / 1000);
            int secondsLeft = (Gas.TIME_KHI_GAS / 1000) - secondPassed;
            sendTextTime(player, KHI_GASS, "Khí Gas Hủy Diệt: ", secondsLeft);
        }
    }
    public void sendTextConDuongRanDoc(Player player) {
        if (player.clan != null && !player.clan.haveGoneConDuongRanDoc
                && player.clan.timeOpenConDuongRanDoc != 0) {
            int secondPassed = (int) ((System.currentTimeMillis() - player.clan.timeOpenConDuongRanDoc) / 1000);
            int secondsLeft = (ConDuongRanDoc.TIME_CDRD / 1000) - secondPassed;
            sendTextTime(player, CON_DUONG_RAN_DOC, "Con đường rắn độc: ", secondsLeft);
        }
    }

    public void sendTextBanDoKhoBau(Player player) {
        if (player.clan != null) {
            if (player.clan.banDoKhoBau != null && player.clan != null && !player.clan.banDoKhoBau_haveGone && player.clan.banDoKhoBau_lastTimeOpen != 0) {
                int secondPassed = (int) ((System.currentTimeMillis() - player.clan.banDoKhoBau.getLastTimeOpen()) / 1000);
                int secondsLeft = (BanDoKhoBau.TIME_BAN_DO_KHO_BAU / 1000) - secondPassed;
                sendTextTime(player, BAN_DO_KHO_BAU, "Bản đồ kho báu", secondsLeft);
            }
        }
    }

//    public void sendTextKhiGas(Player player) {
//        if (player.clan != null
//                && player.clan.lastTimeOpenKG != 0) {
//            int secondPassed = (int) ((System.currentTimeMillis() - player.clan.lastTimeOpenKG) / 1000);
//            int secondsLeft = (KhiGas.TIME_KHI_GAS / 1000) - secondPassed;
//            sendTextTime(player, KHI_GAS, "Khí Gas", secondsLeft);
//        }
//    }

    public void removeTextDoanhTrai(Player player) {
        removeTextTime(player, DOANH_TRAI);
    }

    public void removeTextTime(Player player, byte id) {
        sendTextTime(player, id, "", 0);
    }

    public void sendTextTime(Player player, byte id, String text, int seconds) {
        Message msg;
        try {
            msg = new Message(65);
            msg.writer().writeByte(id);
            msg.writer().writeUTF(text);
            msg.writer().writeShort(seconds);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void sendItemTime(Player player, int itemId, int time) {
        Message msg;
        try {
            msg = new Message(-106);
            msg.writer().writeShort(itemId);
            msg.writer().writeShort(time);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void removeItemTime(Player player, int itemTime) {
        sendItemTime(player, itemTime, 0);
    }

}
