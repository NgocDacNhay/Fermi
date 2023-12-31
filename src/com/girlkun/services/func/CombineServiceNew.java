package com.girlkun.services.func;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerNotify;
import com.girlkun.network.io.Message;
import com.girlkun.services.*;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.util.*;
import java.util.stream.Collectors;

public class CombineServiceNew {

    private static final int COST_DOI_VE_DOI_DO_HUY_DIET = 500000000;
    private static final int COST_DAP_DO_KICH_HOAT = 500000000;
    private static final int COST_DOI_MANH_KICH_HOAT = 500000000;

    private static final int COST = 500000000;
    private static final int RUBY = 20000;

    private static final int TIME_COMBINE = 1;

    private static final byte MAX_STAR_ITEM = 10;
    private static final byte MAX_LEVEL_ITEM = 8;

    private static final byte OPEN_TAB_COMBINE = 0;
    private static final byte REOPEN_TAB_COMBINE = 1;
    private static final byte COMBINE_SUCCESS = 2;
    private static final byte COMBINE_FAIL = 3;
    private static final byte COMBINE_CHANGE_OPTION = 4;
    private static final byte COMBINE_DRAGON_BALL = 5;
    public static final byte OPEN_ITEM = 6;

    public static final int EP_SAO_TRANG_BI = 500;
    public static final int PHA_LE_HOA_TRANG_BI = 501;
    public static final int PHA_LE_HOA_TRANG_BI_100_LAN = 501100;
    public static final int CHUYEN_HOA_TRANG_BI = 502;

    public static final int NANG_CAP_VAT_PHAM = 510;
    public static final int NANG_CAP_BONG_TAI = 511;
    public static final int MO_CHI_SO_BONG_TAI = 519;

    public static final int MO_CHI_SO_Chien_Linh = 520;
    public static final int NANG_CAP_KHI = 521;
    public static final int Nang_Chien_Linh = 522;
    public static final int CHE_TAO_TRANG_BI_TS = 523;
    public static final int NHAP_NGOC_RONG = 513;
    public static final int PHAN_RA_DO_THAN_LINH = 514;
    public static final int NANG_CAP_DO_TS = 515;
    public static final int NANG_CAP_SKH_VIP = 516;
    
    public static final int PLH_CAITRANG = 601;
    public static final int EP_SAO_CAI_TRANG = 602;

    private static final int GOLD_MOCS_BONG_TAI = 500_000_000;
    private static final int RUBY_MOCS_BONG_TAI = 500;
    private static final int GOLD_BONG_TAI2 = 500_000_000;
    private static final int RUBY_BONG_TAI2 = 1_000;

    private static final int GOLD_LINHTHU = 500_000_000;
    private static final int GEM_LINHTHU = 5_000;
    
    private static final int COST_NANG_CAP_DO_TS = 2_000_000_000;

    private static final int RATIO_NANG_CAP_ChienLinh = 50;
    private static final int GOLD_Nang_Chien_Linh = 1_000_000_000;
    private static final int RUBY_Nang_Chien_Linh = 5000;
    private static final int RATIO_NANG_CAP = 100;
    private static final int GOLD_MOCS_Chien_Linh = 500_000_000;
    private static final int RUBY_MOCS_Chien_Linh = 1000;

    private static final int GOLD_NANG_KHI = 500_000_000;
    private static final int RUBY_NANG_KHI = 1000;

    public static final int NANG_CAP_DO_KICH_HOAT = 550;
    public static final int PS_HOA_TRANG_BI = 2150;
    public static final int TAY_PS_HOA_TRANG_BI = 2151;

    private final Npc baHatMit;
//    private final Npc tosukaio;
    private final Npc whis;
    private final Npc npsthiensu64;
    private final Npc khidaumoi;
    private final Npc trunglinhthu;
    private static CombineServiceNew i;

    public CombineServiceNew() {
        this.baHatMit = NpcManager.getNpc(ConstNpc.BA_HAT_MIT);
        this.npsthiensu64 = NpcManager.getNpc(ConstNpc.NPC_64);
        this.khidaumoi = NpcManager.getNpc(ConstNpc.KHI_DAU_MOI);
        this.trunglinhthu = NpcManager.getNpc(ConstNpc.TRUNG_LINH_THU);
         this.whis = NpcManager.getNpc(ConstNpc.WHIS);
    }

    public static CombineServiceNew gI() {
        if (i == null) {
            i = new CombineServiceNew();
        }
        return i;
    }

    /**
     * Mở tab đập đồ
     *
     * @param player
     * @param type kiểu đập đồ
     */
    public void openTabCombine(Player player, int type) {
        player.combineNew.setTypeCombine(type);
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_TAB_COMBINE);
            msg.writer().writeUTF(getTextInfoTabCombine(type));
            msg.writer().writeUTF(getTextTopTabCombine(type));
            if (player.iDMark.getNpcChose() != null) {
                msg.writer().writeShort(player.iDMark.getNpcChose().tempId);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiển thị thông tin đập đồ
     *
     * @param player
     */
    public void showInfoCombine(Player player, int[] index) {
        player.combineNew.clearItemCombine();
        if (index.length > 0) {
            for (int i = 0; i < index.length; i++) {
                player.combineNew.itemsCombine.add(player.inventory.itemsBag.get(index[i]));
            }
        }
        switch (player.combineNew.typeCombine) {
            case NANG_CAP_DO_KICH_HOAT:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 2 Món đồ Thần Linh bất kì", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item dtl = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 555 && item.template.id <= 567) {
                                dtl = item;
                                break;
                            }
                        }
                    }
                    if (dtl == null) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ thần linh", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi Món đồ Thần Linh này để nhận được một món đồ kích hoạt bất kì?\n|7|"
                            + "Và nhận được 1 món đồ kích hoạt bất kì\n"
                            + "|1|Cần " + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " vàng";

