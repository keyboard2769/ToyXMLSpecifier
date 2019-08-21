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

public class ZcRangedModel {
  
  protected int cmMin,cmMax;
  
  public ZcRangedModel(int pxMin, int pxRange){
    ccSetRange(pxMin, pxRange);
  }//+++
  
  //===
  
  public final void ccSetRange(int pxRange){
    cmMax=cmMin+pxRange&0x7FFFFFFF;
  }//+++
  

  public final void ccSetRange(int pxMin, int pxRange){
    cmMin=pxMin;cmMax=pxMin+pxRange&0x7FFFFFFF;
  }//+++
  
  //===
  
  public final int ccLimit(int pxSource){
    int lpRes=pxSource>cmMax?cmMax:pxSource;
    lpRes=lpRes<cmMin?cmMin:lpRes;
    return lpRes;
  }//+++
  
  public final int ccWarp(int pxSource) {
    int lpRes=pxSource>cmMax?cmMin:pxSource;
    lpRes=lpRes<cmMin?cmMax:lpRes;
    return lpRes;
  }//+++
  
  //===
  
  public final boolean ccContains(int pxSource){
    return (pxSource>=cmMin)&&(pxSource<cmMax);
  }//+++
  
  public final int ccGetMin(){
    return cmMin;
  }//+++
  
  public final int ccGetMax(){
    return cmMax;
  }//+++
  
  public final int ccGetRange(){
    return cmMax-cmMin;
  }//+++
  
}//***eof
