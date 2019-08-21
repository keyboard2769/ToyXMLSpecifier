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

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import static pppmain.MainFrame.C_V_OS;
import static pppmain.MainFrame.C_V_PWD;
import static pppmain.MainFrame.C_V_NEWLINE;

/**
 *
 * @author N216
 */
public final class TcLogPane extends JPanel {

  private static final TcLogPane SELF = new TcLogPane();
  public static final TcLogPane ccGetInstance(){return SELF;}//+++
  
  //===
  
  private static JTextArea pbArea;
  private static JTextField pbField;
  
  private TcLogPane(){
    super(new BorderLayout());
    ssSetupLogPane();
  }//++!

  //===
  
  private void ssSetupLogPane(){
    
    //-- component ** area
    String lpHellow="init:mini_kosui 0.0.1.0\n";
    lpHellow+="on:"+C_V_OS+C_V_NEWLINE;
    lpHellow+="at:"+C_V_PWD+C_V_NEWLINE;
    lpHellow+="standby::"+C_V_NEWLINE;
    pbArea=new JTextArea(lpHellow);
    ScConst.ccSetupShowerArea(pbArea);
    JScrollPane lpCenterPane=new JScrollPane(pbArea);
    
    //-- component ** field
    pbField = new JTextField("");
    pbField.addKeyListener(new KeyListener() {
      @Override public void keyTyped(KeyEvent ke) {}
      @Override public void keyPressed(KeyEvent ke) {}
      @Override public void keyReleased(KeyEvent ke) {
        int lpCharCode=(int)ke.getKeyChar();
        switch(lpCharCode){
          case 0x0A:
            if(pbField.getText().isEmpty()){return;}
            ccStackln("[echo]"+pbField.getText());
            pbField.setText("");
          break;
          default:break;
        }//..?
      }//+++
    });
    
    //-- packing
    add(lpCenterPane,BorderLayout.CENTER);
    add(pbField,BorderLayout.PAGE_END);
    setBorder(BorderFactory.createEtchedBorder());
    
  }//+++
  
  public static final void ccStackln(String pxLine){
    ccStackln(pxLine, null);
  }//+++
  
  public static final void ccStackln(String pxTag, Object pxVal){
    if(pxTag==null){return;}
    if(pxVal==null){
      pbArea.append(pxTag+C_V_NEWLINE);
    }else{
      pbArea.append(pxTag+":"+pxVal.toString()+C_V_NEWLINE);
    }//..?
    int lpLength = pbArea.getText().length();
    pbArea.setSelectionStart(lpLength-1);
    pbArea.setSelectionEnd(lpLength);
  }//+++

 }//***eof
