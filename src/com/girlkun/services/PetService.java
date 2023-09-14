package com.girlkun.services;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.player.NewPet;
import com.girlkun.models.player.Pet;
import com.girlkun.models.player.Player;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;

public class PetService {

    private static PetService i;

    public static PetService gI() {
        if (i == null) {
            i = new PetService();
        }
        return i;
    }

    public void createNormalPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, false,false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Xin hãy thu nhận làm đệ tử");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createNormalPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, false,false);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Xin hãy thu nhận làm đệ tử");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createMabuPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, true, false,false);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Oa oa oa...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createMabuPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, true, false,false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Oa oa oa...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createBerusPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, true,false);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Thần hủy diệt hiện thân tất cả quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createBerusPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, true,false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Thần hủy diệt hiện thân tất cả quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    } public void createNewPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, false,true);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createNewPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false,false,true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
       public void createBillPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetBill(player, false, false,false, false, true, false, false, false);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createBillPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetBill(player, false,false, false, false, true, false, false, false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createGokuPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetGoku(player, false, false,false, false, false, true, false, false);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createGokuPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetGoku(player, false, false,false, false, false, true, false, false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    
    public void createTanjiroPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewTanjiroPet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    
    public void createHashibiraPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewHashibiraPet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    
    public void createInosukePet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewInosukePet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    
    public void createZenitsuPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewZenitsuPet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createNezukoPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewNezukoPet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createCumberPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetCumber(player, false, false,false, false, false, false, true, false, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createCumberPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetCumber(player, false, false,false, false, false, false, false, true);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createCumberGoldPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetCumberGold(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createCumber4Pet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetCumber4(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createCumberSuper4Pet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetCumberSuper4(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createBrl(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetBrl(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createBillbingoPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createBillbingoPet(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createChillConPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetChillCon(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createChillBoPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetChillBo(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createBrl1Pet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPetBrl1(player, false, false,false, false, false, false, false, true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
    public void createPicPet(Player player, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false, false,true);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }

    public void createPicPet(Player player, int gender, byte... limitPower) {
        new Thread(() -> {
            try {
                createNewPet(player, false,false,true, (byte) gender);
                if (limitPower != null && limitPower.length == 1) {
                    player.pet.nPoint.limitPower = limitPower[0];
                }
                Thread.sleep(1000);
                Service.getInstance().chatJustForMe(player, player.pet, "Sư Phụ SooMe hiện thân tụi m quỳ xuống...");
            } catch (Exception e) {
            }
        }).start();
    }
     private void createNewPet(Player player, boolean isMabu, boolean isBerus,boolean isPic, byte... gender) {
        int[] data = isMabu ? isPic ?  getDataPetMabu():getDataPetPic() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (isMabu ? "Mabư" : isBerus ? "Berus" : isPic ? "Đệ Tử Píc" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = isMabu || isBerus|| isPic ? 1500000 : 2000;
        pet.typePet = (byte) (isMabu ? 1 : isBerus ? 2 :isPic ? 3 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
     private void createNewPetBill(Player player,boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = isbill ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (isbill ? "Đệ Tử Uub" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = isbill ? 1500000 : 2000;
        pet.typePet = (byte) (isbill ? 4 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetGoku(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iswhis ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iswhis ? "Đệ Goku Vô Cựa" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iswhis ? 1500000 : 2000;
        pet.typePet = (byte) (iswhis ? 5 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetCumber(Player player,boolean iszamat,  boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = isgoku ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (isgoku ? "Đệ Cumber Black" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = isgoku ? 1500000 : 2000;
        pet.typePet = (byte) (isgoku ? 6 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetCumberGold(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Cumber Gold" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 7 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createBillbingoPet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Bill" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 8 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetChillCon(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Chill" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 9 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetChillBo(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Chill Cấp 2" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 10 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewTanjiroPet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Tử Tanjiro" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 11 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewHashibiraPet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Tử Hashibira" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 12 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewInosukePet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Tử Inosuke" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 13 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewZenitsuPet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Tử Zenitsu" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 14 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewNezukoPet(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Tử Nezuko" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 15 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetCumber4(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Cumber 4.0" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 16 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetCumberSuper4(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean iscumber, byte... gender){
        int[] data = iscumber ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (iscumber ? "Đệ Cumber Super 4.0" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = iscumber ? 1500000 : 2000;
        pet.typePet = (byte) (iscumber ? 17 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetBrl(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean isBroly, byte... gender){
        int[] data = isBroly ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (isBroly ? "Đệ Broly Super" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = isBroly ? 1500000 : 2000;
        pet.typePet = (byte) (isBroly ? 18 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    private void createNewPetBrl1(Player player, boolean iszamat, boolean isMabu, boolean isBerus,boolean isPic, boolean isbill, boolean iswhis, boolean isgoku, boolean isBroly, byte... gender){
        int[] data = isBroly ? getDataPetMabu() : getDataPetNormal();
        Pet pet = new Pet(player);
        pet.name = "$" + (isBroly ? "Đệ Broly" : "Đệ tử");
        pet.gender = (gender != null && gender.length != 0) ? gender[0] : (byte) Util.nextInt(0, 2);
        pet.id = -player.id;
        pet.nPoint.power = isBroly ? 1500000 : 2000;
        pet.typePet = (byte) (isBroly ? 19 : 0);
        pet.nPoint.stamina = 1000;
        pet.nPoint.maxStamina = 1000;
        pet.nPoint.hpg = data[0];
        pet.nPoint.mpg = data[1];
        pet.nPoint.dameg = data[2];
        pet.nPoint.defg = data[3];
        pet.nPoint.critg = data[4];
        for (int i = 0; i < 7; i++) {
            pet.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        pet.playerSkill.skills.add(SkillUtil.createSkill(Util.nextInt(0, 2) * 2, 1));
        for (int i = 0; i < 3; i++) {
            pet.playerSkill.skills.add(SkillUtil.createEmptySkill());
        }
        pet.nPoint.setFullHpMp();
        player.pet = pet;
    }
    public void changeNormalPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createNormalPet(player, gender, limitPower);
    }

    public void changeNormalPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createNormalPet(player, limitPower);
    }

    public void changeMabuPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createMabuPet(player, limitPower);
    }

    public void changeMabuPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createMabuPet(player, gender, limitPower);
    }
    public void changeBerusPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBerusPet(player, limitPower);
    }

    public void changeBerusPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBerusPet(player, gender, limitPower);
    } public void changeGokuPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createGokuPet(player, limitPower);
    }

    public void changeGokuPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createGokuPet(player, gender, limitPower);
    } public void changeBillPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBillPet(player, limitPower);
    }

    public void changeBillPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBillPet(player, gender, limitPower);
    }
    public void changeTanjiroPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createTanjiroPet(player, gender, limitPower);
    }
    public void changeHashibiraPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createHashibiraPet(player, gender, limitPower);
    }
    public void changeInosukePet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createInosukePet(player, gender, limitPower);
    }
    public void changeZanitsuPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createZenitsuPet(player, gender, limitPower);
    }
    public void changeNezukoPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createNezukoPet(player, gender, limitPower);
    }
     public void changeCumberPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumberPet(player, limitPower);
    }

    public void changeCumberPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumberPet(player, gender, limitPower);
    }
     public void changeCumberGoldPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumberGoldPet(player, limitPower);
    }

    public void changeCumberGoldPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumberGoldPet(player, gender, limitPower);
    }
    public void changeCumber4Pet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumber4Pet(player, gender, limitPower);
    }
    public void changeCumberSuper4Pet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createCumberSuper4Pet(player, gender, limitPower);
    }
    public void changePicPet(Player player) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createPicPet(player, limitPower);
    }

    public void changePicPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createPicPet(player, gender, limitPower);
    }
    public void changeBillBiNgoPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBillbingoPet(player, gender, limitPower);
    }
    public void changeBrl(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBrl(player, gender, limitPower);
    }
    public void changeChillConPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createChillConPet(player, gender, limitPower);
    }
    public void changeChillBoPet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createChillBoPet(player, gender, limitPower);
    }
    public void changeBrl1Pet(Player player, int gender) {
        byte limitPower = player.pet.nPoint.limitPower;
        if (player.fusion.typeFusion != ConstPlayer.NON_FUSION) {
            player.pet.unFusion();
        }
        ChangeMapService.gI().exitMap(player.pet);
        player.pet.dispose();
        player.pet = null;
        createBrl1Pet(player, gender, limitPower);
    }
    public void changeNamePet(Player player, String name) {
        try {
            if (!InventoryServiceNew.gI().isExistItemBag(player, 400)) {
                Service.getInstance().sendThongBao(player, "Bạn cần thẻ đặt tên đệ tử, mua tại Santa");
                return;
            } else if (Util.haveSpecialCharacter(name)) {
                Service.getInstance().sendThongBao(player, "Tên không được chứa ký tự đặc biệt");
                return;
            } else if (name.length() > 10) {
                Service.getInstance().sendThongBao(player, "Tên quá dài");
                return;
            }
            ChangeMapService.gI().exitMap(player.pet);
            player.pet.name = "$" + name.toLowerCase().trim();
            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItemBag(player, 400), 1);
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().chatJustForMe(player, player.pet, "Cảm ơn sư phụ đã đặt cho con tên " + name);
                } catch (Exception e) {
                }
            }).start();
        } catch (Exception ex) {

        }
    }

    private int[] getDataPetNormal() {
        int[] hpmp = {1700, 1800, 1900, 2000, 2100, 2200};
        int[] petData = new int[5];
        petData[0] = Util.nextInt(40, 105) * 20; //hp
        petData[1] = Util.nextInt(40, 105) * 20; //mp
        petData[2] = Util.nextInt(20, 45); //dame
        petData[3] = Util.nextInt(9, 50); //def
        petData[4] = Util.nextInt(0, 2); //crit
        return petData;
    }

    private int[] getDataPetMabu() {
        int[] hpmp = {1700, 1800, 1900, 2000, 2100, 2200};
        int[] petData = new int[5];
        petData[0] = Util.nextInt(40, 105) * 20; //hp
        petData[1] = Util.nextInt(40, 105) * 20; //mp
        petData[2] = Util.nextInt(50, 120); //dame
        petData[3] = Util.nextInt(9, 50); //def
        petData[4] = Util.nextInt(0, 2); //crit
        return petData;
    }
     private int[] getDataPetPic() {
        int[] hpmp = {1800, 1900, 2000, 2100, 2200,2300};
        int[] petData = new int[5];
        petData[0] = Util.nextInt(40, 115) * 20; //hp
        petData[1] = Util.nextInt(40, 115) * 20; //mp
        petData[2] = Util.nextInt(70, 140); //dame
        petData[3] = Util.nextInt(9, 50); //def
        petData[4] = Util.nextInt(0, 2); //crit
        return petData;
    }
     private int[] getDataPetBroly() {
        int[] hpmp = {1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000};
        int[] petData = new int[5];
        petData[0] = Util.nextInt(100, 300) * 10; //hp
        petData[1] = Util.nextInt(100, 300) * 10; //mp
        petData[2] = Util.nextInt(100, 250); //dame
        petData[3] = Util.nextInt(100, 300) * 10; //def
        petData[4] = Util.nextInt(1, 15); //crit
        return petData;
    }
    public static void Pet2(Player pl, int h, int b, int l, String name) {
        if (pl.newpet != null) {
            pl.newpet.dispose();
//            pl.newpet1.dispose();
        }
        pl.newpet = new NewPet(pl, (short) h, (short) b, (short) l, name);
        //pl.newpet1 = new NewPet(pl, (short) h, (short) b, (short) l);
        pl.newpet.name = "$";
     //   pl.newpet1.name = "$";
        pl.newpet.gender = pl.gender;
       //   pl.newpet1.gender = pl.gender;
        pl.newpet.nPoint.tiemNang = 1;
        // pl.newpet1.nPoint.tiemNang = 1;
        pl.newpet.nPoint.power = 1;
      //  pl.newpet1.nPoint.power = 1;
        pl.newpet.nPoint.limitPower = 1;
   //      pl.newpet1.nPoint.limitPower = 1;
        pl.newpet.nPoint.hpg = 500000000;
    //    pl.newpet1.nPoint.hpg = 500000000;
        pl.newpet.nPoint.mpg = 500000000;
    //    pl.newpet1.nPoint.mpg = 500000000;
        pl.newpet.nPoint.hp = 500000000;
        pl.newpet.nPoint.mp = 500000000;
        pl.newpet.nPoint.dameg = 1;
        pl.newpet.nPoint.defg = 1;
        pl.newpet.nPoint.critg = 1;
        pl.newpet.nPoint.stamina = 1;
        pl.newpet.nPoint.setBasePoint();
        pl.newpet.nPoint.setFullHpMp();
//        pl.newpet1.nPoint.hp = 500000000;
//        pl.newpet1.nPoint.mp = 500000000;
//        pl.newpet1.nPoint.dameg = 1;
//        pl.newpet1.nPoint.defg = 1;
//        pl.newpet1.nPoint.critg = 1;
//        pl.newpet1.nPoint.stamina = 1;
//        pl.newpet1.nPoint.setBasePoint();
//        pl.newpet1.nPoint.setFullHpMp();
    }
 
    //--------------------------------------------------------------------------
}