                    if (player.inventory.gold < COST_DAP_DO_KICH_HOAT) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_DAP_DO_KICH_HOAT,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 2) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;

            case Nang_Chien_Linh:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item linhthu = null;
                    Item ttt = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.type == 72) {
                            linhthu = item;
                        } else if (item.template.id == 2031) {
                            ttt = item;
                        }
                    }

                    if (linhthu != null && ttt != null) {

                        player.combineNew.goldCombine = GOLD_Nang_Chien_Linh;
                        player.combineNew.rubyCombine = RUBY_Nang_Chien_Linh;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP_ChienLinh;

                        String npcSay = "Pet: " + linhthu.template.name + " \n|2|";
                        for (Item.ItemOption io : linhthu.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (ttt.quantity >= 10) {
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                    npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                    trunglinhthu.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                            "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                                } else {
                                    npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                    trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(10 - ttt.quantity) + "Thăng tinh thạch";
                            trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }

                    } else {
                        this.trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Linh Thú và x10 Thăng tinh thạch", "Đóng");
                    }
                } else {
                    this.trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Linh Thú và x10 Thăng tinh thạch", "Đóng");
                }
                break;
            case NANG_CAP_KHI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item ctkhi = null;
                    Item dns = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (checkctkhi(item)) {
                            ctkhi = item;
                        } else if (item.template.id == 674) {
                            dns = item;
                        }
                    }

                    if (ctkhi != null && dns != null) {
                        int lvkhi = lvkhi(ctkhi);
                        int countdns = getcountdnsnangkhi(lvkhi);
                        player.combineNew.goldCombine = getGoldnangkhi(lvkhi);
                        player.combineNew.rubyCombine = getRubydnangkhi(lvkhi);
                        player.combineNew.ratioCombine = getRatioNangkhi(lvkhi);

                        String npcSay = "Cải trang khỉ Cấp: " + lvkhi + " \n|2|";
                        for (Item.ItemOption io : ctkhi.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (dns.quantity >= countdns) {
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                    npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                    khidaumoi.createOtherMenu(player, ConstNpc.MENU_NANG_KHI, npcSay,
                                            "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                                } else {
                                    npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                    khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(countdns - dns.quantity) + " Đá Ngũ Sắc";
                            khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }

                    } else {
                        this.khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Cải trang khỉ Cấp 1-7 và 10 + 10*lvkhi Đá Ngũ Sắc", "Đóng");
                    }
                } else {
                    this.khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Cải trang khỉ Cấp 1-7 và 10 + 10*lvkhi Đá Ngũ Sắc", "Đóng");
                }
                break;

            case NANG_CAP_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item bongtai = null;
                    Item manhvobt = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (checkbongtai(item)) {
                            bongtai = item;
                        } else if (item.template.id == 933) {
                            manhvobt = item;
                        }
                    }

                    if (bongtai != null && manhvobt != null) {
                        int lvbt = lvbt(bongtai);
                        int countmvbt = getcountmvbtnangbt(lvbt);
                        player.combineNew.goldCombine = getGoldnangbt(lvbt);
                        player.combineNew.rubyCombine = getRubydnangbt(lvbt);
                        player.combineNew.ratioCombine = getRationangbt(lvbt);

                        String npcSay = "Bông tai Porata Cấp: " + lvbt + " \n|2|";
                        for (Item.ItemOption io : bongtai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (manhvobt.quantity >= countmvbt) {
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                    npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                    baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                            "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                                } else {
                                    npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                    baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(countmvbt - manhvobt.quantity) + " Mảnh vỡ bông tai";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }

                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 1 hoặc 2 hoặc 3 và Mảnh vỡ bông tai", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 1 hoặc 2 hoặc 3 và Mảnh vỡ bông tai", "Đóng");
                }
                break;
            case MO_CHI_SO_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item bongTai = null;
                    Item manhHon = null;
                    Item daXanhLam = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 921) {
                            bongTai = item;
                        } else if (item.template.id == 934) {
                            manhHon = item;
                        } else if (item.template.id == 674) {
                            daXanhLam = item;
                        }
                    }
                    if (bongTai != null && manhHon != null && daXanhLam != null && daXanhLam.quantity >= 2 && manhHon.quantity >= 99) {

                        player.combineNew.goldCombine = GOLD_MOCS_BONG_TAI;
                        player.combineNew.rubyCombine = RUBY_MOCS_BONG_TAI;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;

                        String npcSay = "Bông tai Porata cấp " + (bongTai.template.id == 921 ? bongTai.template.id == 1155 ? bongTai.template.id == 1156 ? "2" : "3" : "4" : "1") + " \n|2|";
                        for (Item.ItemOption io : bongTai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 2 hoặc 3 hoặc 4, X99 Mảnh hồn bông tai và 2 Đá ngũ sắc", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 2 hoặc 3 hoặc 4, X99 Mảnh hồn bông tai và 2 Đá ngũ sắc", "Đóng");
                }

                break;
            case MO_CHI_SO_Chien_Linh:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item ChienLinh = null;
                    Item damathuat = null;
                    Item honthu = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id >= 1149 && item.template.id <= 1151) {
                            ChienLinh = item;
                        } else if (item.template.id == 2030) {
                            damathuat = item;
                        } else if (item.template.id == 2029) {
                            honthu = item;
                        }
                    }
                    if (ChienLinh != null && damathuat != null && damathuat.quantity >= 99 && honthu.quantity >= 99) {

                        player.combineNew.goldCombine = GOLD_MOCS_Chien_Linh;
                        player.combineNew.rubyCombine = RUBY_MOCS_Chien_Linh;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;

                        String npcSay = "Chiến Linh " + "\n|2|";
                        for (Item.ItemOption io : ChienLinh.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                trunglinhthu.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                            trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Chiến Linh, X99 Đá ma thuật và X99 Hồn linh thú", "Đóng");
                    }
                } else {
                    this.trunglinhthu.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Chiến Linh, X99 Đá ma thuật và X99 Hồn linh thú", "Đóng");
                }

                break;

            case EP_SAO_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item trangBi = null;
                    Item daPhaLe = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (isTrangBiPhaLeHoa(item)) {
                            trangBi = item;
                        } else if (isDaPhaLe(item)) {
                            daPhaLe = item;
                        }
                    }
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    if (trangBi != null && daPhaLe != null) {
                        for (Item.ItemOption io : trangBi.itemOptions) {
                            if (io.optionTemplate.id == 102) {
                                star = io.param;
                            } else if (io.optionTemplate.id == 107) {
                                starEmpty = io.param;
                            }
                        }
                        if (star < starEmpty) {
                            player.combineNew.rubyCombine = getGemEpSao(star);
                            String npcSay = trangBi.template.name + "\n|2|";
                            for (Item.ItemOption io : trangBi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            if (daPhaLe.template.type == 30) {
                                for (Item.ItemOption io : daPhaLe.itemOptions) {
                                    npcSay += "|7|" + io.getOptionString() + "\n";
                                }
                            } else {
                                npcSay += "|7|" + ItemService.gI().getItemOptionTemplate(getOptionDaPhaLe(daPhaLe)).name.replaceAll("#", getParamDaPhaLe(daPhaLe) + "") + "\n";
                            }
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.rubyCombine) + " ngọc hồng";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.rubyCombine + " ngọc hồng");

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                }
                break;
             case EP_SAO_CAI_TRANG:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item trangBi = null;
                    Item daPhaLe = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (isCaiTrang(item)) {
                            trangBi = item;
                        } else if (isDaPhaLe(item)) {
                            daPhaLe = item;
                        }
                    }
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    if (trangBi != null && daPhaLe != null) {
                        for (Item.ItemOption io : trangBi.itemOptions) {
                            if (io.optionTemplate.id == 102) {
                                star = io.param;
                            } else if (io.optionTemplate.id == 107) {
                                starEmpty = io.param;
                            }
                        }
                        if (star < starEmpty) {
                            player.combineNew.rubyCombine = getGemEpSao(star);
                            String npcSay = trangBi.template.name + "\n|2|";
                            for (Item.ItemOption io : trangBi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            if (daPhaLe.template.type == 30) {
                                for (Item.ItemOption io : daPhaLe.itemOptions) {
                                    npcSay += "|7|" + io.getOptionString() + "\n";
                                }
                            } else {
                                npcSay += "|7|" + ItemService.gI().getItemOptionTemplate(getOptionDaPhaLe(daPhaLe)).name.replaceAll("#", getParamDaPhaLe(daPhaLe) + "") + "\n";
                            }
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.rubyCombine) + " ngọc hồng";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.rubyCombine + " ngọc hồng");

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                }
                break;

            case PHA_LE_HOA_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isTrangBiPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_STAR_ITEM) {
                            player.combineNew.goldCombine = getGoldPhaLeHoa(star);
                            player.combineNew.gemCombine = getGemPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioPhaLeHoa(star);

                            String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 vật phẩm để pha lê hóa", "Đóng");
                }
                break;
                
            case PHA_LE_HOA_TRANG_BI_100_LAN:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isTrangBiPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_STAR_ITEM) {
                            player.combineNew.goldCombine = getGoldPhaLeHoa(star);
                            player.combineNew.gemCombine = getGemPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioPhaLeHoa(star);

                            String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 vật phẩm để pha lê hóa", "Đóng");
                }
                break;

                case PLH_CAITRANG:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isCaiTrang(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_LEVEL_ITEM) {
                            player.combineNew.goldCombine = getGoldPhaLeHoa(star);
                            player.combineNew.gemCombine = getGemPhaLeHoa(star);
                            player.combineNew.ratioCombine = getTile(star);

                            String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 vật phẩm để pha lê hóa", "Đóng");
                }
                break;
                
              case CHE_TAO_TRANG_BI_TS:
                 if (player.combineNew.itemsCombine.size() == 0) {
                   // this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Tuandz", "Yes");
                    return;
                }
                  if (player.combineNew.itemsCombine.size() >= 2 &&  player.combineNew.itemsCombine.size() < 5) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() &&  item.isCongThucVip()).count() < 1) {
                        this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Công thức Vip", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() < 1) {
                        this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Mảnh đồ thiên sứ", "Đóng");
                        return;
                    }
//                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() < 1 || player.combineNew.itemsCombine.size() == 4 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() < 1) {
//                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Đá nâng cấp", "Đóng");
//                        return;
//                    }
//                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).count() < 1 || player.combineNew.itemsCombine.size() == 4 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).count() < 1) {
//                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Đá may mắn", "Đóng");
//                        return;
//                    }
                    Item mTS = null, daNC = null, daMM = null;
                        for (Item item : player.combineNew.itemsCombine) {
                            if (item.isNotNullItem()) {
                                if (item.isManhTS()) {
                                mTS = item;
                            } else if (item.isDaNangCap()) {
                                daNC = item;
                            } else if (item.isDaMayMan()) {
                                daMM = item;
                            }
                        }
                    }
                    int tilemacdinh = 35;    
                    int tilenew = tilemacdinh;
