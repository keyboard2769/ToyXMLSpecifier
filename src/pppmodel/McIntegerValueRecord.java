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

abstract class McIntegerValueRecord implements MiRecord{
  
  private final int cmID;
  private final String cmKey;
  private int cmValue;
  private ZcRangedModel cmFilterRange;
  
  public McIntegerValueRecord(int pxID, String pxKey, int pxValue){
    cmID=pxID;
    cmKey=pxKey;
    cmFilterRange=null;
    ssSetValue(pxValue);
  }//..!
  
  //===
  
  private void ssSetValue(int pxValue){
    if(cmFilterRange==null){
      cmValue=pxValue;
    }else{
      cmValue=cmFilterRange.ccLimit(pxValue);
    }//..?
  }//+++
  
  public final void ccSetFilter(int pxMin, int pxRange){
    if(cmFilterRange==null){
      cmFilterRange=new ZcRangedModel(pxMin, pxRange);
    }else{
      cmFilterRange.ccSetRange(pxMin, pxRange);
    }//..?
  }//+++
  
  //===

  @Override public int ccGetID() {
    return cmID;
  }//+++

  @Override public String ccGetKey() {
    return cmKey;
  }//+++
  
  //=== **
  
  @Override public void ccSetValue(boolean pxVal) {
    ssSetValue(pxVal?1:0);
  }//+++
  
  @Override public void ccSetValue(int pxVal) {
    ssSetValue(pxVal);
  }//+++
  
  @Override public void ccSetValue(float pxVal) {
    ssSetValue((int)pxVal);
  }//+++
  
  @Override public void ccSetValue(String pxVal) {
    ssSetValue(VcConst.ccParseIntegerString(pxVal));
  }//+++
  
  //=== **

  @Override public boolean ccGetBoolValue() {
    return cmValue!=0;
  }//+++
  
  @Override public int ccGetIntValue() {
    return cmValue;
  }//+++
  
  @Override public float ccGetFloatValue() {
    return (float)cmValue;
  }//+++
  
  @Override public String ccGetStringValue() {
    return Integer.toString(cmValue);
  }//+++
  
}//***eof
