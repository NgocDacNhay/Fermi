/*
 * Beo Sờ tu đi ô
*/
package com.arriety.MaQuaTang;

import com.girlkun.database.GirlkunDB;
import com.girlkun.models.Template;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.player.Player;
import com.girlkun.services.NpcService;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Administrator
 */
public class MaQuaTangManager {
     public String name;
    public final ArrayList<MaQuaTang> listGiftCode = new ArrayList<>();
    
     
    private static MaQuaTangManager instance;
    public static MaQuaTangManager gI() {
        if (instance == null) {
            instance = new MaQuaTangManager();
        }
        return instance;
    }
      public void init() {
        try(  Connection con = GirlkunDB.getConnection();) {
          
            PreparedStatement ps = con.prepareStatement("SELECT * FROM giftcode");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MaQuaTang giftcode = new MaQuaTang();
                ArrayList<Integer> tempListIdPlayer = new ArrayList<>();
                String tempDBListIdPlayers = null;
                giftcode.code = rs.getString("code");
                giftcode.countLeft = rs.getInt("count_left");
                giftcode.datecreate = rs.getTimestamp("datecreate");
                giftcode.dateexpired = rs.getTimestamp("expired");
                String dbListIdPlayer = rs.getString("ListIdPlayers");
                JSONArray jar = (JSONArray) JSONValue.parse(rs.getString("detail"));
                if (jar != null) {
                    for (int i = 0; i < jar.size(); ++i) {
                        JSONObject jsonObj = (JSONObject) jar.get(i);
                        giftcode.detail.put(Integer.parseInt(jsonObj.get("id").toString()), Integer.parseInt(jsonObj.get("quantity").toString()));
                        jsonObj.clear();
                    }
                } 
               
                JSONArray option= (JSONArray) JSONValue.parse(rs.getString("itemoption"));
                  Logger.log("Done-------------------"+option.toString());
                if(option!=null){
                    for(int u = 0;u<option.size();u++){
                    JSONObject jsonobject = (JSONObject) option.get(u);
                            giftcode.option.add(new ItemOption(Integer.parseInt(jsonobject.get("id").toString()),Integer.parseInt(jsonobject.get("param").toString())));
                                                      jsonobject.clear();
                           
                            }
                    
                            }
                if(!dbListIdPlayer.isEmpty()){
                    tempDBListIdPlayers = dbListIdPlayer = removeCharAt(dbListIdPlayer, 0);
                    tempDBListIdPlayers = dbListIdPlayer = removeCharAt(dbListIdPlayer, dbListIdPlayer.length() - 1);
                    String[] resultTempDBListIdPlayers = tempDBListIdPlayers.split(",");
                    for(String item : resultTempDBListIdPlayers){
                        if(!item.isEmpty())
                            tempListIdPlayer.add(Integer.parseInt(item));
                    }
                    giftcode.listIdPlayer = tempListIdPlayer;
                }
            
                listGiftCode.add(giftcode);
            }
            con.close();
        } catch (Exception erorlog) {
            erorlog.printStackTrace();
        }
    }   
      public void updateGiftcodeListIdPlayer(ArrayList<Integer> ListIdPlayer, String code){
        try {
            String sql = "UPDATE giftcode SET ListIdPlayers=? WHERE code=?";
            ArrayList<Integer> deDupStringList = new ArrayList<>(new HashSet<>(ListIdPlayer));
            GirlkunDB.executeUpdate(sql, JSONValue.toJSONString(deDupStringList), code);
        } catch (Exception e) {
            // TODO: handle exception
        }
      }
      public void sizeList(Player pl){
        Service.getInstance().sendThongBao(pl, ""+MaQuaTang.class);
    }
       public MaQuaTang checkUseGiftCode(int idPlayer, String code) {
        for (MaQuaTang giftCode: listGiftCode) {
                if (giftCode.code.equals(code) && giftCode.countLeft > 0 && !giftCode.isUsedGiftCode(idPlayer)) {
                giftCode.countLeft -= 1;
                giftCode.addPlayerUsed(idPlayer);
                updateGiftcodeListIdPlayer(giftCode.listIdPlayer, code);
                return giftCode;
            }
        }
        return null;
    }
      public void checkInfomationGiftCode(Player p) {
        StringBuilder sb = new StringBuilder();
        for (MaQuaTang giftCode: listGiftCode) {
            sb.append("Code: ").append(giftCode.code).append(", Số lượng: ").append(giftCode.countLeft);
        }
        
        NpcService.gI().createTutorial(p, 5073, sb.toString());
    }

    public static String removeCharAt(String s, int pos){
        return s.substring(0, pos) + s.substring(pos + 1);
    }

}