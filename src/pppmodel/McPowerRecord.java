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

public class McPowerRecord extends McIntegerValueRecord{
  
  private static final String C_TYPE = "power";

  public McPowerRecord(int pxID, String pxPowerName, int pxWatt) {
    super(pxID, pxPowerName, pxWatt);
    ccSetFilter(0, 199999);
  }//..!
  
  //===

  @Override public String ccGetType() {
    return C_TYPE;
  }//+++

  //===

  @Override public void ccSetValue(float pxVal) {
    ccSetValue(
      ((int)(pxVal*1000f))
    );
  }//+++

  @Override public void ccSetValue(String pxVal) {
    ccSetValue(VcConst.ccParseFloatString(pxVal));
  }//+++
  
  //===

  @Override public float ccGetFloatValue() {
    return VcConst.ccRoundForTwoAfter(
      ((float)ccGetIntValue())/1000f
    );
  }//+++

  @Override public String ccGetStringValue() {
    return Float.toString(ccGetFloatValue());
  }//+++
  
}//***eof