//                    if (daNC != null) {
//                        tilenew += (daNC.template.id - 1073) * 10;                     
//                    }

                    String npcSay = "|1|Chế tạo " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " Thiên sứ " 
                            + player.combineNew.itemsCombine.stream().filter(Item::isCongThucVip).findFirst().get().typeHanhTinh() + "\n"
                            + "|1|Mạnh hơn trang bị Hủy Diệt từ 20% đến 35% \n"
                            + "|2|Mảnh ghép " +  mTS.quantity + "/999(Thất bại -99 mảnh ghép)";
                    if (daNC != null) {
                        npcSay += "|2|Đá nâng cấp " + player.combineNew.itemsCombine.stream().filter(Item::isDaNangCap).findFirst().get().typeDanangcap() 
                                  + " (+" + (daNC.template.id - 1073) + "0% tỉ lệ thành công)\n";
                    }
                    if (daMM != null) {
                        npcSay += "|2|Đá may mắn " + player.combineNew.itemsCombine.stream().filter(Item::isDaMayMan).findFirst().get().typeDaMayman()
                                  + " (+" + (daMM.template.id - 1078) + "0% tỉ lệ tối đa các chỉ số)\n";
                    }
                    if (daNC != null) {
                        tilenew += (daNC.template.id - 1073) * 10;
                        npcSay += "|2|Tỉ lệ thành công: " + tilenew + "%\n";
                    } else {
                        npcSay += "|2|Tỉ lệ thành công: " + tilemacdinh + "%\n";
                    }
                    npcSay += "|2|Phí nâng cấp: 2 tỉ vàng";
                    if (player.inventory.gold < 2000000000) {
                        this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn không đủ vàng", "Đóng");
                        return;
                    }
                    this.whis.createOtherMenu(player, ConstNpc.MENU_DAP_DO,
                            npcSay, "Đồng ý", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 4) {
                        this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Không đủ nguyên liệu", "Đóng");
                }
                break; 
            case TAY_PS_HOA_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 2) {
                        Item daHacHoa = null;
                        Item itemHacHoa = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 2047) {
                                daHacHoa = item_;
                            } else if (item_.isTrangBiHacHoa()) {
                                itemHacHoa = item_;
                            }
                        }
                        if (daHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn bùa giải pháp sư", "Đóng");
                            return;
                        }
                        if (itemHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu trang bị", "Đóng");
                            return;
                        }

                        String npcSay = "|2|Hiện tại " + itemHacHoa.template.name + "\n|0|";
                        for (Item.ItemOption io : itemHacHoa.itemOptions) {
                            if (io.optionTemplate.id != 72) {
                                npcSay += io.getOptionString() + "\n";
                            }
                        }
                        npcSay += "|2|Sau khi nâng cấp sẽ xoá hết các chỉ số pháp sư ngẫu nhiên"
                                + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                + "Cần " + Util.numberToMoney(COST) + " vàng";

                        this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần có trang bị có thể pháp sư và bùa giải pháp sư", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }

                break;
            case PS_HOA_TRANG_BI:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 2) {
                        Item daHacHoa = null;
                        Item itemHacHoa = null;
                        for (Item item_ : player.combineNew.itemsCombine) {
                            if (item_.template.id == 2046) {
                                daHacHoa = item_;
                            } else if (item_.isTrangBiHacHoa()) {
                                itemHacHoa = item_;
                            }
                        }
                        if (daHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu đá pháp sư", "Đóng");
                            return;
                        }
                        if (itemHacHoa == null) {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn còn thiếu trang bị", "Đóng");
                            return;
                        }
                        if (itemHacHoa != null) {
                            for (ItemOption itopt : itemHacHoa.itemOptions) {
                                if (itopt.optionTemplate.id == 223) {
                                    if (itopt.param >= 8) {
                                        Service.getInstance().sendThongBao(player, "Trang bị đã đạt tới giới hạn pháp sư");
                                        return;
                                    }
                                }
                            }
                        }
                        
                        player.combineNew.goldCombine = COST;
                        player.combineNew.gemCombine = RUBY;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;
                        
                        
                        String npcSay = "|2|Hiện tại " + itemHacHoa.template.name + "\n|0|";
                        for (Item.ItemOption io : itemHacHoa.itemOptions) {
                            if (io.optionTemplate.id != 72) {
                                npcSay += io.getOptionString() + "\n";
                            }
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng"
                                     + "Cần " + Util.numberToMoney(player.combineNew.gemCombine) + " vàng";
                                      
                        }

                        this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Nâng cấp", "Từ chối");
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần có trang bị có thể pháp sư và đá pháp sư", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }

                break;
            case NHAP_NGOC_RONG:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 1) {
                        Item item = player.combineNew.itemsCombine.get(0);
                        if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                            String npcSay = "|2|Con có muốn biến 7 " + item.template.name + " thành\n"
                                    + "1 viên " + ItemService.gI().getTemplate((short) (item.template.id - 1)).name + "\n"
                                    + "|7|Cần 7 " + item.template.name;
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép", "Từ chối");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }
                break;
            case NANG_CAP_VAT_PHAM:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đá nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    Item itemDo = null;
                    Item itemDNC = null;
                    Item itemDBV = null;
                    for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                        if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                                itemDBV = player.combineNew.itemsCombine.get(j);
                                continue;
                            }
                            if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                                itemDo = player.combineNew.itemsCombine.get(j);
                            } else {
                                itemDNC = player.combineNew.itemsCombine.get(j);
                            }
                        }
                    }
                    if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                        int level = 0;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            player.combineNew.goldCombine = getGoldNangCapDo(level);
                            player.combineNew.ratioCombine = (float) getTileNangCapDo(level);
                            player.combineNew.countDaNangCap = getCountDaNangCapDo(level);
                            player.combineNew.countDaBaoVe = (short) getCountDaBaoVe(level);
                            String npcSay = "|2|Hiện tại " + itemDo.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id != 72) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            String option = null;
                            int param = 0;
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id == 47
                                        || io.optionTemplate.id == 6
                                        || io.optionTemplate.id == 0
                                        || io.optionTemplate.id == 7
                                        || io.optionTemplate.id == 14
                                        || io.optionTemplate.id == 22
                                        || io.optionTemplate.id == 23) {
                                    option = io.optionTemplate.name;
                                    param = io.param + (io.param * 10 / 100);
                                    break;
                                }
                            }
                            npcSay += "|2|Sau khi nâng cấp (+" + (level + 1) + ")\n|7|"
                                    + option.replaceAll("#", String.valueOf(param))
                                    + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                    + (player.combineNew.countDaNangCap > itemDNC.quantity ? "|7|" : "|1|")
                                    + "Cần " + player.combineNew.countDaNangCap + " " + itemDNC.template.name
                                    + "\n" + (player.combineNew.goldCombine > player.inventory.gold ? "|7|" : "|1|")
                                    + "Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";

                            String daNPC = player.combineNew.itemsCombine.size() == 3 && itemDBV != null ? String.format("\nCần tốn %s đá bảo vệ", player.combineNew.countDaBaoVe) : "";
                            if ((level == 2 || level == 4 || level == 6) && !(player.combineNew.itemsCombine.size() == 3 && itemDBV != null)) {
                                npcSay += "\nNếu thất bại sẽ rớt xuống (+" + (level - 1) + ")";
                            }
                            if (player.combineNew.countDaNangCap > itemDNC.quantity) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - itemDNC.quantity) + " " + itemDNC.template.name);
                            } else if (player.combineNew.goldCombine > player.inventory.gold) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + Util.numberToMoney((player.combineNew.goldCombine - player.inventory.gold)) + " vàng");
                            } else if (player.combineNew.itemsCombine.size() == 3 && Objects.nonNull(itemDBV) && itemDBV.quantity < player.combineNew.countDaBaoVe) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaBaoVe - itemDBV.quantity) + " đá bảo vệ");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                        npcSay, "Nâng cấp\n" + Util.numberToMoney(player.combineNew.goldCombine) + " vàng" + daNPC, "Từ chối");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang bị của ngươi đã đạt cấp tối đa", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                    }
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        break;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                }
                break;
            case PHAN_RA_DO_THAN_LINH:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con hãy đưa ta đồ thần linh để phân rã", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 1) {
                    List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
                    int couponAdd = 0;
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (item.isNotNullItem()) {
                        if (item.template.id >= 555 && item.template.id <= 567) {
                            couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
                        }
                    }
                    if (couponAdd == 0) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã đồ thần linh thôi", "Đóng");
                        return;
                    }
                    String npcSay = "|2|Sau khi phân rải vật phẩm\n|7|"
                            + "Bạn sẽ nhận được : " + couponAdd + " Điểm\n"
                            + (500000000 > player.inventory.gold ? "|7|" : "|1|")
                            + "Cần " + Util.numberToMoney(500000000) + " vàng";

                    if (player.inventory.gold < 500000000) {
                        this.baHatMit.npcChat(player, "Hết tiền rồi\nẢo ít thôi con");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_PHAN_RA_DO_THAN_LINH,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(500000000) + " vàng", "Từ chối");
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã 1 lần 1 món đồ thần linh", "Đóng");
                }
                break;
            case NANG_CAP_DO_TS:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 2 món Hủy Diệt bất kì và 1 món Thần Linh cùng loại", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ thần linh", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() < 2) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ hủy diệt", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu mảnh thiên sứ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " thiên sứ tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_NANG_CAP_DO_TS,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case NANG_CAP_SKH_VIP:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 1 món Thần Linh và 2 món SKH ngẫu nhiên", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ Thần Linh", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() < 2) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ kích hoạt ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isDTL).findFirst().get().typeName() + " kích hoạt VIP tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_NANG_DOI_SKH_VIP,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
        }
    }

    /**
     * Bắt đầu đập đồ - điều hướng từng loại đập đồ
     *
     * @param player
     */
    public void startCombine(Player player) {
        switch (player.combineNew.typeCombine) {
            case EP_SAO_TRANG_BI:
                epSaoTrangBi(player);
                break;
            case EP_SAO_CAI_TRANG:
                epSaoCaiTrang(player);
                break;
            case PHA_LE_HOA_TRANG_BI:
                phaLeHoaTrangBi(player);
                break;
            case PHA_LE_HOA_TRANG_BI_100_LAN:
                phaLeHoaTrangBi100Lan(player);
                break;
            case PLH_CAITRANG:
                phaLect(player);
                break;
            case CHUYEN_HOA_TRANG_BI:

                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong(player);
                break;

            case PHAN_RA_DO_THAN_LINH:
                phanradothanlinh(player);
                break;
            case NANG_CAP_DO_TS:
                openDTS(player);
                break;
            case NANG_CAP_SKH_VIP:
                openSKHVIP(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham(player);
                break;
            case NANG_CAP_BONG_TAI:
                nangCapBongTai(player);
                break;
            case MO_CHI_SO_BONG_TAI:
                moChiSoBongTai(player);
            case NANG_CAP_KHI:
                nangCapKhi(player);
                break;
            case PS_HOA_TRANG_BI:
                psHoaTrangBi(player);
                break;
            case TAY_PS_HOA_TRANG_BI:
                tayHacHoaTrangBi(player);
                break;
            case MO_CHI_SO_Chien_Linh:
                moChiSoLinhThu(player);
                break;
            case Nang_Chien_Linh:
                nangCapChienLinh(player);
                break;
            case NANG_CAP_DO_KICH_HOAT:
                dapDoKichHoat(player);
            case CHE_TAO_TRANG_BI_TS:
                openCreateItemAngel(player);
                break;     
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    public void GetTrangBiKichHoathuydiet(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(1500, 2000)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(100, 150)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(9000, 11000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(90, 150)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(15, 20)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {// 
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void GetTrangBiKichHoatthiensu(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(2000, 2500)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(150, 200)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(18000, 20000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(150, 200)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(20, 25)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {// 
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void laychisoctkhi(Player player, Item ctkhi, int lvkhi) {
        ctkhi.itemOptions.add(new ItemOption(50, 11 + 2 * lvkhi));//sd
        ctkhi.itemOptions.add(new ItemOption(77, 11 + 2 * lvkhi));//hp
        ctkhi.itemOptions.add(new ItemOption(103, 11 + 2 * lvkhi));//ki
        ctkhi.itemOptions.add(new ItemOption(14, 6 + 2 * lvkhi));//cm
        ctkhi.itemOptions.add(new ItemOption(5, 30 + 10 * lvkhi));//sd cm
        ctkhi.itemOptions.add(new ItemOption(106, 0));
        ctkhi.itemOptions.add(new ItemOption(34, 0));
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void laychiChienLinh(Player player, Item ctkhi) {
        ctkhi.itemOptions.add(new ItemOption(50, Util.nextInt(7, 15)));//sd
        ctkhi.itemOptions.add(new ItemOption(77, Util.nextInt(7, 15)));//hp
        ctkhi.itemOptions.add(new ItemOption(103, Util.nextInt(7, 15)));//ki
        ctkhi.itemOptions.add(new ItemOption(72, 2));
        ctkhi.itemOptions.add(new ItemOption(30, 0));
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private void doiKiemThan(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item keo = null, luoiKiem = null, chuoiKiem = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 2015) {
                    keo = it;
                } else if (it.template.id == 2016) {
                    chuoiKiem = it;
                } else if (it.template.id == 2017) {
                    luoiKiem = it;
                }
            }
            if (keo != null && keo.quantity >= 99 && luoiKiem != null && chuoiKiem != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2018);
                    item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(9, 15)));
                    item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(8, 15)));
                    item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(8, 15)));
                    if (Util.isTrue(80, 100)) {
                        item.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 15)));
                    }
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, keo, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, luoiKiem, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, chuoiKiem, 1);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiChuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhNhua = player.combineNew.itemsCombine.get(0);
            if (manhNhua.template.id == 2014 && manhNhua.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2016);
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhNhua, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiLuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhSat = player.combineNew.itemsCombine.get(0);
            if (manhSat.template.id == 2013 && manhSat.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2017);
                    InventoryServiceNew.gI().addItemBag(player, item);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhSat, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiManhKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
            Item nr1s = null, doThan = null, buaBaoVe = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 14) {
                    nr1s = it;
                } else if (it.template.id == 2010) {
                    buaBaoVe = it;
                } else if (it.template.id >= 555 && it.template.id <= 567) {
                    doThan = it;
                }
            }

            if (nr1s != null && doThan != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_MANH_KICH_HOAT) {
                    player.inventory.gold -= COST_DOI_MANH_KICH_HOAT;
                    int tiLe = buaBaoVe != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) 2009);
                        item.itemOptions.add(new Item.ItemOption(30, 0));
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, nr1s, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, doThan, 1);
                    if (buaBaoVe != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, buaBaoVe, 1);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            } else {
                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị thần linh và 1 viên ngọc rồng 1 sao", "Đóng");
            }
        }
    }

    private void phanradothanlinh(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            player.inventory.gold -= 500000000;
            List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
            Item item = player.combineNew.itemsCombine.get(0);
            int couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
            sendEffectSuccessCombine(player);
            player.inventory.coupon += couponAdd;
            this.baHatMit.npcChat(player, "Con đã nhận được " + couponAdd + " điểm");
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
            player.combineNew.itemsCombine.clear();
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
        }
    }

    public void openDTS(Player player) {
        //check sl đồ tl, đồ hd
        // new update 2 mon huy diet + 1 mon than linh(skh theo style) +  5 manh bat ki
        if (player.combineNew.itemsCombine.size() != 4) {
            Service.gI().sendThongBao(player, "Thiếu đồ");
            return;
        }
        if (player.inventory.gold < COST) {
            Service.gI().sendThongBao(player, "Ảo ít thôi con...");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
        Item itemTL = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).findFirst().get();
        List<Item> itemHDs = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).collect(Collectors.toList());
        Item itemManh = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 5).findFirst().get();

        player.inventory.gold -= COST;
        sendEffectSuccessCombine(player);
        short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2

        Item itemTS = ItemService.gI().DoThienSu(itemIds[itemTL.template.gender > 2 ? player.gender : itemTL.template.gender][itemManh.typeIdManh()], itemTL.template.gender);
        InventoryServiceNew.gI().addItemBag(player, itemTS);

        InventoryServiceNew.gI().subQuantityItemsBag(player, itemTL, 1);
        InventoryServiceNew.gI().subQuantityItemsBag(player, itemManh, 5);
        itemHDs.forEach(item -> InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1));
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        Service.gI().sendThongBao(player, "Bạn đã nhận được " + itemTS.template.name);
        player.combineNew.itemsCombine.clear();
        reOpenItemCombine(player);
    }

    public void openSKHVIP(Player player) {
        // 1 thiên sứ + 2 món kích hoạt -- món đầu kh làm gốc
        if (player.combineNew.itemsCombine.size() != 3) {
            Service.gI().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu đồ thiên sứ");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() != 2) {
            Service.gI().sendThongBao(player, "Thiếu đồ kích hoạt");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 1) {
                Service.gI().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= COST;
            Item itemTS = player.combineNew.itemsCombine.stream().filter(Item::isDTL).findFirst().get();
            List<Item> itemSKH = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).collect(Collectors.toList());
            CombineServiceNew.gI().sendEffectOpenItem(player, itemTS.template.iconID, itemTS.template.iconID);
            short itemId;
            if (itemTS.template.gender == 3 || itemTS.template.type == 4) {
                itemId = Manager.radaSKHVip[Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.radaSKHVip[6];
                }
            } else {
                itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][6];
                }
            }
            int skhId = ItemService.gI().randomSKHId(itemTS.template.gender);
            Item item;
            if (new Item(itemId).isDTL()) {
                item = Util.ratiItemTL(itemId);
                item.itemOptions.add(new Item.ItemOption(skhId, 1));
                item.itemOptions.add(new Item.ItemOption(ItemService.gI().optionIdSKH(skhId), 1));
                item.itemOptions.remove(item.itemOptions.stream().filter(itemOption -> itemOption.optionTemplate.id == 21).findFirst().get());
                item.itemOptions.add(new Item.ItemOption(21, 15));
                item.itemOptions.add(new Item.ItemOption(30, 1));
            } else {
                item = ItemService.gI().itemSKH(itemId, skhId);
            }
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemTS, 1);
            itemSKH.forEach(i -> InventoryServiceNew.gI().subQuantityItemsBag(player, i, 1));
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    private void dapDoKichHoat(Player player) {

        //Trum Cot ddow
        if (player.combineNew.itemsCombine.size() == 2) {
            Item dtl1 = null;
            Item dtl2 = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id >= 555 && item.template.id <= 567) {
                        if (dtl1 == null) {
                            dtl1 = item;
                        } else {
                            dtl2 = item;
                        }
                    }
                }
            }
            if (dtl1 != null && dtl2 != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 //check chỗ trống hành trang
                        && player.inventory.gold >= COST_DAP_DO_KICH_HOAT) {
                    player.inventory.gold -= COST_DAP_DO_KICH_HOAT;
                    int tiLe = 100;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) getTempIdItemC0(dtl1.template.gender, dtl1.template.type));
                        RewardService.gI().initBaseOptionClothes(item.template.id, item.template.type, item.itemOptions);
                        RewardService.gI().initActivationOption(item.template.gender < 3 ? item.template.gender : player.gender, item.template.type, item.itemOptions);
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dtl1, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dtl2, 1);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiVeHuyDiet(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (item.isNotNullItem() && item.template.id >= 555 && item.template.id <= 567) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_VE_DOI_DO_HUY_DIET) {
                    player.inventory.gold -= COST_DOI_VE_DOI_DO_HUY_DIET;
                    Item ticket = ItemService.gI().createNewItem((short) (2001 + item.template.type));
                    ticket.itemOptions.add(new Item.ItemOption(30, 0));
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                    InventoryServiceNew.gI().addItemBag(player, ticket);
                    sendEffectOpenItem(player, item.template.iconID, ticket.template.iconID);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void nangCapChienLinh(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }

            Item linhthu = null;
            Item ttt = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.type == 72) {
                    linhthu = item;
                } else if (item.template.id == 2031) {
                    ttt = item;
                }
            }
            if (linhthu != null && ttt != null) {

                if (ttt.quantity < 10) {
                    Service.gI().sendThongBao(player, "Thăng tinh thạch");
                    return;
                }
                player.inventory.gold -= gold;
                player.inventory.ruby -= ruby;
                InventoryServiceNew.gI().subQuantityItemsBag(player, ttt, 10);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    short[] chienlinh = {2019, 2020, 2021, 2022, 2023, 2024, 2025};
                    linhthu.template = ItemService.gI().getTemplate(chienlinh[Util.nextInt(0, 2)]);
                    linhthu.itemOptions.clear();
                    laychiChienLinh(player, linhthu);
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void nangCapKhi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }

            Item ctkhi = null;
            Item dns = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (checkctkhi(item)) {
                    ctkhi = item;
                } else if (item.template.id == 674) {
                    dns = item;
                }
            }
            if (ctkhi != null && dns != null) {
                int lvkhi = lvkhi(ctkhi);
                int countdns = getcountdnsnangkhi(lvkhi);
                if (countdns > dns.quantity) {
                    Service.gI().sendThongBao(player, "Không đủ đá ngũ sắc");
                    return;
                }
                player.inventory.gold -= gold;
                player.inventory.ruby -= ruby;
                InventoryServiceNew.gI().subQuantityItemsBag(player, dns, countdns);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    short idctkhisaunc = getidctkhisaukhilencap(lvkhi);
                    ctkhi.template = ItemService.gI().getTemplate(idctkhisaunc);
                    ctkhi.itemOptions.clear();
                    ctkhi.itemOptions.add(new Item.ItemOption(72, lvkhi + 1));
                    laychisoctkhi(player, ctkhi, lvkhi);
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void nangCapBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }
            Item bongtai = null;
            Item manhvobt = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (checkbongtai(item)) {
                    bongtai = item;
                } else if (item.template.id == 933) {
                    manhvobt = item;
                }
            }
            if (bongtai != null && manhvobt != null) {
                int lvbt = lvbt(bongtai);
                int countmvbt = getcountmvbtnangbt(lvbt);
                if (countmvbt > manhvobt.quantity) {
                    Service.gI().sendThongBao(player, "Không đủ Mảnh vỡ bông tai");
                    return;
                }
                player.inventory.gold -= gold;
                player.inventory.ruby -= ruby;
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhvobt, countmvbt);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    bongtai.template = ItemService.gI().getTemplate(getidbtsaukhilencap(lvbt));
                    bongtai.itemOptions.clear();
                    bongtai.itemOptions.add(new Item.ItemOption(72, lvbt + 1));
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void moChiSoBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }
            Item bongTai = null;
            Item manhHon = null;
            Item daXanhLam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 921) {
                    bongTai = item;
                } else if (item.template.id == 934) {
                    manhHon = item;
                } else if (item.template.id == 674) {
                    daXanhLam = item;
                }
            }
            if (bongTai != null && daXanhLam != null && daXanhLam.quantity >= 2 && manhHon.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.ruby -= ruby;
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhHon, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, daXanhLam, 2);
                if (Util.isTrue(60, 100)) {
                    bongTai.itemOptions.clear();
                    if (bongTai.template.id == 921) {
                        bongTai.itemOptions.add(new Item.ItemOption(72, 2));
                    } else if (bongTai.template.id == 1155) {
                        bongTai.itemOptions.add(new Item.ItemOption(72, 3));
                    } else if (bongTai.template.id == 1156) {
                        bongTai.itemOptions.add(new Item.ItemOption(72, 4));
                    }
                    int rdUp = Util.nextInt(0, 7);
                    if (rdUp == 0) {
                        bongTai.itemOptions.add(new Item.ItemOption(50, Util.nextInt(5, 17)));
                    } else if (rdUp == 1) {
                        bongTai.itemOptions.add(new Item.ItemOption(77, Util.nextInt(5, 17)));
                    } else if (rdUp == 2) {
                        bongTai.itemOptions.add(new Item.ItemOption(103, Util.nextInt(5, 17)));
                    } else if (rdUp == 3) {
                        bongTai.itemOptions.add(new Item.ItemOption(108, Util.nextInt(5, 17)));
                    } else if (rdUp == 4) {
                        bongTai.itemOptions.add(new Item.ItemOption(94, Util.nextInt(5, 17)));
                    } else if (rdUp == 5) {
                        bongTai.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 17)));
                    } else if (rdUp == 6) {
                        bongTai.itemOptions.add(new Item.ItemOption(80, Util.nextInt(5, 17)));
                    } else if (rdUp == 7) {
                        bongTai.itemOptions.add(new Item.ItemOption(81, Util.nextInt(5, 17)));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void moChiSoLinhThu(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }
            Item ChienLinh = null;
            Item damathuat = null;
            Item honthu = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id >= 1149 && item.template.id <= 1151) {
                    ChienLinh = item;
                } else if (item.template.id == 2030) {
                    damathuat = item;
                } else if (item.template.id == 2029) {
                    honthu = item;
                }
            }
            if (ChienLinh != null && damathuat.quantity >= 99 && honthu.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.ruby -= ruby;
                InventoryServiceNew.gI().subQuantityItemsBag(player, damathuat, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, honthu, 99);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    ChienLinh.itemOptions.add(new Item.ItemOption(206, 0));
                    int rdUp = Util.nextInt(0, 7);
                    if (rdUp == 1) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(50, Util.nextInt(5, 25)));
                    } else if (rdUp == 1) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(77, Util.nextInt(5, 25)));
                    } else if (rdUp == 2) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(103, Util.nextInt(5, 25)));
                    } else if (rdUp == 3) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(108, Util.nextInt(5, 25)));
                    } else if (rdUp == 4) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(94, Util.nextInt(5, 15)));
                    } else if (rdUp == 5) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 15)));
                    } else if (rdUp == 6) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(80, Util.nextInt(5, 25)));
                    } else if (rdUp == 7) {
                        ChienLinh.itemOptions.add(new Item.ItemOption(81, Util.nextInt(5, 25)));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
       public void openCreateItemAngel(Player player) {
        // Công thức vip + x999 Mảnh thiên sứ + đá nâng cấp + đá may mắn
        if (player.combineNew.itemsCombine.size() < 2 || player.combineNew.itemsCombine.size() > 4) {
            Service.getInstance().sendThongBao(player, "Thiếu vật phẩm, vui lòng thêm vào");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Công thức Vip");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Mảnh thiên sứ");
            return;
        }
        Item mTS = null, daNC = null, daMM = null, CtVip = null;
        for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.isManhTS()) {
                        mTS = item;
                    } else if (item.isDaNangCap()) {
                        daNC = item;
                    } else if (item.isDaMayMan()) {
                        daMM = item;
                    } else if (item.isCongThucVip()) {
                        CtVip = item;
                    }
                }
            }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 ) {//check chỗ trống hành trang
            if (player.inventory.gold < COST_NANG_CAP_DO_TS) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
                    player.inventory.gold -= COST_NANG_CAP_DO_TS;
                    
                    int tilemacdinh = 35;
                    int tileLucky = 20;
                    if (daNC != null) {
                        tilemacdinh += (daNC.template.id - 1073)*10;
                    } else {
                        tilemacdinh = tilemacdinh;
                    }
                    if (daMM != null) {
                        tileLucky += tileLucky*(daMM.template.id - 1078)*10/100;
                    } else {
                        tileLucky = tileLucky;
                    }
                    if (Util.nextInt(0, 100) < tilemacdinh) {
                        Item itemCtVip = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).findFirst().get();
                        if (daNC != null) {
                        Item itemDaNangC = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).findFirst().get();
                        }
                        if (daMM != null) {
                        Item itemDaMayM = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).findFirst().get();
                        }
                        Item itemManh = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).findFirst().get();
                        
                        tilemacdinh = Util.nextInt(0, 50);
                        if (tilemacdinh == 49) { tilemacdinh = 20;}
                        else if (tilemacdinh == 48 || tilemacdinh == 47) { tilemacdinh = 19;}
                        else if (tilemacdinh == 46 || tilemacdinh == 45) { tilemacdinh = 18;}
                        else if (tilemacdinh == 44 || tilemacdinh == 43) { tilemacdinh = 17;}
                        else if (tilemacdinh == 42 || tilemacdinh == 41) { tilemacdinh = 16;}
                        else if (tilemacdinh == 40 || tilemacdinh == 39) { tilemacdinh = 15;}
                        else if (tilemacdinh == 38 || tilemacdinh == 37) { tilemacdinh = 14;}
                        else if (tilemacdinh == 36 || tilemacdinh == 35) { tilemacdinh = 13;}
                        else if (tilemacdinh == 34 || tilemacdinh == 33) { tilemacdinh = 12;}
                        else if (tilemacdinh == 32 || tilemacdinh == 31) { tilemacdinh = 11;}
                        else if (tilemacdinh == 30 || tilemacdinh == 29) { tilemacdinh = 10;}
                        else if (tilemacdinh <= 28 || tilemacdinh >= 26) { tilemacdinh = 9;}
                        else if (tilemacdinh <= 25 || tilemacdinh >= 23) { tilemacdinh = 8;}
                        else if (tilemacdinh <= 22 || tilemacdinh >= 20) { tilemacdinh = 7;}
                        else if (tilemacdinh <= 19 || tilemacdinh >= 17) { tilemacdinh = 6;}
                        else if (tilemacdinh <= 16 || tilemacdinh >= 14) { tilemacdinh = 5;}
                        else if (tilemacdinh <= 13 || tilemacdinh >= 11) { tilemacdinh = 4;}
                        else if (tilemacdinh <= 10 || tilemacdinh >= 8) { tilemacdinh = 3;}
                        else if (tilemacdinh <= 7 || tilemacdinh >= 5) { tilemacdinh = 2;}
                        else if (tilemacdinh <= 4 || tilemacdinh >= 2) { tilemacdinh = 1;}
                        else if (tilemacdinh <= 1) { tilemacdinh = 0;}
                        short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2

                        Item itemTS = ItemService.gI().DoThienSu(itemIds[itemCtVip.template.gender > 2 ? player.gender : itemCtVip.template.gender][itemManh.typeIdManh()], itemCtVip.template.gender);
                        
                        tilemacdinh += 10;
                        
                        if (tilemacdinh > 0) {
                            for(byte i = 0; i < itemTS.itemOptions.size(); i++) {
                            if(itemTS.itemOptions.get(i).optionTemplate.id != 21 && itemTS.itemOptions.get(i).optionTemplate.id != 30) {
                                itemTS.itemOptions.get(i).param += (itemTS.itemOptions.get(i).param*tilemacdinh/100);
                            }
                        }
                    }
                        tilemacdinh = Util.nextInt(0, 100);
                        
                        if (tilemacdinh <= tileLucky) {
                        if (tilemacdinh >= (tileLucky - 3)) {
                            tileLucky = 3;
                        } else if (tilemacdinh <= (tileLucky - 4) && tilemacdinh >= (tileLucky - 10)) {
                            tileLucky = 2;
                        } else { tileLucky = 1; }
                        itemTS.itemOptions.add(new Item.ItemOption(15, tileLucky));
                        ArrayList<Integer> listOptionBonus = new ArrayList<>();
                        listOptionBonus.add(50); 
                        listOptionBonus.add(77); 
                        listOptionBonus.add(103); 
                        listOptionBonus.add(98);
                        listOptionBonus.add(99);
                        for (int i = 0; i < tileLucky; i++) {
                            tilemacdinh = Util.nextInt(0, listOptionBonus.size());
                            itemTS.itemOptions.add(new ItemOption(listOptionBonus.get(tilemacdinh), Util.nextInt(1, 5)));
                            listOptionBonus.remove(tilemacdinh);
                        }
                    }
                        
                        InventoryServiceNew.gI().addItemBag(player, itemTS);
                        sendEffectSuccessCombine(player);
                        if (mTS != null && daMM != null && daNC != null && CtVip != null ) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                    } else if (CtVip != null && mTS != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                    } else if (CtVip != null && mTS != null && daNC != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
                    } else if (CtVip != null && mTS != null && daMM != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 999);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
                    }
                    
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                        
                    } else {
                        sendEffectFailCombine(player);
                        if (mTS != null && daMM != null && daNC != null && CtVip != null ) {
                       
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                     
                    } else if (CtVip != null && mTS != null) {
                      
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                    } else if (CtVip != null && mTS != null && daNC != null) {
                     
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                       
                    } else if (CtVip != null && mTS != null && daMM != null) {
             
                        InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
                     
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                    }
                    
                } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }


    private void epSaoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ ngọc hồng để thực hiện");
                return;
            }
            Item trangBi = null;
            Item CaiTrang = null;
            Item daPhaLe = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isTrangBiPhaLeHoa(item)) {
                    trangBi = item;
                }
                else if (isDaPhaLe(item)) {
                    daPhaLe = item;
                }
            }
            int star = 0; //sao pha lê đã ép
            int starEmpty = 0; //lỗ sao pha lê
            if (trangBi != null && daPhaLe != null) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : trangBi.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.ruby -= ruby;
                    int optionId = getOptionDaPhaLe(daPhaLe);
                    int param = getParamDaPhaLe(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : trangBi.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
    private void epSaoCaiTrang(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ ngọc hồng để thực hiện");
                return;
            }
            Item CaiTrang = null;
            Item daPhaLe = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isCaiTrang(item)) {
                    CaiTrang = item;
                }
                else if (isDaPhaLe(item)) {
                    daPhaLe = item;
                }
            }
            int star = 0; //sao pha lê đã ép
            int starEmpty = 0; //lỗ sao pha lê
            if (CaiTrang != null && daPhaLe != null) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : CaiTrang.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.ruby -= ruby;
                    int optionId = getOptionDaPhaLe(daPhaLe);
                    int param = getParamDaPhaLe(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : CaiTrang.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        CaiTrang.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        CaiTrang.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void phaLeHoaTrangBi(Player player) {
        if (!player.combineNew.itemsCombine.isEmpty()) {
            int gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            } else if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item item = player.combineNew.itemsCombine.get(0);
            if (isTrangBiPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_ITEM) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                    if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }



    private void phaLeHoaTrangBi100Lan(Player player) {
        int k = 0;
        for(int i=0 ;i < 100 ;i++ ){
            if (!player.combineNew.itemsCombine.isEmpty()) {
                int gold = player.combineNew.goldCombine;
                int gem = player.combineNew.gemCombine;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                } else if (player.inventory.gem < gem) {
                    Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                    return;
                }
                Item item = player.combineNew.itemsCombine.get(0);
                if (isTrangBiPhaLeHoa(item)) {
                    int star = 0;
                    Item.ItemOption optionStar = null;
                    for (Item.ItemOption io : item.itemOptions) {
                        if (io.optionTemplate.id == 107) {
                            star = io.param;
                            optionStar = io;
                            break;
                        }
                    }
                    if (star < MAX_STAR_ITEM) {
                        player.inventory.gold -= gold;
                        player.inventory.gem -= gem;
                        byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                        if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                            if (optionStar == null) {
                                item.itemOptions.add(new Item.ItemOption(107, 1));
                            } else {
                                optionStar.param++;
                            }
                            sendEffectSuccessCombine(player);
                            if (optionStar != null && optionStar.param >= 7) {
                                ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                        + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                            }
                            k = 1;
                        } else {
                            k = 0;
                            sendEffectFailCombine(player);
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
            if(k==1){
                Service.gI().sendThongBao(player, "Nâng sao "+(i+1)+"lần");
                break;
            } 
        }
        
    }    
    
    private void phaLect(Player player) {
        if (!player.combineNew.itemsCombine.isEmpty()) {
            int gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            } else if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item item = player.combineNew.itemsCombine.get(0);
            if (isCaiTrang(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_LEVEL_ITEM) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                    if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }
    
    private void psHoaTrangBi(Player player) {

        if (player.combineNew.itemsCombine.size() != 2) {
            Service.getInstance().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isTrangBiHacHoa()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 2046).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < COST) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để pháp sư hóa...");
                return;
            }
            if (player.inventory.ruby < RUBY) {
                Service.getInstance().sendThongBao(player, "Con cần thêm hồng ngọc để pháp sư hóa...");
                return;
            }
            player.inventory.gold -= COST;
            player.inventory.ruby -= RUBY;
            Item daHacHoa = player.combineNew.itemsCombine.stream().filter(item -> item.template.id == 2046).findFirst().get();
            Item trangBiHacHoa = player.combineNew.itemsCombine.stream().filter(Item::isTrangBiHacHoa).findFirst().get();
            if (daHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
                return;
            }
            if (trangBiHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
                return;
            }

            if (trangBiHacHoa != null) {
                for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                    if (itopt.optionTemplate.id == 223) {
                        if (itopt.param >= 8) {
                            Service.getInstance().sendThongBao(player, "Trang bị đã đạt tới giới hạn pháp sư");
                            return;
                        }
                    }
                }
            }

            if (Util.isTrue(100, 100)) {
                sendEffectSuccessCombine(player);
                List<Integer> idOptionHacHoa = Arrays.asList(219, 220, 221, 222);
                int randomOption = idOptionHacHoa.get(Util.nextInt(0, 3));
                if (!trangBiHacHoa.haveOption(223)) {
                    trangBiHacHoa.itemOptions.add(new ItemOption(223, 1));
                } else {
                    for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                        if (itopt.optionTemplate.id == 223) {
                            itopt.param += 1;
                            break;
                        }
                    }
                }
                if (!trangBiHacHoa.haveOption(randomOption)) {
                    trangBiHacHoa.itemOptions.add(new ItemOption(randomOption, Util.nextInt(5,10)));
                } else {
                    for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                        if (itopt.optionTemplate.id == randomOption) {
                            itopt.param += Util.nextInt(5,10);
                            break;
                        }
                    }
                }

                Service.getInstance().sendThongBao(player, "Bạn đã pháp sư hóa thành công");
            } else {
                sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, daHacHoa, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    private void tayHacHoaTrangBi(Player player) {

        if (player.combineNew.itemsCombine.size() != 2) {
            Service.getInstance().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isTrangBiHacHoa()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu trang bị hắc hóa");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 2047).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu đá pháp sư");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 0) {
                Service.getInstance().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= 0;
            Item buagiaihachoa = player.combineNew.itemsCombine.stream().filter(item -> item.template.id == 2047).findFirst().get();
            Item trangBiHacHoa = player.combineNew.itemsCombine.stream().filter(Item::isTrangBiHacHoa).findFirst().get();
            if (buagiaihachoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu bùa giải pháp sư");
                return;
            }
            if (trangBiHacHoa == null) {
                Service.getInstance().sendThongBao(player, "Thiếu trang bị pháp sư");
                return;
            }

            if (Util.isTrue(100, 100)) {
                sendEffectSuccessCombine(player);
                List<Integer> idOptionHacHoa = Arrays.asList(219, 220, 221, 222);

                ItemOption option_223 = new ItemOption();
                ItemOption option_219 = new ItemOption();
                ItemOption option_220 = new ItemOption();
                ItemOption option_221 = new ItemOption();
                ItemOption option_222 = new ItemOption();

                for (ItemOption itopt : trangBiHacHoa.itemOptions) {
                    if (itopt.optionTemplate.id == 223) {
                        System.out.println("223");
                        option_223 = itopt;
                    }
                    if (itopt.optionTemplate.id == 219) {
                        System.out.println("219");
                        option_219 = itopt;
                    }
                    if (itopt.optionTemplate.id == 220) {
                        System.out.println("220");
                        option_220 = itopt;
                    }
                    if (itopt.optionTemplate.id == 221) {
                        System.out.println("221");
                        option_221 = itopt;
                    }
                    if (itopt.optionTemplate.id == 222) {
                        System.out.println("222");
                        option_222 = itopt;
                    }
                }
                if (option_223 != null) {
                    trangBiHacHoa.itemOptions.remove(option_223);
                }
                if (option_219 != null) {
                    trangBiHacHoa.itemOptions.remove(option_219);
                }
                if (option_220 != null) {
                    trangBiHacHoa.itemOptions.remove(option_220);
                }
                if (option_221 != null) {
                    trangBiHacHoa.itemOptions.remove(option_221);
                }
                if (option_222 != null) {
                    trangBiHacHoa.itemOptions.remove(option_222);
                }
                Service.getInstance().sendThongBao(player, "Bạn đã tẩy thành công");
                InventoryServiceNew.gI().sendItemBags(player);
            } else {
                sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, buagiaihachoa, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        }
    }
    private void nhapNgocRong(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                    sendEffectSuccessCombine(player);
                    InventoryServiceNew.gI().addItemBag(player, nr);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
//                    sendEffectCombineDB(player, item.template.iconID);
                }
            }
        }
    }

    private void nangCapVatPham(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;//admin
            }
            Item itemDo = null;
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                int gold = player.combineNew.goldCombine;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) {
                        return;
                    }
                    if (itemDBV.quantity < countDaBaoVe) {
                        return;
                    }
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    player.inventory.gold -= gold;
                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        option.param += (option.param * 10 / 100);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                        } else {
                            optionLevel.param++;
                        }
                        if (optionLevel != null && optionLevel.param >= 5) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
                                    + "thành công " + itemDo.template.name + " lên +" + optionLevel.param);
                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     * r
     * Hiệu ứng mở item
     *
     * @param player
     */
    public void sendEffectOpenItem(Player player, short icon1, short icon2) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_ITEM);
            msg.writer().writeShort(icon1);
            msg.writer().writeShort(icon2);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thành công
     *
     * @param player
     */
    private void sendEffectSuccessCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_SUCCESS);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thất bại
     *
     * @param player
     */
    private void sendEffectFailCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_FAIL);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Gửi lại danh sách đồ trong tab combine
     *
     * @param player
     */
    private void reOpenItemCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(REOPEN_TAB_COMBINE);
            msg.writer().writeByte(player.combineNew.itemsCombine.size());
            for (Item it : player.combineNew.itemsCombine) {
                for (int j = 0; j < player.inventory.itemsBag.size(); j++) {
                    if (it == player.inventory.itemsBag.get(j)) {
                        msg.writer().writeByte(j);
                    }
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng ghép ngọc rồng
     *
     * @param player
     * @param icon
     */
    private void sendEffectCombineDB(Player player, short icon) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_DRAGON_BALL);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //--------------------------------------------------------------------------Ratio, cost combine
    private int getGoldPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 5_000_000;
            case 1:
                return 10_000_000;
            case 2:
                return 20_000_000;
            case 3:
                return 40_000_000;
            case 4:
                return 50_000_000;
            case 5:
                return 60_000_000;
            case 6:
                return 70_000_000;
            case 7:
                return 80_000_000;
            case 8:
                return 100_000_000;
            case 9:
                return 120_000_000;
            case 10:
                return 150_000_000;
            case 11:
                return 200_000_000;
            case 12:
                return 320_000_000;
            case 13:
                return 420_000_000;
            case 14:
                return 550_000_000;
            case 15:
                return 600_000_000;
            case 16:
                return 720_000_000;
        }
        return 0;
    }

