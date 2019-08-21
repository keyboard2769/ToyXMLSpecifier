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

import java.util.HashMap;

/**
 *
 * @author N216
 */
public final class McDescriptionHolder {

  private static final McDescriptionHolder SELF = new McDescriptionHolder();
  public static final McDescriptionHolder getInstance(){return SELF;}//+++
  private McDescriptionHolder(){}//++!

  //===
  
  private final HashMap<MiRecord, String> cmDescriptionMap
   = new HashMap<>(256);
  
  //===
  
  public final void ccInit(){
    
    cmDescriptionMap.put(McBaseData.SPEC_VB_MODE,"--rc-desp-vbmode");
    cmDescriptionMap.put(McBaseData.SPEC_VB_CTRL,"--rc-desp-vbctrl");
  
  }//..!
  
  //===
  
  static public final String get(MiRecord pxTarget){
    return SELF.cmDescriptionMap.getOrDefault(pxTarget, "...");
  }//...
  
 }//***eof
