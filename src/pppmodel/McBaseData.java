/*
 * Copyright (C) 2019 N216
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package pppmodel;

import java.util.ArrayList;
import java.util.HashMap;
import pppmain.McConst;
import pppmain.TcConfigPane;
import pppmain.VcConst;
import processing.data.XML;

/**
 *
 * @author N216
 */
public final class McBaseData {
  
  private static McBaseData SELF = new McBaseData();
  public static final McBaseData ccGetInstance() {return SELF;}//+++
  private McBaseData(){}//++!
  
  //===
  
  public static final MiRecord O_DUMMY_RECORD
    = new McIntegerValueRecord(0xFFFF, "...", 0) 
  { 
    @Override public String ccGetType(){return "dummy";}//+++
    @Override public void ccSetValue(String pxVal){}//+++
    @Override public String ccGetStringValue(){return "...";}//+++
  };
  
  //===
  
  public static final int C_MAX_USER_ITEM = 8;
  private final HashMap<String, MiRecord> cmUserKeyMap
   = new HashMap<>(C_MAX_USER_ITEM);
  private final HashMap<Integer, MiRecord> cmUserIDMap
   = new HashMap<>(C_MAX_USER_ITEM);
  
  public static final McUserRecord USER_NAME
    = new McUserRecord(1,"--user-name", "OOXXComp");
  public static final McUserRecord USER_SA_NUMBER
    = new McUserRecord(2,"--user-sanumber", "SA9876P99");
  public static final McUserRecord USER_LOCALE
    = new McUserRecord(3,"--user-locale", "the what ever place");
  public static final McUserRecord USER_EXPIRE_DATE
    = new McUserRecord(4,"--user-expire", "88-12-27");
  
  //===
  
  public static final int C_MAX_SPEC_ITEM = 64;
  private final HashMap<String, MiRecord> cmSpecKeyMap
   = new HashMap<>(C_MAX_SPEC_ITEM);
  private final HashMap<Integer, MiRecord> cmSpecIDMap
   = new HashMap<>(C_MAX_SPEC_ITEM);
  
  public static final McSpecRecord SPEC_AG_CAT
    = new McSpecRecord(1,"--spec-agcat", 6);
  public static final McSpecRecord SPEC_FR_CAT
    = new McSpecRecord(2, "--spec-frcat", 2);
  public static final McSpecRecord SPEC_AS_CAT
    = new McSpecRecord(3, "--spec-ascat", 1);
  
  public static final McSpecRecord SPEC_VB_MODE
    = new McSpecRecord(4, "--spec-vbmode", 2);
  public static final McSpecRecord SPEC_VB_CTRL
    = new McSpecRecord(5, "--spec-vbctrl", 2);
  
  //===
  
  public static final int C_MAX_POWER_ITEM = 128;
  private final HashMap<String, MiRecord> cmPowerKeyMap
   = new HashMap<>(C_MAX_POWER_ITEM);
  private final HashMap<Integer, MiRecord> cmPowerIDMap
   = new HashMap<>(C_MAX_POWER_ITEM);
  
  public static final McPowerRecord POWER_FILLER_FEEDER
   = new McPowerRecord(7, "--power-fillerFeeder", 5500);
  public static final McPowerRecord POWER_FILLER_ELEVATOR
   = new McPowerRecord(8, "--power-fillerElevator", 4000);
  public static final McPowerRecord POWER_FILLER_SILOSCREW
   = new McPowerRecord(9, "--power-fillerSiloscrew", 7500);
  
  //===
  
  public static void ccInitContainer(){
    
    ssAddUserRecord(USER_NAME);
    ssAddUserRecord(USER_SA_NUMBER);
    ssAddUserRecord(USER_LOCALE);
    ssAddUserRecord(USER_EXPIRE_DATE);
    
    ssAddSpecRecord(SPEC_AG_CAT);SPEC_AG_CAT.ccSetFilter(4, 2);
    ssAddSpecRecord(SPEC_FR_CAT);SPEC_FR_CAT.ccSetFilter(1, 2);
    ssAddSpecRecord(SPEC_AS_CAT);SPEC_AS_CAT.ccSetFilter(1, 2);
    ssAddSpecRecord(SPEC_VB_CTRL);SPEC_VB_CTRL.ccSetFilter(0, 4);
    ssAddSpecRecord(SPEC_VB_MODE);SPEC_VB_MODE.ccSetFilter(0, 4);
    
    ssAddPowerRecord(POWER_FILLER_FEEDER);
    ssAddPowerRecord(POWER_FILLER_ELEVATOR);
    ssAddPowerRecord(POWER_FILLER_SILOSCREW);
    
  }//+++
  
  //===
  
  public static int ccGetUserListCount(){
    return C_MAX_USER_ITEM;
  }//+++
  
