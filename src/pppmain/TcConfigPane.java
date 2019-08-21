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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import pppmodel.McBaseData;
import pppmodel.McDescriptionHolder;

import pppmodel.McTableAdaptor;
import pppmodel.MiRecord;

/**
 *
 * @author N216
 */
public final class TcConfigPane extends JPanel{

  private static final TcConfigPane SELF = new TcConfigPane();
  public static final TcConfigPane ccGetInstance(){return SELF;}//+++
  
  //===
  
  private final JButton cmModifySW;
  private final JList<String> cmList;
  private final JTable cmTable;
  private final JTextArea cmArea;
    
  //===
  
  private final McTableAdaptor cmInfoTable = new McTableAdaptor(){
    @Override public int getColumnCount() {return 2;}
    @Override public int getRowCount() {
      return McBaseData.ccGetUserListCount();
    }//+++
    @Override public String getColumnName(int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:return McTranslator.tr("key");
        case 1:return McTranslator.tr("value");
      }//..?
    }//+++
    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:
          String lpKey=McBaseData.ccGetUserRecord(pxRowIndex).ccGetKey();
          return McTranslator.tr(lpKey);
        case 1:return McBaseData.ccGetUserRecord(pxRowIndex).ccGetStringValue();
      }//..?
    }//+++
  };
  
  private final McTableAdaptor cmSpecTable= new McTableAdaptor(){
    @Override public int getColumnCount() {return 2;}
    @Override public int getRowCount() {
      return McBaseData.ccGetSpecListCount();
    }//+++
    @Override public String getColumnName(int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:return McTranslator.tr("key");
        case 1:return McTranslator.tr("value");
      }//..?
    }//++
    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:
          String lpKey=McBaseData.ccGetSpecRecord(pxRowIndex).ccGetKey();
          return McTranslator.tr(lpKey);
        case 1:return McBaseData.ccGetSpecRecord(pxRowIndex).ccGetStringValue();
      }//..?
    }//+++
  };
  
  private final McTableAdaptor cmPowerTable= new McTableAdaptor(){
    @Override public int getColumnCount() {return 2;}
    @Override public int getRowCount() {
      return McBaseData.ccGetPowerListCount();
    }//+++
    @Override public String getColumnName(int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:return McTranslator.tr("--tablecolumn-item");
        case 1:return McTranslator.tr("--tablecolumn-power");
      }//..?
    }//++
    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:default:
          String lpKey=McBaseData.ccGetPowerRecord(pxRowIndex).ccGetKey();
          return McTranslator.tr(lpKey);
        case 1:
          return McBaseData.ccGetPowerRecord(pxRowIndex).ccGetStringValue();
      }//..?
    }//+++
  };
  
  private final MouseAdapter cmLocalMouseListener = new MouseAdapter() {
    @Override public void mousePressed(MouseEvent e) {
      
      //-- getting
      Object lpSource = e.getSource();
      int lpListIndex=-1;
      int lpTableIndex=-1;
      
      //-- list to table
      if(lpSource instanceof JList){
        lpListIndex=cmList.getSelectedIndex();
        ccUpdateTable(lpListIndex);
        cmArea.setText("...");
      }//..?
      
      //-- table to decription
      if(lpSource instanceof JTable){
        
        //-- get index
        lpListIndex=cmList.getSelectedIndex();
        lpTableIndex=cmTable.getSelectedRow();
        if(lpListIndex<0 || lpTableIndex <0){
          cmArea.setText("...");
          return;
        }//..?
        
        //-- get record
        MiRecord lpSelectRecord
         = McBaseData.ccGetRecord(lpListIndex,lpTableIndex);
        if(lpSelectRecord.equals(McBaseData.O_DUMMY_RECORD)){
          cmArea.setText("...");
          return;
        }//..?
        
        //-- show desp
        cmArea.setText(McTranslator.tr(
          McDescriptionHolder.get(lpSelectRecord)
        ));
      
      }//..?
      
    }//+++
  };
  
  private final ActionListener cmLocalActionListener = new ActionListener() {
    @Override public void actionPerformed(ActionEvent e) {
      
      //-- get index
      int lpListIndex=cmList.getSelectedIndex();
      int lpTableIndex=cmTable.getSelectedRow();
      if(lpListIndex<0 || lpTableIndex <0){
        ScConst.ccMessageBox("selection error!!");
        return;
      }//..?
      
      //-- get record
      MiRecord lpSelectRecord
       = McBaseData.ccGetRecord(lpListIndex,lpTableIndex);
      if(lpSelectRecord.equals(McBaseData.O_DUMMY_RECORD)){
        ScConst.ccMessageBox("empty record!!");
        return;
      }//..?
      
      //-- get and set input
      String lpInput = ScConst.ccGetStringByInputBox("input value", "");
      lpSelectRecord.ccSetValue(lpInput);
      ccUpdateTable();
      
    }//+++
  };
  
  //===
  
  private TcConfigPane(){
    super(new BorderLayout());
    
    //-- button
    cmModifySW=new JButton(McTranslator.tr("MOD"));
    cmModifySW.setActionCommand("--button-config-mod");
    cmModifySW.setMnemonic('d');
    cmModifySW.addActionListener(cmLocalActionListener);
    
    //-- left
    cmList=new JList<>(new String[]{
      McTranslator.tr("user"),
      McTranslator.tr("spec"),
      McTranslator.tr("power")
    });
    cmList.addMouseListener(cmLocalMouseListener);
    cmList.setSelectedIndex(0);
    ScConst.ccSetupFont(cmList);
    JScrollPane lpListPane = new JScrollPane(cmList);
    Dimension lpListSize=new Dimension(96, 72);
    lpListPane.setMinimumSize(lpListSize);
    lpListPane.setPreferredSize(lpListSize);
    
    cmArea=new JTextArea("...");
    ScConst.ccSetupShowerArea(cmArea);
    cmArea.setWrapStyleWord(true);
    cmArea.setLineWrap(true);
    JScrollPane lpDescriptionPane=new JScrollPane(cmArea);
    
    JPanel lpLeftPane = new JPanel(new GridLayout(2, 0));
    lpLeftPane.add(lpListPane);
    lpLeftPane.add(lpDescriptionPane);
    
    //-- center
    cmTable=new JTable(cmInfoTable);
    ScConst.ccSetupFont(cmTable);
    cmTable.addMouseListener(cmLocalMouseListener);
    cmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    cmTable.getTableHeader().setReorderingAllowed(false);
    JScrollPane lpTablePane = new JScrollPane(cmTable);
    ScConst.ccSetupFont(lpTablePane);
    
    //-- pack
    add(lpLeftPane,BorderLayout.LINE_START);
    add(lpTablePane,BorderLayout.CENTER);
    add(cmModifySW,BorderLayout.PAGE_END);
    
  }//++!

  //===
  
  public final void ccUpdateTable(int pxIndex){
    switch(pxIndex){
      default:case 0:cmTable.setModel(cmInfoTable);break;
      case 1:cmTable.setModel(cmSpecTable);break;
      case 2:cmTable.setModel(cmPowerTable);break;
    }//..?
    ccUpdateTable();
  }//+++
  
  public final void ccUpdateTable(){
    cmTable.repaint();
    cmTable.requestFocus();
  }//+++
  
  //===
  
 }//***eof
