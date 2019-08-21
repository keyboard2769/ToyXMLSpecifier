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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import processing.data.XML;
import processing.data.Table;
import static pppmain.TcLogPane.ccStackln;
import processing.data.TableRow;

/**
 *
 * @author N216
 */
public final class McConst {

  private static final McConst SELF = new McConst();
  public static final McConst ccGetInstance(){return SELF;}//+++
  private McConst(){}//++!

  //===
  
  public static final XML ccNewXML(){
    XML lpXML=null;
    try {
      lpXML = XML.parse("<root/>");
    } catch (Exception e) {
      e.printStackTrace();
      ccStackln("[BAD]",e.getLocalizedMessage());
      lpXML=null;
    }//..?
    return lpXML;
  }//+++
  
  public static final XML ccLoadXML(File pxFile){
    if(pxFile==null){return null;}
    XML lpXML = null;
    try {
       lpXML = new XML(pxFile);
    } catch (Exception e) {
      ccStackln("[ECC]",e.getLocalizedMessage());
      lpXML = null;
    }//..?
    if(lpXML == null){
      ccStackln("[BAD]failed to generate xml object from choosen file.");
    }//..?
    return lpXML;
  }//+++
  
  public static final Table ccLoadCSV(File pxFile){
    if(pxFile==null){return null;}
    Table lpCSV=null;
    try {
      lpCSV = new Table(pxFile, "header");
    } catch (Exception e) {
      e.printStackTrace();
      ccStackln("[BAD]",e.getLocalizedMessage());
      lpCSV=null;
    }//..?
    if(lpCSV==null){
      ccStackln("[BAD]failed to generate table object from choosen file.");
    }//..?
    return lpCSV;
  }//+++
  
  public static final
  boolean ccValidateCSVColumn(Table pxCSV, String[] pxColumns){
    
    //-- precheck
    if(pxCSV==null){return false;}
    if(pxColumns==null){return false;}
    if(pxColumns.length<=1){
      System.err.println("pppmain.McConst.ccValidateCSVColumn()::"
       + "single column form is forbidden.");
      return false;
    }//..?
    
    //-- additional check
    int lpColumnCount=pxCSV.getColumnCount();
    int lpRowCount=pxCSV.getRowCount();
    /* 4 */VcConst.ccLogln("column count", lpColumnCount);
    /* 4 */VcConst.ccLogln("row clunt", lpRowCount);
    if(lpColumnCount<=1 || lpRowCount==0){
      VcConst.ccLogln("csvc::too few row and column", 0);
      return false;
    }//..?
    
    //-- loop
    Set<String> lpTitleSet
      = new HashSet<>(Arrays.asList(pxCSV.getColumnTitles()));
    boolean lpTester=true;
    /* 4 */for(String it:lpTitleSet){VcConst.ccLogln("csv-c", it);}//..~
    for(String it:pxColumns){
      /* 4 */VcConst.ccLogln("csvc-given", it);
      lpTester&=lpTitleSet.contains(it);
    }//..~
    return lpTester;
    
  }//+++
  
  static public final
  String ccGetColumnValue(TableRow pxRow, String pxColumn, String pxOrDefault){
    if(pxRow==null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxColumn)){return pxOrDefault;}
    String lpCell;
    try {
      lpCell = pxRow.getString(pxColumn);
    } catch (Exception e) {
      System.err.println("pppmain.McConst.ccGetColumnValue()::"
        + e.getMessage());
      lpCell=null;
    }//..?
    
    //[head]::
    
    if(lpCell==null){return pxOrDefault;}
    else{return lpCell;}
  }//+++

 }//***eof