//    private float getRatioPhaLeHoa(int star) { //tile dap do chi hat mit
//        switch (star) {
//            case 0:
//                return 90f;// 5tr vang
//            case 1:
//                return 70f;  // 10tr
//            case 2:
//                return 50f; // 20tr
//            case 3:
//                return 30f; // 40tr
//            case 4:
//                return 20f; // 50tr
//            case 5:
//                return 15f; // 60tr
//            case 6:
//                return 10f; // 70tr
//            case 7:
//                return 5f; // 80tr
//            case 8:
//                return 1;    // 100tr
//            case 9:
//                return 0.5f;    // 120tr
//            case 10:
//                return 0.2f;   // 150tr
//            case 11:
//                return 0.1f; // 200tr
//             case 12:
//                return 0.05f;    // 220tr
//        }
//        
//        
//        return 0;
//    }
    private float getRatioPhaLeHoa(int star) { //tile dap do chi hat mit
        switch (star) {
            case 0:
                return 100f;// 5tr vang
            case 1:
                return 100f;  // 10tr
            case 2:
                return 90f; // 20tr
            case 3:
                return 85f; // 40tr
            case 4:
                return 70f; // 50tr
            case 5:
                return 60f; // 60tr
            case 6:
                return 50f; // 70tr
            case 7:
                return 30f; // 80tr
            case 8:
                return 10f;    // 100tr
            case 9:
                return 5f;  // 10tr
            case 10:
                return 3f; // 20tr
            case 11:
                return 100f; // 40tr
            case 12:
                return 100f; // 50tr
            case 13:
                return 0.5f; // 60tr
            case 14:
                return 0.3f; // 70tr
            case 15:
                return 0.2f; // 80tr
            case 16:
                return 0.1f;    // 100tr

        }

        return 0;
    }
    
    private float getTile(int star) { //tile dap do chi hat mit
        switch (star) {
            case 0:
                return 100f;// 5tr vang
            case 1:
                return 80f;  // 10tr
            case 2:
                return 70f; // 20tr
            case 3:
                return 55f; // 40tr
            case 4:
                return 48f; // 50tr
            case 5:
                return 30f; // 60tr
            case 6:
                return 20f; // 70tr
            case 7:
                return 10f; // 80tr
            case 8:
                return 7f;    // 100tr
            case 9:
                return 5f;  // 10tr
            case 10:
                return 2f; // 20tr

        }

        return 0;
    }

    private float getRatioNangkhi(int lvkhi) { //tile dap do chi hat mit
        switch (lvkhi) {
            case 1:
                return 0f;
            case 2:
                return 0f;
            case 3:
                return 0f;
            case 4:
                return 0f;
            case 5:
                return 0f;
            case 6:
                return 0f;
            case 7:
                return 0f;
        }

        return 0;
    }

    private float getRationangbt(int lvbt) { //tile dap do chi hat mit
        switch (lvbt) {
            case 1:
                return 15f;
            case 2:
                return 10f;
            case 3:
                return 5f;

        }

        return 0;
    }

    private int getGoldnangbt(int lvbt) {
        return GOLD_BONG_TAI2 + 200000000 * lvbt;
    }

    private int getRubydnangbt(int lvbt) {
        return RUBY_BONG_TAI2 + 2000 * lvbt;
    }

    private int getcountmvbtnangbt(int lvbt) {
        return 100 + 50 * lvbt;
    }

    private boolean checkbongtai(Item item) {
        if (item.template.id == 454) {
            return true;
        }
        return false;
    }

    private int lvbt(Item bongtai) {
        switch (bongtai.template.id) {
            case 454:
                return 1;
        }

        return 0;

    }

    private short getidbtsaukhilencap(int lvbtcu) {
        switch (lvbtcu) {
            case 1:
                return 921;

        }
        return 0;
    }

    private int getGoldnangkhi(int lvkhi) {
        return GOLD_NANG_KHI + 50000000 * lvkhi;
    }

    private int getRubydnangkhi(int lvkhi) {
        return RUBY_NANG_KHI + 1000 * lvkhi;
    }

    private int getcountdnsnangkhi(int lvkhi) {
        return 10 + 5 * lvkhi;
    }

    private boolean checkctkhi(Item item) {
        if ((item.template.id >= 1136 && item.template.id <= 1140) || (item.template.id >= 1208 && item.template.id <= 1210)) {
            return true;
        }
        return false;
    }

    private int lvkhi(Item ctkhi) {
        switch (ctkhi.template.id) {
            case 1137:
                return 1;
            case 1208:
                return 2;
            case 1209:
                return 3;
            case 1210:
                return 4;
            case 1138:
                return 5;
            case 1139:
                return 6;
            case 1140:
                return 7;
        }

        return 0;

    }

    private short getidctkhisaukhilencap(int lvkhicu) {
        switch (lvkhicu) {
            case 1:
                return 1208;
            case 2:
                return 1209;
            case 3:
                return 1210;
            case 4:
                return 1138;
            case 5:
                return 1139;
            case 6:
                return 1140;
            case 7:
                return 1136;
        }
        return 0;
    }

    private int getGemPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 10;
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 40;
            case 4:
                return 50;
            case 5:
                return 60;
            case 6:
                return 70;
            case 7:
                return 80;
            case 8:
                return 90;
            case 9:
                return 100;
            case 10:
                return 150;
            case 11:
                return 170;
            case 12:
                return 200;
            case 13:
                return 350;
            case 14:
                return 400;
            case 15:
                return 550;
            case 16:
                return 700;
        }
        return 0;
    }

    private int getGemEpSao(int star) {
        switch (star) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 25;
            case 5:
                return 50;
            case 6:
                return 100;
            case 7:
                return 100;
            case 8:
                return 100;
            case 9:
                return 100;
            case 10:
                return 100;
            case 11:
                return 100;
            case 12:
                return 100;
            case 13:
                return 100;
            case 14:
                return 100;
            case 15:
                return 100;
            case 16:
                return 100;
        }
        return 0;
    }

    private double getTileNangCapDo(int level) {
        switch (level) {
            case 0:
                return 100;
            case 1:
                return 100;
            case 2:
                return 100;
            case 3:
                return 100;
            case 4:
                return 100;
            case 5:
                return 100;
            case 6:
                return 100;
            case 7: // 7 sao
                return 100;
            case 8:
                return 100;
            case 9:
                return 100;
            case 10: // 7 sao
                return 100;
            case 11: // 7 sao
                return 100;
            case 12: // 7 sao
                return 100;
        }
        return 0;
    }

    private int getCountDaNangCapDo(int level) {
        switch (level) {
            case 0:
                return 3;
            case 1:
                return 7;
            case 2:
                return 11;
            case 3:
                return 17;
            case 4:
                return 23;
            case 5:
                return 35;
            case 6:
                return 50;
            case 7:
                return 70;
            case 8:
                return 70;
            case 9:
                return 70;
            case 10:
                return 70;
            case 11:
                return 70;
            case 12:
                return 70;
        }
        return 0;
    }

    private int getCountDaBaoVe(int level) {
        return level + 1;
    }

    private int getGoldNangCapDo(int level) {
        switch (level) {
            case 0:
                return 10000;
            case 1:
                return 70000;
            case 2:
                return 300000;
            case 3:
                return 1500000;
            case 4:
                return 7000000;
            case 5:
                return 23000000;
            case 6:
                return 100000000;
            case 7:
                return 250000000;
        }
        return 0;
    }

    //--------------------------------------------------------------------------check
    private boolean isCoupleItemNangCap(Item item1, Item item2) {
        Item trangBi = null;
        Item daNangCap = null;
        if (item1 != null && item1.isNotNullItem()) {
            if (item1.template.type < 5) {
                trangBi = item1;
            } else if (item1.template.type == 14) {
                daNangCap = item1;
            }
        }
        if (item2 != null && item2.isNotNullItem()) {
            if (item2.template.type < 5) {
                trangBi = item2;
            } else if (item2.template.type == 14) {
                daNangCap = item2;
            }
        }
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isCoupleItemNangCapCheck(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isDaPhaLe(Item item) {
        return item != null && (item.template.type == 30 || (item.template.id >= 14 && item.template.id <= 20));
    }

    private boolean isTrangBiPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.type < 5 || item.template.type == 32) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isCaiTrang(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.type == 5) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private int getParamDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).param;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 5; // +5%hp
            case 19:
                return 5; // +5%ki
            case 18:
                return 5; // +5%hp/30s
            case 17:
                return 5; // +5%ki/30s
            case 16:
                return 3; // +3%sđ
            case 15:
                return 1; // +2%giáp
            case 14:
                return 1; // +5%né đòn
            default:
                return -1;
        }
    }

    private int getOptionDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).optionTemplate.id;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 77;
            case 19:
                return 103;
            case 18:
                return 80;
            case 17:
                return 81;
            case 16:
                return 50;
            case 15:
                return 94;
            case 14:
                return 108;
            default:
                return -1;
        }
    }

    /**
     * Trả về id item c0
     *
     * @param gender
     * @param type
     * @return
     */
    private int getTempIdItemC0(int gender, int type) {
        if (type == 4) {
            return 12;
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return 0;
                    case 1:
                        return 6;
                    case 2:
                        return 21;
                    case 3:
                        return 27;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return 1;
                    case 1:
                        return 7;
                    case 2:
                        return 22;
                    case 3:
                        return 28;
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return 2;
                    case 1:
                        return 8;
                    case 2:
                        return 23;
                    case 3:
                        return 29;
                }
                break;
        }
        return -1;
    }

    //Trả về tên đồ c0
    private String getNameItemC0(int gender, int type) {
        if (type == 4) {
            return "Rada cấp 1";
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return "Áo vải 3 lỗ";
                    case 1:
                        return "Quần vải đen";
                    case 2:
                        return "Găng thun đen";
                    case 3:
                        return "Giầy nhựa";
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return "Áo sợi len";
                    case 1:
                        return "Quần sợi len";
                    case 2:
                        return "Găng sợi len";
                    case 3:
                        return "Giầy sợi len";
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return "Áo vải thô";
                    case 1:
                        return "Quần vải thô";
                    case 2:
                        return "Găng vải thô";
                    case 3:
                        return "Giầy vải thô";
                }
                break;
        }
        return "";
    }

    //--------------------------------------------------------------------------Text tab combine
    private String getTextTopTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case EP_SAO_CAI_TRANG:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case PHA_LE_HOA_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case PHA_LE_HOA_TRANG_BI_100_LAN:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case PLH_CAITRANG:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case NHAP_NGOC_RONG:
                return "Ta sẽ phù phép\ncho 7 viên Ngọc Rồng\nthành 1 viên Ngọc Rồng cấp cao";
            case NANG_CAP_VAT_PHAM:
                return "Ta sẽ phù phép cho trang bị của ngươi trở lên mạnh mẽ";
            case PHAN_RA_DO_THAN_LINH:
                return "Ta sẽ phân rã \n  trang bị của người thành điểm!";
            case NANG_CAP_DO_TS:
                return "Ta sẽ nâng cấp \n  trang bị của người thành\n đồ thiên sứ!";
            case NANG_CAP_SKH_VIP:
                return "Thiên sứ nhờ ta nâng cấp \n  trang bị của người thành\n SKH VIP!";
            case NANG_CAP_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata của ngươi\n Tăng một cấp";
            case MO_CHI_SO_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata cấp 2 của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case MO_CHI_SO_Chien_Linh:
                return "Ta sẽ phù phép\ncho Chiến Linh của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case NANG_CAP_KHI:
                return "Ta sẽ phù phép\ncho Cải trang Khỉ của ngươi\nTăng một cấp!!";
            case Nang_Chien_Linh:
                return "Ta sẽ biến linh thú của ngươi \nThành Chiến Linh!!!";
            case NANG_CAP_DO_KICH_HOAT:
                return "Ta sẽ giúp ngươi\n làm điều đó";
            case CHE_TAO_TRANG_BI_TS:
                return "Chế tạo\ntrang bị thiên sứ"; 
            case PS_HOA_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\nthành trang bị pháp sư";
            case TAY_PS_HOA_TRANG_BI:
                return "Ta sẽ giúp ngươi\ntẩy trang bị\npháp sư";
            default:
                return "";
        }
    }

    private String getTextInfoTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case EP_SAO_CAI_TRANG:
                return "Chọn trang bị\n(Cải trang) có ô đặt sao pha lê\nChọn loại sao pha lê\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI_100_LAN:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case PLH_CAITRANG:
                return "Chọn trang bị\n(Cải trang)\nSau đó chọn 'Nâng cấp'";
            case NHAP_NGOC_RONG:
                return "Vào hành trang\nChọn 7 viên ngọc cùng sao\nSau đó chọn 'Làm phép'";
            case NANG_CAP_VAT_PHAM:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để nâng cấp\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHAN_RA_DO_THAN_LINH:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để phân rã\n"
                        + "Sau đó chọn 'Phân Rã'";
            case NANG_CAP_DO_TS:
                return "vào hành trang\nChọn 2 trang bị hủy diệt bất kì\nkèm 1 món đồ thần linh\n và 5 mảnh thiên sứ\n "
                        + "sẽ cho ra đồ thiên sứ từ 0-15% chỉ số"
                        + "Sau đó chọn 'Nâng Cấp'";
            case NANG_CAP_SKH_VIP:
                return "vào hành trang\nChọn 1 trang bị Thần Linh bất kì\nChọn tiếp ngẫu nhiên 2 món SKH thường \n "
                        + " đồ SKH VIP sẽ cùng loại với đồ Thần Linh\n"
                        + "Sau đó chọn 'Nâng Cấp'";
            case NANG_CAP_BONG_TAI:
                return "Tách bông tai trước khi nâng\nVào hành trang\nChọn bông tai Porata\nChọn mảnh bông tai để nâng cấp \nSau đó chọn 'Nâng cấp'";
            case MO_CHI_SO_BONG_TAI:
                return "Vào hành trang\nChọn bông tai Porata Cấp 2\nChọn Mảnh hồn bông tai số lượng 99 cái\nvà 2 Đá ngũ sắc để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case MO_CHI_SO_Chien_Linh:
                return "Vào hành trang\nChọn Chiến Linh\nChọn Đá ma thuật số lượng 99 cái\nvà x99 Hồn Thú để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_KHI:
                return "Vào hành trang\nChọn Cải trang Khỉ \nChọn Đá Ngũ Sắc để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case Nang_Chien_Linh:
                return "Vào hành trang\nChọn Linh Thú \nChọn x10 Thăng tinh thạch để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_DO_KICH_HOAT:
                return "Vào hành trang\nChọn 2 trang bị Thần Linh bất kì\n "
                        + " và 500tr vàng\n"
                        + "Sau đó chọn 'Nâng Cấp'";
            case CHE_TAO_TRANG_BI_TS:
                return "Cần 1 công thức vip\n"
                        + "Mảnh trang bị tương ứng\n"
                        + "1 đá nâng cấp (tùy chọn)\n"
                        + "1 đá may mắn (tùy chọn)\n";
            case PS_HOA_TRANG_BI:
                return "Vào hành trang\nChọn 1 trang bị có để pháp sư hoá (Cải trang, Pet, Phụ kiện đeo lưng, Linh thú) và đá pháp sư\n "
                        + "để nâng cấp chỉ số pháp sư\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case TAY_PS_HOA_TRANG_BI:
                return "vào hành trang\nChọn 1 trang bị đã pháp sư hoá và bùa tẩy pháp sư\n "
                        + "để xoá nâng cấp chỉ số pháp sư"
                        + "Sau đó chọn 'Nâng cấp'";
            default:
                return "";
        }
    }

}
