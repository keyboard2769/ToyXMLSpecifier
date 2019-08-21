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
 * 
 */

package pppmain;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import processing.data.XML;
import pppmodel.McBaseData;
import pppmodel.McDescriptionHolder;
import static pppmain.TcLogPane.ccStackln;

/**
 *
 * @author N216
 */
public class MainFrame {

  public static final String C_V_PATHSEP
   = System.getProperty("file.separator");
  
  public static final String C_V_NEWLINE
   = System.getProperty("line.separator");
  
  public static final String C_V_OS
   = System.getProperty("os.name");
  
  public static final String C_V_PWD
   = System.getProperty("user.dir");
  
  private static JFrame pbFrame=null;
  
  private MainFrame(){};
  
  public static final JFrame ccGetFrame(){return pbFrame;}//+++
  
  private static void ssSetupFrame() {
    
    //-- pre
    ScConst.ccInitDefaultFont();
    McTranslator.ccGetInstance().ccInit();
    if(ScConst.ccYesOrNoBox("load translation file??")){
      File lpFile = ScConst.ccGetFileByFileChooser('f');
      boolean lpCanLoad=ccVarifyFileForLoading(lpFile);
      boolean lpIsCSV=ccVarifyCSVFileExtension(lpFile);
      if(lpCanLoad&&lpIsCSV){
        McTranslator.ccGetInstance().ccParseCSV(lpFile);
      }//..?
    }//..?
    if(ScConst.ccYesOrNoBox("load text resource file??")){
      File lpFile = ScConst.ccGetFileByFileChooser('f');
      boolean lpCanLoad=ccVarifyFileForLoading(lpFile);
      boolean lpIsXML=ccVarifyXMLFileExtension(lpFile);
      if(lpCanLoad&&lpIsXML){
        McTranslator.ccGetInstance().ccParseXML(lpFile);
      }//..?
    }//..?
    
    //-- help menu
    JMenuItem lpInfoItem = ScConst
      .ccMyMenuItem(McTranslator.tr("info"), "--action-info", O_ACT_MNG);
    lpInfoItem.setMnemonic('i');
    
    //-- file menu 
    JMenuItem lpOperateItem = ScConst
      .ccMyMenuItem(McTranslator.tr("operate"), "--action-ope", O_ACT_MNG);
    
    JMenuItem lpLoadItem = ScConst
      .ccMyMenuItem(McTranslator.tr("load"), "--action-load", O_ACT_MNG);
    lpLoadItem.setMnemonic('o');
    
    JMenuItem lpSaveasItem = ScConst
      .ccMyMenuItem(McTranslator.tr("saveas"), "--action-saveas", O_ACT_MNG);
    lpSaveasItem.setMnemonic('s');
    
    JMenuItem lpQuitItem = ScConst
      .ccMyMenuItem(McTranslator.tr("quit"), "--action-quit", O_ACT_MNG);
    lpQuitItem.setMnemonic('q');
    
    //-- menu bar
    JMenu lpFileMenu=new JMenu(McTranslator.tr("file"));
    lpFileMenu.setMnemonic(KeyEvent.VK_F);
    lpFileMenu.add(lpOperateItem);
    lpFileMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpFileMenu.add(lpLoadItem);
    lpFileMenu.add(lpSaveasItem);
    lpFileMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpFileMenu.add(lpQuitItem);
    
    JMenu lpHelpMenu = new JMenu(McTranslator.tr("help"));
    lpHelpMenu.setMnemonic(KeyEvent.VK_H);
    lpHelpMenu.add(lpInfoItem);
    
    JMenuBar lpMenuBar = new JMenuBar();
    lpMenuBar.add(lpFileMenu);
    lpMenuBar.add(lpHelpMenu);
    
    //-- center pane
    JTabbedPane lpCenterPane = new JTabbedPane();
    ScConst.ccSetupFont(lpCenterPane);
    lpCenterPane.add(McTranslator.tr("config"), TcConfigPane.ccGetInstance());
    lpCenterPane.add(McTranslator.tr("log"), TcLogPane.ccGetInstance());
    
    //-- frame
    pbFrame = new JFrame("XML Specifier v0.01");
    pbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pbFrame.setJMenuBar(lpMenuBar);
    pbFrame.getContentPane().add(lpCenterPane);
    
    //-- frame ** packup
    Point lpOrigin=ScConst.ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(320, 240);
    pbFrame.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    pbFrame.setPreferredSize(lpWindowSize);
    pbFrame.setResizable(false);
    pbFrame.pack();
    pbFrame.setVisible(true);
    
    //-- post action
    McBaseData.ccInitContainer();
    McDescriptionHolder.getInstance().ccInit();
    
  }//+++
  
  //=== 
  
  private static final ActionListener O_ACT_MNG = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      String lpCommand = ae.getActionCommand();
      
