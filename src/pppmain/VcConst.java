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

/**
 *
 * @author N216
 */
public class VcConst {

  private static VcConst self = null;
  static public final VcConst ccGetInstance() {
    if(self==null){self=new VcConst();return self;}
    else{return self;}
  }//+++
  private VcConst(){}//++!

  //===
  
  private static final boolean C_DOS_LOG = false;
  
  static public final void ccLogln(String pxTag, Object pxVal){
    if(!C_DOS_LOG){return;}
    ccPrintln(pxTag, pxVal);
  }//+++
  
  static public final void ccPrintln(String pxTag, Object pxVal){
    System.out.println("-- "+pxTag+":"+pxVal.toString());
  }//+++
  
  //===
  
  static public final String ccNulloutString(String pxInput){
    return pxInput==null?"":pxInput;
  }//+++
  
  static public final boolean ccIsValidString(String pxInput){
    if(pxInput==null){return false;}
    return !pxInput.isEmpty();
  }//+++
  
  static public final boolean ccIsIntegerString(String pxNum){
    return pxNum.matches("^[+-]?(([1-9][0-9]{0,9})|(0))$");
  }//+++

  static public final boolean ccIsFloatString(String pxNum){
    return pxNum.matches("^[+-]?(([1-9][0-9]{0,9})|(0))([.][0-9]{1,2})?$");
  }//+++

  static public boolean ccParseBoolString(String pxSource){
    if(!ccIsValidString(pxSource)){return false;}
    if(pxSource.equals("true") || pxSource.equals("TRUE")){return true;}
    if(pxSource.equals("yes") || pxSource.equals("YES")){return true;}
    if(pxSource.equals("on") || pxSource.equals("ON")){return true;}
    if(ccParseIntegerString(pxSource)==0){return false;}
    return false;
  }//+++
  
  static public int ccParseIntegerString(String pxSource){

    //-- pre judge
    if(!VcConst.ccIsValidString(pxSource)){return 0;}

    //-- judge input ** pxSource
    boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
    boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
    if(!lpIsFloat && !lpIsInteger){return 0;}

    //-- transform
    int lpRes=0;
    if(lpIsFloat){
      lpRes=(int)(Float.parseFloat(pxSource));
    }//..?
    if(lpIsInteger){
      lpRes=Integer.parseInt(pxSource);
    }//..?
    return lpRes;

  }//+++
  
  static public float ccParseFloatString(String pxSource){

    //-- pre judge
    if(!VcConst.ccIsValidString(pxSource)){return 0.0f;}

    //-- judge input ** pxSource
    boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
    boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
    if(!lpIsFloat && !lpIsInteger){return 0.0f;}

    //-- transform
    float lpRes=0.0f;
    if(lpIsFloat){
      lpRes=Float.parseFloat(pxSource);
    }//..?
    if(lpIsInteger){
      lpRes=(float)(Integer.parseInt(pxSource));
    }//..?
    return lpRes;

  }//+++
  
  static public float ccRoundForOneAfter(float pxSource){
    return (float)(
      ((int)(pxSource*10f))
    )/10f;
  }//+++

  static public float ccRoundForTwoAfter(float pxSource){
    return (float)(
      ((int)(pxSource*100f))
    )/100f;
  }//+++

 }//***eof
