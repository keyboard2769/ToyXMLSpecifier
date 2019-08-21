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

import pppmain.VcConst;

/**
 *
 * @author N216
 */
public class McUserRecord implements MiRecord{
  
  private static final String C_TYPE = "user";
  
  private final int cmID;
  private final String cmKey;
  private String cmValue;
  
  public McUserRecord(int pxID, String pxKey, String pxValue){
    cmID=pxID;
    cmKey=pxKey;
    cmValue=pxValue;
  }//..!
  
  //===

  @Override public String ccGetType() {
    return C_TYPE;
  }//+++

  @Override public int ccGetID() {
    return cmID;
  }//+++

  @Override public String ccGetKey() {
    return cmKey;
  }//+++
  
  //=== **
  
  @Override public void ccSetValue(boolean pxVal) {}//+++
  @Override public void ccSetValue(int pxVal) {}//+++
  @Override public void ccSetValue(float pxVal) {}//+++
  @Override public void ccSetValue(String pxVal) {
    if(!VcConst.ccIsValidString(pxVal)){return;}
    cmValue=pxVal;
  }//+++
  
  //=== **

  @Override public boolean ccGetBoolValue() {return false;}//+++
  @Override public int ccGetIntValue() {return 0;}//+++
  @Override public float ccGetFloatValue() {return 0f;}//+++
  @Override public String ccGetStringValue() {
    return cmValue;
  }//+++
  
}//***eof
