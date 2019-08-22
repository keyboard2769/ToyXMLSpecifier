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
import java.io.File;
import java.util.LinkedList;
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
    //-- pre ** init nessesarity
    ScConst.ccInitDefaultFont();
    McTranslator.ccGetInstance().ccInit();
    
    //-- pre ** load resource
    //-- pre ** load resource ** local const
    String lpRelativeResourceFolderHD
      = C_V_PATHSEP+"srcc"+C_V_PATHSEP+"pppresource"+C_V_PATHSEP;
    String lpAbsoluteResourceRootHD
      = C_V_PWD+lpRelativeResourceFolderHD;
    String lpTextResourceFileName="rc.xml";
    String lpTranslationResourceFileName="tr.csv";
    String[] lpDesResouceFileName={
      lpTextResourceFileName,
      lpTranslationResourceFileName
    };
    /* 4 */VcConst.ccLogln("mf-pwd", C_V_PWD);
    /* 4 */VcConst.ccLogln("mf-abpp", lpAbsoluteResourceRootHD);
    
    //-- pre ** load resource ** veryfy and retake
    File lpFolderHD=new File(lpAbsoluteResourceRootHD);
    boolean lpIsHardcodedValid=ccVerifyResourceFileExistence
      (lpFolderHD, lpDesResouceFileName);
    File lpTextResource=null, lpTranslationResource=null;
    if(lpIsHardcodedValid){
      lpTextResource = new File
        (lpAbsoluteResourceRootHD+C_V_PATHSEP+lpTextResourceFileName);
      lpTranslationResource = new File
        (lpAbsoluteResourceRootHD+C_V_PATHSEP+lpTranslationResourceFileName);
    }else{
      if(
        !ScConst.ccYesOrNoBox
          ("failed to locate resource file\n choose your self?")
      ){
        ssQuit("--user denialed to locate resource manually");
      }//..?
      File lpFolderFC;
      while(true){
        lpFolderFC = ScConst.ccGetFileByFileChooser('d');
        if(lpFolderFC==null){
          ScConst.ccWarnBox("this may load no resource file");
          break;
        }//..?
        boolean lpIsSelectedValid=ccVerifyResourceFileExistence
          (lpFolderFC, lpDesResouceFileName);
        if(lpIsSelectedValid){
          lpTextResource=new File
            (lpFolderFC.getAbsolutePath()
              +C_V_PATHSEP+lpTextResourceFileName);
          lpTranslationResource=new File
            (lpFolderFC.getAbsolutePath()
              +C_V_PATHSEP+lpTranslationResourceFileName);
          break;
        }else{
          ScConst.ccErrorBox("cant locate resource file");
        }//..?
      }//..~
    }//..?
    
    //-- pre ** load resource ** veryfy and parse
    if(lpTextResource==null||lpTranslationResource==null){
      ScConst.ccWarnBox("booting with out resource");
    }else{
      
      //-- 
      boolean lpIsTextResourceValid
       = ccVerifyFileForLoading(lpTextResource)
       &&ccVerifyXMLFileExtension(lpTextResource);
      if(lpIsTextResourceValid){
        McTranslator.ccGetInstance().ccParseXML(lpTextResource);
      }else{
        ScConst.ccErrorBox("not loading text resource cuz it did pass");
      }//..?
      
      //--
      boolean lpIsTranslationResourceValid
       = ccVerifyFileForLoading(lpTranslationResource)
       &&ccVerifyCSVFileExtension(lpTranslationResource);
      if(lpIsTranslationResourceValid){
        McTranslator.ccGetInstance().ccParseCSV(lpTranslationResource);
      }else{
        ScConst.ccErrorBox("not loading translation cuz it did pass");
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
    lpFileMenu.setMnemonic('f');
    lpFileMenu.add(lpOperateItem);
    lpFileMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpFileMenu.add(lpLoadItem);
    lpFileMenu.add(lpSaveasItem);
    lpFileMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpFileMenu.add(lpQuitItem);
    
    JMenu lpHelpMenu = new JMenu(McTranslator.tr("help"));
    lpHelpMenu.setMnemonic('h');
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
    if(!ccVerifyFileForLoading(lpFile)){return;}
    if(!ccVerifyXMLFileExtension(lpFile)){return;}
    XML lpXML = McConst.ccLoadXML(lpFile);
    McBaseData.ccParseXML(lpXML);
  }//+++
  
  private static void aaSaveas(){
    File lpFile = ScConst.ccGetFileByFileChooser('f');
    if(!ccVerifyFileForSaving(lpFile)){return;}
    if(!ccVerifyXMLFileExtension(lpFile)){return;}
    XML lpXML=McBaseData.ccGenerateXML();
    if(lpXML==null){
      ccStackln("[BAD]failed to generate xml object from base data.");
      return;
    }//..?
    lpXML.save(lpFile,null);
  }//+++
  
  //=== util
  
  private static final
  boolean ccVerifyResourceFileExistence(File pxFolder, String[] pxDesFileName){
    if(!ccVerifyFolder(pxFolder)){return false;}
    if(pxDesFileName==null){return false;}
    LinkedList<String> lpFolderList = new LinkedList<>();
    for(File it:pxFolder.listFiles()){
      lpFolderList.add(it.getName());
    }//..~
    boolean lpRes=true;
    for(String it:pxDesFileName){
      lpRes&=lpFolderList.contains(it);
    }//..~
    return lpRes;
  }//+++
  
  public static final void ccReadupFolderContent(File pxFolder){
    if(!ccVerifyFolder(pxFolder)){return;}
    for(File it:pxFolder.listFiles()){
      VcConst.ccPrintln("r-subf", it.getName());
    }//+++
  }//+++
  
  public static final boolean ccVerifyFolder(File pxFolder){
    if(pxFolder==null){ccStackln("[CANCEL]invalid selection");return false;}
    if(!pxFolder.isAbsolute()){ccStackln("[BAD]bad path");return false;}
    if(!pxFolder.isDirectory()){ccStackln("[BAD]not folder");return false;}
    return true;
  }//+++
  
  private static boolean ccVerifyFileForLoading(File pxFile){
    if(pxFile==null){ccStackln("[CANCEL]invalid selection");return false;}
    if(!pxFile.isAbsolute()){ccStackln("[BAD]bad path");return false;}
    if(!pxFile.exists()){ccStackln("[BAD]bad accessment");return false;}
    if(pxFile.length()>65535){
      ccStackln("[BAD]file size exceeded limit");return false;
    }//..?
    ccStackln("[accepted]file size[byte]:",pxFile.length());
    return true;
  }//+++
  
  private static boolean ccVerifyFileForSaving(File pxFile){
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
  
  private static boolean ccVerifyXMLFileExtension(File pxFile){
    return ccVerifyFileExtension(pxFile, "^.+(.xml)$");
  }//+++
  
  private static boolean ccVerifyCSVFileExtension(File pxFile){
    return ccVerifyFileExtension(pxFile, "^.+(.csv)$");
  }//+++
  
  private static boolean ccVerifyFileExtension(File pxFile, String pxRegex){
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
  
}//***eof
