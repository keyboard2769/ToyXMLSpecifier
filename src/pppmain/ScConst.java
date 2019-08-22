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

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import javax.swing.JComponent;

/**
 *
 * @author N216
 */
public class ScConst {
  
  public static final String C_M_INVALID = "";

  private static ScConst self = null;
  public static final ScConst ccGetInstance() {
    if(self==null){self=new ScConst();return self;}
    else{return self;}
  }//+++
  
  //===
  
  //===
  
  private ScConst(){}//++!

  //===
  
  private static Font cmDefaultFont=null;
  
  public static final void ccInitDefaultFont(){
    cmDefaultFont=new Font(Font.DIALOG_INPUT,  Font.PLAIN, 12);
  }//+++
  
  public static final Font ccGetDefaultFont(){
    return cmDefaultFont;
  }//+++
  
  public static final void ccSetupFont(JComponent pxTarget){
    if(cmDefaultFont==null){return;}
    if(pxTarget==null){return;}
    pxTarget.setFont(cmDefaultFont);
  }//+++
  
  //===
  
  public static final boolean ccIsEDT(){
    if(SwingUtilities.isEventDispatchThread()){return true;}
    System.err.println("--err::out_from_edt");
    return false;
  }//+++
  
  public static void ccApplyLookAndFeel(int pxIndex, boolean pxRead) {

    String lpTarget = UIManager.getCrossPlatformLookAndFeelClassName();

    //-- getting
    if (pxIndex >= 0) {
      UIManager.LookAndFeelInfo[] lpInfos = UIManager.getInstalledLookAndFeels();
      if (pxRead) {
        System.out.println("--installed lookNfeel: 0->");
        int cnt=0;
        for (UIManager.LookAndFeelInfo it : lpInfos) {
          System.out.print("["+Integer.toString(cnt)+"] ");
          System.out.println(it.getClassName());
          cnt++;
        }//..~
      }//..?
      int lpIndex=pxIndex>(lpInfos.length-1)?lpInfos.length-1:pxIndex;
      lpTarget = lpInfos[lpIndex].getClassName();
    }//..?

    //-- applying
    try {
      UIManager.setLookAndFeel(lpTarget);
    }catch (ClassNotFoundException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }catch (InstantiationException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }catch (IllegalAccessException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }catch (UnsupportedLookAndFeelException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }//..%

  }//+++
  
  public static final void ccSetupShowerArea(JTextArea pxTarget){
    pxTarget.setBackground(Color.LIGHT_GRAY);
    pxTarget.setDisabledTextColor(Color.DARK_GRAY);
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
  }//+++
  
  public static final Point ccGetScreenInitPoint(){
    Point lpDummyPoint = null;
    Point lpInitPoint = null;
    for (
      GraphicsDevice lpDevice:
      GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()
    ){
      if (lpDummyPoint == null) {
        lpDummyPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      } else if (lpInitPoint == null) {
        lpInitPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      }//..?
    }//..~
    if (lpInitPoint == null) {lpInitPoint = lpDummyPoint;}
    if (lpInitPoint == null) {lpInitPoint = new Point(0,0);}
    return lpInitPoint;
  }//+++
  
  static final public JMenuItem ccMyMenuItem(
    String pxText,
    String pxCommand,
    ActionListener pxListner
  ){
    JMenuItem lpRes;
    if(VcConst.ccIsValidString(pxText)){
      lpRes = new JMenuItem(pxText);
    }else{
      lpRes = new JMenuItem("<?>");
    }//..?
    if(VcConst.ccIsValidString(pxCommand)){
      lpRes.setActionCommand(pxCommand);
    }//..?
    if(pxListner!=null){
      lpRes.addActionListener(pxListner);
    }//..?
    return lpRes;
  }//+++
  
  
  public static final void ccMessageBox(String pxMessage){
    if(!ccIsEDT()){return;}
    JOptionPane.showMessageDialog(MainFrame.ccGetFrame(),pxMessage);
  }//+++
  
  public static final void ccWarnBox(String pxMessage){
    if(!ccIsEDT()){return;}
    JOptionPane.showMessageDialog(
      MainFrame.ccGetFrame(),
      pxMessage,
      MainFrame.ccGetFrame()!=null?MainFrame.ccGetFrame().getTitle():"!!",
      JOptionPane.WARNING_MESSAGE
    );
  }//+++
  
  public static final void ccErrorBox(String pxMessage){
    if(!ccIsEDT()){return;}
    JOptionPane.showMessageDialog(
      MainFrame.ccGetFrame(),
      pxMessage,
      MainFrame.ccGetFrame()!=null?MainFrame.ccGetFrame().getTitle():"!!",
      JOptionPane.ERROR_MESSAGE
    );
  }//+++
  
  public static final boolean ccYesOrNoBox(String pxMessage){
    if(!ccIsEDT()){return false;}
    int i=JOptionPane.showConfirmDialog(
      MainFrame.ccGetFrame(),
      pxMessage,
      MainFrame.ccGetFrame()!=null?MainFrame.ccGetFrame().getTitle():"!!",
      JOptionPane.YES_NO_OPTION
    );
    return i==0;
  }//+++
  
  public static final
  String ccGetStringByInputBox(String pxBrief, String pxDefault){
    if(!ccIsEDT()){return "";}
    String lpRes=JOptionPane.showInputDialog(
      MainFrame.ccGetFrame(), pxBrief, pxDefault
    );
    if(lpRes!=null){return lpRes;}
    else{return "";}
  }//+++
  
  //===
  
  private static final JFileChooser
    O_FILE_CHOOSER = new JFileChooser(MainFrame.C_V_PWD);
  
  public static final File ccGetFileByFileChooser(char pxMode){
    if(ccIsEDT()){
      int lpMode=JFileChooser.FILES_AND_DIRECTORIES;
      switch(pxMode){
        case 'f':lpMode=JFileChooser.FILES_ONLY;break;
        case 'd':lpMode=JFileChooser.DIRECTORIES_ONLY;break;
        default:break;
      }//..?
      O_FILE_CHOOSER.setApproveButtonText("Confirm");
      O_FILE_CHOOSER.updateUI();
      O_FILE_CHOOSER.setFileSelectionMode(lpMode);
      int lpFlag=O_FILE_CHOOSER
        .showDialog(MainFrame.ccGetFrame(), null);
      if(lpFlag==JFileChooser.APPROVE_OPTION){
        File lpFile=O_FILE_CHOOSER.getSelectedFile();
        return lpFile;
      }else{return null;}
    }else{return null;}
  }//+++
  
  public static final String ccGetPathByFileChooser(char pxMode){ 
    File lpFile=ccGetFileByFileChooser(pxMode);
    if(lpFile==null){return C_M_INVALID;}
    if(!lpFile.isAbsolute()){return C_M_INVALID;}
    return lpFile.getAbsolutePath();
  }//+++
  
 }//***eof