      if(lpCommand.equals("--action-ope")){
        
        pppmodel.McBaseData.SPEC_AG_CAT.ccSetValue(4);
        ccStackln("--some value has been changed!!");
        
        return;
      }//..?
      
      if(lpCommand.equals("--action-load")){
        aaLoad();
        return;
      }//..?
      
      if(lpCommand.equals("--action-saveas")){
        aaSaveas();
        return;
      }//..?
      
      if(lpCommand.equals("--action-info")){
        ScConst.ccMessageBox(
          McTranslator.tr("--rc-help-info")
        );
        return;
      }//..?

      if(lpCommand.equals("--action-quit")){
        aaQuit();
        return;
      }//..?

      System.err.println(
        "pppmain.MainActionManager.actionPerformed()::"
        + "unhandled_action_command:"+lpCommand
      );
    }//+++
  };
  
  private static void aaQuit(){
    ssQuit(null);
  }//+++
  
  private static void ssQuit(String pxAddition){
    System.out.print("pppmain.MainActionManager.quit::");
    if(pxAddition!=null){
      System.out.println(pxAddition);
    }System.exit(0);
  }//+++
  
  private static void aaLoad(){
    File lpFile = ScConst.ccGetFileByFileChooser('f');
    if(!ccVarifyFileForLoading(lpFile)){return;}
    if(!ccVarifyXMLFileExtension(lpFile)){return;}
    XML lpXML = McConst.ccLoadXML(lpFile);
    McBaseData.ccParseXML(lpXML);
  }//+++
  
  private static void aaSaveas(){
    File lpFile = ScConst.ccGetFileByFileChooser('f');
    if(!ccVarifyFileForSaving(lpFile)){return;}
    if(!ccVarifyXMLFileExtension(lpFile)){return;}
    XML lpXML=McBaseData.ccGenerateXML();
    if(lpXML==null){
      ccStackln("[BAD]failed to generate xml object from base data.");
      return;
    }//..?
    lpXML.save(lpFile,null);
  }//+++
  
  //=== util
  
  private static boolean ccVarifyFileForLoading(File pxFile){
    if(pxFile==null){ccStackln("[CANCEL]invalid selection");return false;}
    if(!pxFile.isAbsolute()){ccStackln("[BAD]bad path");return false;}
    if(!pxFile.exists()){ccStackln("[BAD]bad accessment");return false;}
    if(pxFile.length()>65535){
      ccStackln("[BAD]file size exceeded limit");return false;
    }//..?
    ccStackln("[accepted]file size[byte]:",pxFile.length());
    return true;
  }//+++
  
  private static boolean ccVarifyFileForSaving(File pxFile){
    if(pxFile==null){ccStackln("[CANCEL]invalid selection");return false;}
    if(!pxFile.isAbsolute()){ccStackln("[BAD]bad path");return false;}
    if(pxFile.exists()){
      boolean lpDoOverwrite=ScConst.ccYesOrNoBox(
        "WARN:you wanna overwrite that file??"
      );
      if(!lpDoOverwrite){
        ccStackln("[ACC]canceld with confirmation.");
        return false;
      }else{return true;}//..?
    }else{return true;}//..?
  }//+++
  
  private static boolean ccVarifyXMLFileExtension(File pxFile){
    return ccVarifyFileExtension(pxFile, "^.+(.xml)$");
  }//+++
  
  private static boolean ccVarifyCSVFileExtension(File pxFile){
    return ccVarifyFileExtension(pxFile, "^.+(.csv)$");
  }//+++
  
  private static boolean ccVarifyFileExtension(File pxFile, String pxRegex){
    if(pxFile==null){ccStackln("[CANCEL]invalid selection");return false;}
    if(!VcConst.ccIsValidString(pxRegex)){
      System.err.println("pppmain.MainFrame.ccVarifyFileExtension()::"
        + "regex string invalid!!");
      return false;
    }//+++
    String lpName=pxFile.getName();
    if(!VcConst.ccIsValidString(lpName)){
      ccStackln("[BAD]bad name");return false;
    }//+++
    if(!lpName.matches(pxRegex)){
      ccStackln("[BAD]file name dosent match",lpName);
      return false;
    }//..?
    ccStackln("[accepted]",lpName);
    return true;
  }//+++
  
  //=== entry

  public static void main(String[] args) {
    System.out.println("TemlateConsoleFrame.main()::activate");
    ScConst.ccApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {ssSetupFrame();}//+++
    });
    System.out.println("TemlateConsoleFrame.main()::over");
  }//++!
  
  /* ** 
   * ** plan
   * 
   * [todo]::make all singleton class member non static
   * [todo]::let pre loading more automatic
   * 
   * 
   * 
   *
   * ** done
   * 
   * 
   * 
   *  
   * 
   */

}//***eof
