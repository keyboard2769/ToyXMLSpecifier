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

package pppmain;

import java.io.File;
import java.util.HashMap;
import processing.data.XML;
import processing.data.Table;
import processing.data.TableRow;

/**
 *
 * @author N216
 */
public class McTranslator {
  
  public static final String C_FIELDKEY_TYPE = "#type";
  public static final String C_FIELDKEY_KEY = "key";
  public static final String C_FIELDKEY_EN = "en";
  public static final String C_FIELDKEY_JP = "jp";
  public static final String C_FIELDKEY_ZH = "zh";
  public static final String C_FIELDVALUE_TR = "tr";
  
  private static final McTranslator SELF = new McTranslator();
  public static final McTranslator ccGetInstance(){return SELF;}//+++\
  private McTranslator(){}
  
  //===
  
  private final HashMap<String, String> lpEnglishDict=new HashMap<>();
  private final HashMap<String, String> lpChineseDict=new HashMap<>();
  private final HashMap<String, String> lpJapaneseDict=new HashMap<>();
  private char cmMode='c';
  
  public final void ccInit(){
    
    lpEnglishDict.put("", "");
    lpChineseDict.put("", "");
    lpJapaneseDict.put("", "");
  
  }//..!
  
  public final void ccSetMode(char pxMode_ejc){
    cmMode=pxMode_ejc;
  }//+++
  
  //===
  
  public final void ccParseXML(File pxTarget){
    XML lpXML=McConst.ccLoadXML(pxTarget);
    if(lpXML==null){return;}
    /* 4 */VcConst.ccLogln("count", lpXML.getChildCount());
    String lpKey,lpEn,lpJp,lpZh;
    for(XML it:lpXML.getChildren()){
      if(it.getName().equals(C_FIELDVALUE_TR)){
        lpKey=it.getString(C_FIELDKEY_KEY, "<nn>");
        lpEn=McConst.ccGetXMLChildContent(it, C_FIELDKEY_EN, "...");
        lpJp=McConst.ccGetXMLChildContent(it, C_FIELDKEY_JP, "...");
        lpZh=McConst.ccGetXMLChildContent(it, C_FIELDKEY_ZH, "...");
        /* 4 */VcConst.ccLogln("key", lpKey);
        /* 4 */VcConst.ccLogln("en", lpEn);
        /* 4 */VcConst.ccLogln("jp", lpJp);
        /* 4 */VcConst.ccLogln("zh", lpZh);
        lpEnglishDict.put(lpKey, lpEn);
        lpJapaneseDict.put(lpKey, lpJp);
        lpChineseDict.put(lpKey, lpZh);
      }//..?
    }//..~
  }//+++
  
  public final void ccParseCSV(File pxTarget){
    
    //-- loading
    Table lpCSV=McConst.ccLoadCSV(pxTarget);
    if(lpCSV==null){return;}
    
    
    //-- validation
    boolean lpColumnCheck=McConst.ccVerifyCSVColumn(lpCSV, new String[]{
      C_FIELDKEY_TYPE,C_FIELDKEY_KEY,
      C_FIELDKEY_EN,C_FIELDKEY_JP,C_FIELDKEY_ZH
    });
    if(!lpColumnCheck){return;}
    
    //-- looping
    String lpType,lpKey,lpEn,lpJp,lpZh;
    for(TableRow it:lpCSV.rows()){
      lpType=McConst.ccGetColumnValue(it, C_FIELDKEY_TYPE, "...");
      lpKey=McConst.ccGetColumnValue(it, C_FIELDKEY_KEY, "...");
      lpEn=McConst.ccGetColumnValue(it, C_FIELDKEY_EN, "...");
      lpJp=McConst.ccGetColumnValue(it, C_FIELDKEY_JP, "...");
      lpZh=McConst.ccGetColumnValue(it, C_FIELDKEY_ZH, "...");
      if(lpType.equals(C_FIELDVALUE_TR)&&VcConst.ccIsValidString(lpKey)){
        /* 4 */VcConst.ccLogln(lpKey, lpEn);
        lpEnglishDict.put(lpKey, lpEn);
        lpJapaneseDict.put(lpKey, lpJp);
        lpChineseDict.put(lpKey, lpZh);
      }//..?
    }//..~
    
  }//+++
  
  //===
  
  public static final String tr(String pxSource){
    if(pxSource==null){return pxSource;}
    if(pxSource.equals("")){return pxSource;}
    if(pxSource.equals("...")){return pxSource;}
    switch(SELF.cmMode){
      case 'e':return SELF.lpEnglishDict.getOrDefault(pxSource, pxSource);
      case 'j':return SELF.lpJapaneseDict.getOrDefault(pxSource, pxSource);
      case 'c':return SELF.lpChineseDict.getOrDefault(pxSource, pxSource);
      default:return pxSource;
    }//..?
  }//+++
  
}//**eof