  private static void ssAddUserRecord(MiRecord pxRecord){
    SELF.cmUserIDMap.put(pxRecord.ccGetID(), pxRecord);
    SELF.cmUserKeyMap.put(pxRecord.ccGetKey(), pxRecord);
  }//+++
  
  public static MiRecord ccGetUserRecord(int pxIndex){
    return SELF.cmUserIDMap.getOrDefault(pxIndex, O_DUMMY_RECORD);
  }//+++
  
  public static MiRecord ccGetUserRecord(String pxKey){
    return SELF.cmUserKeyMap.getOrDefault(pxKey, O_DUMMY_RECORD);
  }//+++
  
  //===
  
  public static int ccGetSpecListCount(){
    return C_MAX_SPEC_ITEM;
  }//+++
  
  private static void ssAddSpecRecord(MiRecord pxRecord){
    SELF.cmSpecIDMap.put(pxRecord.ccGetID(), pxRecord);
    SELF.cmSpecKeyMap.put(pxRecord.ccGetKey(), pxRecord);
  }//+++
  
  public static MiRecord ccGetSpecRecord(int pxIndex){
    return SELF.cmSpecIDMap.getOrDefault(pxIndex, O_DUMMY_RECORD);
  }//+++
  
  public static MiRecord ccGetSpecRecord(String pxKey){
    return SELF.cmSpecKeyMap.getOrDefault(pxKey, O_DUMMY_RECORD);
  }//+++
  
  //===
  
  public static int ccGetPowerListCount(){
    return C_MAX_POWER_ITEM;
  }//+++
  
  private static void ssAddPowerRecord(MiRecord pxRecord){
    SELF.cmPowerIDMap.put(pxRecord.ccGetID(), pxRecord);
    SELF.cmPowerKeyMap.put(pxRecord.ccGetKey(), pxRecord);
  }//+++
  
  public static MiRecord ccGetPowerRecord(int pxIndex){
    return SELF.cmPowerIDMap.getOrDefault(pxIndex, O_DUMMY_RECORD);
  }//+++
  
  public static MiRecord ccGetPowerRecord(String pxKey){
    return SELF.cmPowerKeyMap.getOrDefault(pxKey, O_DUMMY_RECORD);
  }//+++
  
  //=== 
  
  public static MiRecord ccGetRecord(int pxFolder, int pxID){
    HashMap<Integer,MiRecord> lpTarget=null;
    switch(pxFolder){
      case 0:lpTarget=SELF.cmUserIDMap;break;
      case 1:lpTarget=SELF.cmSpecIDMap;break;
      case 2:lpTarget=SELF.cmPowerIDMap;break;
    }
    if(lpTarget==null){return O_DUMMY_RECORD;}
    return lpTarget.getOrDefault(pxID, O_DUMMY_RECORD);
  }//+++
  
  //===
  
  public static final XML ccGenerateXML(){
    XML lpXML=McConst.ccNewXML();
    if(lpXML==null){return null;}
    ArrayList<MiRecord> lpRollerList = new ArrayList<>();
    lpRollerList.addAll(SELF.cmUserKeyMap.values());
    lpRollerList.addAll(SELF.cmSpecKeyMap.values());
    lpRollerList.addAll(SELF.cmPowerKeyMap.values());
    for(MiRecord it:lpRollerList){
      XML lpInfoXML = lpXML.addChild(it.ccGetType());
      lpInfoXML.setString("key", it.ccGetKey());
      lpInfoXML.setString("value", it.ccGetStringValue());
    }//+++
    return lpXML;
  }//+++
  
  public static final void ccParseXML(XML pxXML){
    VcConst.ccPrintln("count", pxXML.getChildCount());
    String lpType,lpKey,lpValue;
    int lpTypeCheck;
    MiRecord lpRecordBUF;
    for(XML it:pxXML.getChildren()){
      lpType = it.getName();
      lpTypeCheck=-1;
      if(lpType.equals("user")){lpTypeCheck=0;}
      if(lpType.equals("spec")){lpTypeCheck=1;}
      if(lpType.equals("power")){lpTypeCheck=2;}
      if(lpTypeCheck>=0){
        lpKey=it.getString("key", "<?>");
        switch(lpTypeCheck){
          case 0:lpRecordBUF=ccGetUserRecord(lpKey);break;
          case 1:lpRecordBUF=ccGetSpecRecord(lpKey);break;
          case 2:lpRecordBUF=ccGetPowerRecord(lpKey);break;
          default:lpRecordBUF=O_DUMMY_RECORD;break;
        }//..?
        lpValue=it.getString("value", "<?>");
        lpRecordBUF.ccSetValue(lpValue);
        TcConfigPane.ccGetInstance().ccUpdateTable();
      }//..?
    }//..~
  }//+++

 }//***eof
